package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Log4j2
@RequiredArgsConstructor
@Service
public class PredictionsService {

    final WeatherRepository weatherRepository;
    final LocationRepository locationRepository;
    private static final int AMOUT_OF_LAST_RECORD_TO_ANALYSE = 3;

    public WeatherEntity predict(String cityName) throws ChangeSetPersister.NotFoundException {
        log.debug("Loading weather entities from repo...");
        WeatherEntity prediction = new WeatherEntity();
        List<WeatherEntity> weatherEntities = weatherRepository
                .findAllByLocation(locationRepository
                        .findByName(cityName)
                        .orElseThrow(() -> new ChangeSetPersister.NotFoundException()));
        System.out.println(weatherEntities.size() - (1 + AMOUT_OF_LAST_RECORD_TO_ANALYSE));
        List<WeatherEntity> toAnalyseRecords = weatherEntities.stream()
                .sorted((record1, record2) -> record1.getCreatedDate().compareTo(record2.getCreatedDate()))
                .skip(weatherEntities.size() - (1 + AMOUT_OF_LAST_RECORD_TO_ANALYSE))
                .collect(Collectors.toList());

        long diffrenceBeetweenTwoMeasurements = toAnalyseRecords.get(1).getCreatedDate().getEpochSecond()
                - toAnalyseRecords.get(0).getCreatedDate().getEpochSecond();
        prediction.setCreatedDate(toAnalyseRecords
                .get(toAnalyseRecords.size() - 1)
                .getCreatedDate()
                .plusSeconds(diffrenceBeetweenTwoMeasurements));
        List<Point2D.Double> temp = new ArrayList<>(),
                humm = new ArrayList<>(),
                press = new ArrayList<>();

        IntStream.range(1, toAnalyseRecords.size()).forEach(i -> {
            temp.add(new Point2D.Double(diffrenceBeetweenTwoMeasurements * i, toAnalyseRecords.get(i - 1).getTemp()));
            humm.add(new Point2D.Double(diffrenceBeetweenTwoMeasurements * i, toAnalyseRecords.get(i - 1).getHumidity()));
            press.add(new Point2D.Double(diffrenceBeetweenTwoMeasurements * i, toAnalyseRecords.get(i - 1).getPressure()));
        });
        prediction.setPressure(lagrangePrediction(press, diffrenceBeetweenTwoMeasurements * toAnalyseRecords.size()));
        prediction.setTemp(lagrangePrediction(temp, diffrenceBeetweenTwoMeasurements * toAnalyseRecords.size()));
        prediction.setHumidity(lagrangePrediction(humm, diffrenceBeetweenTwoMeasurements * toAnalyseRecords.size()));

        return prediction;
    }

    public static double lagrangePrediction(List<Point2D.Double> points, double x) {
        return points.stream()
                .mapToDouble(point -> {
                    List<Point2D.Double> pointsWithoutPoint = new ArrayList<>(points);
                    pointsWithoutPoint.remove(point);
                    return point.y * pointsWithoutPoint
                            .stream()
                            .mapToDouble(pointFromPointsWithoutPoint ->
                                    (x - pointFromPointsWithoutPoint.x) / (point.x - pointFromPointsWithoutPoint.x))
                            .reduce(1, (a, b) -> a * b);
                }).sum();
    }
}
