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
    static String selectedCityName = "London";

    /*@Scheduled(fixedRate = 10_000)
    void importFromOpenWeatherMap() {
        weatherService.importWeatherDataForCity(selectedCityName);
    }*/

    @Scheduled(fixedRate = 10_000)
    void importFromOpenWeatherMap() {
        weatherService.importWeatherForChosenCities();
    }

    public static void setSelectedCityName(String newCityName) {
        selectedCityName = newCityName;
    }
}
