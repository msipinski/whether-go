package pl.zzpj.djsr.whethergo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;
import pl.zzpj.djsr.whethergo.services.SchedulerService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    final WeatherRepository weatherRepository;

    @GetMapping
    public List<WeatherEntity> list() {
        return weatherRepository.findAll();
    }

    @GetMapping("/latest")
    public WeatherEntity latest() {
        return weatherRepository.findFirstByOrderByCreatedDateDesc();
    }

    @GetMapping("/setCity/{cityName}")
    public void setCity(@PathVariable String cityName) {
        log.debug("Chosen city " + cityName);
        SchedulerService.setSelectedCityName(cityName);
    }
}
