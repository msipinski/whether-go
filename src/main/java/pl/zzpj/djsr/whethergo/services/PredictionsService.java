package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@Service
public class PredictionsService {
    final WeatherRepository weatherRepository;
    final LocationRepository locationRepository;

    public WeatherEntity predict(String cityName) {
        WeatherEntity prediction = new WeatherEntity();
        List<WeatherEntity> weatherEntities = weatherRepository
                .findAllByLocation(locationRepository
                        .findByName(cityName)
                        .get());
        List<Map.Entry> temperature = (List<Map.Entry>) weatherEntities.stream()
                .map(entity -> Map.entry(entity.getCreatedDate(), entity.getTemp()));
        System.out.println(temperature.get(0));
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
