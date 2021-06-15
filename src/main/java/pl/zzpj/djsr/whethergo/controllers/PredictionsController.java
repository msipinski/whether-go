package pl.zzpj.djsr.whethergo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.djsr.whethergo.services.PredictionsService;


@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("${app.api.baseUri}/weather/predict")
public class PredictionsController {

    final PredictionsService service;

    @GetMapping("/{location}")
    public Object predict(@PathVariable String location){
            return service.predict(location);
    }

    @GetMapping("/{location}/{centerWindowDate}/{nearSamplesToAnalyseCount}/{pointToCalc}")
    public Object predict(@PathVariable String location,
                          @PathVariable String centerWindowDate,
                          @PathVariable int nearSamplesToAnalyseCount,
                          @PathVariable String pointToCalc){
        return service.predictPast(location, centerWindowDate, nearSamplesToAnalyseCount, pointToCalc);

    }
}
