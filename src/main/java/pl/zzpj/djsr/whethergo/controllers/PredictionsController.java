package pl.zzpj.djsr.whethergo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;

import javax.annotation.Resource;


@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("${app.api.baseUri}/weather")
public class PredictionsController {
    @Resource
    private PredictionsController controller;
    @GetMapping("/{location}")
    public WeatherEntity predict(@PathVariable String cityName){
        return controller.predict(cityName);
    }
}
