package pl.zzpj.djsr.whethergo.services;

import net.bytebuddy.matcher.FilterableList;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PredictionsServiceTest {

    PredictionsService service;

    @Test
    public void lagrangePredictionTest(){
        List<Point2D.Double> points = new ArrayList<>();
        points.add(new Point2D.Double(1,1));
        points.add(new Point2D.Double(2,4));
        points.add(new Point2D.Double(3,4));
        assertEquals(1, PredictionsService.lagrangePrediction(points,4));

        List<Point2D.Double> points2 = new ArrayList<>();
        points2.add(new Point2D.Double(1,1));
        points2.add(new Point2D.Double(2,4));
        points2.add(new Point2D.Double(3,7));
        assertEquals(10, PredictionsService.lagrangePrediction(points2,4));

        List<Point2D.Double> points3 = new ArrayList<>();
        points3.add(new Point2D.Double(1,10));
        points3.add(new Point2D.Double(2,7));
        points3.add(new Point2D.Double(3,4));
        assertEquals(1, PredictionsService.lagrangePrediction(points3,4));
    }

    @Test
    public void predictWeatherTest(){
    }

}