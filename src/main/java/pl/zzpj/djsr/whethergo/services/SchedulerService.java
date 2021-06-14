package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty("app.scheduler.enabled")
@Service
public class SchedulerService {
    final WeatherService weatherService;

    @Scheduled(fixedRate = 300_000)
    void importFromOpenWeatherMap() {
        weatherService.importFromOpenWeatherMap();
    }
}
