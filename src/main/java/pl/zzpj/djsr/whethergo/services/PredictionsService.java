package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;

import java.awt.geom.Point2D;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Log4j2
@RequiredArgsConstructor
@Service
public class PredictionsService {

    final WeatherRepository weatherRepository;
    final LocationRepository locationRepository;
    private static final int AMOUT_OF_LAST_RECORD_TO_ANALYSE = 5;

    public WeatherEntity predict(String cityName) {
        log.debug("Loading weather entities from repo...");
        WeatherEntity prediction = new WeatherEntity();
        Optional<LocationEntity> ent = locationRepository
                .findByName(cityName);
        List<WeatherEntity> weatherEntities = weatherRepository
                .findAllByLocation(locationRepository
                        .findByName(cityName)
                        .get());
        List<WeatherEntity> toAnalyseRecords = weatherEntities.stream()
                .sorted((record1, record2) -> record1.getCreatedDate().compareTo(record2.getCreatedDate()))
                .skip(weatherEntities.size() - (1 + AMOUT_OF_LAST_RECORD_TO_ANALYSE))
                .collect(Collectors.toList());

        long diffrenceBeetweenTwoMeasurementsTimeInSeconds = toAnalyseRecords.get(1).getCreatedDate().getEpochSecond()
                - toAnalyseRecords.get(0).getCreatedDate().getEpochSecond();
        prediction.setCreatedDate(toAnalyseRecords
                .get(toAnalyseRecords.size() - 1)
                .getCreatedDate()
                .plusSeconds(diffrenceBeetweenTwoMeasurementsTimeInSeconds));
        List<Point2D.Double> temp = new ArrayList<>(),
                humm = new ArrayList<>(),
                press = new ArrayList<>();

        IntStream.range(1, toAnalyseRecords.size()).forEach(i -> {
            temp.add(new Point2D.Double(toAnalyseRecords.get(i).getCreatedDate().getEpochSecond()
                    - toAnalyseRecords.get(i - 1).getCreatedDate().getEpochSecond(), toAnalyseRecords.get(i - 1).getTemp()));
            humm.add(new Point2D.Double(toAnalyseRecords.get(i).getCreatedDate().getEpochSecond()
                    - toAnalyseRecords.get(i - 1).getCreatedDate().getEpochSecond(), toAnalyseRecords.get(i - 1).getHumidity()));
            press.add(new Point2D.Double(toAnalyseRecords.get(i).getCreatedDate().getEpochSecond()
                    - toAnalyseRecords.get(i - 1).getCreatedDate().getEpochSecond(), toAnalyseRecords.get(i - 1).getPressure()));
        });
        prediction.setPressure(lagrangePrediction(press, diffrenceBeetweenTwoMeasurementsTimeInSeconds * (2 + toAnalyseRecords.size())));
        prediction.setTemp(lagrangePrediction(temp, diffrenceBeetweenTwoMeasurementsTimeInSeconds * (2 + toAnalyseRecords.size())));
        prediction.setHumidity(lagrangePrediction(humm, diffrenceBeetweenTwoMeasurementsTimeInSeconds * (2 + toAnalyseRecords.size())));

        return prediction;
    }

    public WeatherEntity predictPast(String cityName, String centerWindowDate, int nearSamplesToAnalyseCount, String instantString) {
        WeatherEntity prediction = new WeatherEntity();
        List<WeatherEntity> weatherEntities = weatherRepository
                .findAllByLocation(locationRepository
                        .findByName(cityName)
                        .get());
        WeatherEntity record = weatherEntities.stream().filter(x -> x.getCreatedDate()
                .equals(Instant.parse(centerWindowDate)))
                .findFirst().get();

        int index = weatherEntities.indexOf(record);
        prediction.setCreatedDate(Instant.parse(instantString));

        List<Point2D.Double> temp = new ArrayList<>(),
                humm = new ArrayList<>(),
                press = new ArrayList<>();

        IntStream.range(-nearSamplesToAnalyseCount, nearSamplesToAnalyseCount).forEach(i -> {
            temp.add(new Point2D.Double(weatherEntities.get(index - nearSamplesToAnalyseCount).getCreatedDate().getEpochSecond()
                    - weatherEntities.get(index + i).getCreatedDate().getEpochSecond(), weatherEntities.get(index + i).getTemp()));
            humm.add(new Point2D.Double(weatherEntities.get((index - nearSamplesToAnalyseCount)).getCreatedDate().getEpochSecond()
                    - weatherEntities.get(index + i).getCreatedDate().getEpochSecond(), weatherEntities.get(index + i).getHumidity()));
            press.add(new Point2D.Double(weatherEntities.get((index - nearSamplesToAnalyseCount)).getCreatedDate().getEpochSecond()
                    - weatherEntities.get(index + i).getCreatedDate().getEpochSecond(), weatherEntities.get(index + i).getPressure()));
        });

        prediction.setPressure(lagrangePrediction(press, prediction.getCreatedDate().getEpochSecond()
                - weatherEntities.get((index - nearSamplesToAnalyseCount)).getCreatedDate().getEpochSecond()));
        prediction.setTemp(lagrangePrediction(temp, prediction.getCreatedDate().getEpochSecond()
                - weatherEntities.get((index - nearSamplesToAnalyseCount)).getCreatedDate().getEpochSecond()));
        prediction.setHumidity(lagrangePrediction(humm, prediction.getCreatedDate().getEpochSecond()
                - weatherEntities.get((index - nearSamplesToAnalyseCount)).getCreatedDate().getEpochSecond()));

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
