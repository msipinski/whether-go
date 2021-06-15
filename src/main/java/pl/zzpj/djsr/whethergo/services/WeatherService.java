package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zzpj.djsr.whethergo.dtos.WeatherDTO;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Log4j2
@RequiredArgsConstructor
@Service
public class WeatherService {
    final RestTemplate restTemplate;
    final WeatherRepository weatherRepository;
    final LocationRepository locationRepository;


    public void importFromOpenWeatherMap() {
        var weatherDTO = restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?id=3093133&appid=6cc72bb35fdd263c3ea986c5be325603",
                WeatherDTO.class
        );
        log.debug(weatherDTO);
        if (weatherDTO != null) {
            var weatherEntity = WeatherEntity.builder()
                    .temp(weatherDTO.getMain().getTemp())
                    .pressure(weatherDTO.getMain().getPressure())
                    .humidity(weatherDTO.getMain().getHumidity())
                    .createdDate(Instant.now())
                    .build();
            log.debug(weatherEntity);
            weatherRepository.save(weatherEntity);
            log.debug("Imported data from openweathermap.org");
        } else {
            log.warn("Import from openweathermap.org failed");
        }
    }

    public void importWeatherDataForCity(LocationEntity locationEntity) {
        var url = "https://api.openweathermap.org/data/2.5/weather?q="
                + locationEntity.getName() + "&appid=6cc72bb35fdd263c3ea986c5be325603";
        var weatherDTO = restTemplate.getForObject(
                url,
                WeatherDTO.class
        );
        log.debug(weatherDTO);
        if (weatherDTO != null) {
            var weatherEntity = WeatherEntity.builder()
                    .temp(weatherDTO.getMain().getTemp())
                    .pressure(weatherDTO.getMain().getPressure())
                    .humidity(weatherDTO.getMain().getHumidity())
                    .createdDate(Instant.now())
                    .location(locationEntity)
                    .build();
            log.debug(weatherEntity);
            weatherRepository.save(weatherEntity);
            log.debug("Imported data from openweathermap.org");
        } else {
            log.warn("Import from openweathermap.org failed");
        }
    }

    public void importWeatherForChosenCities() {
        var allByImporting = locationRepository.findAllByImporting(true);
        log.info("Importing weather data for " + allByImporting.size() + " location(s)...");
        for (LocationEntity location : allByImporting) {
            log.debug("Importing data for " + location.getName());
            importWeatherDataForCity(location);
        }
    }
    public WeatherEntity getLatestForLocalization(LocationEntity locationEntity){
        return weatherRepository.findFirstByLocationOrderByCreatedDateDesc(locationEntity);
    }
}
