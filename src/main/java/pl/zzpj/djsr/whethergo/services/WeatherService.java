package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zzpj.djsr.whethergo.dtos.WeatherDTO;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;

import java.time.Instant;

@Log4j2
@RequiredArgsConstructor
@Service
public class WeatherService {
    final RestTemplate restTemplate;
    final WeatherRepository weatherRepository;

    public void importFromOpenWeatherMap() {
        var weatherDTO = restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?id=3093133&appid=6cc72bb35fdd263c3ea986c5be325603",
                WeatherDTO.class
        );
        log.debug(weatherDTO);
        var weatherEntity = new WeatherEntity(
                weatherDTO.getMain().getTemp(),
                weatherDTO.getMain().getPressure(),
                weatherDTO.getMain().getHumidity(),
                Instant.now()
        );
        weatherRepository.save(weatherEntity);
    }
}
