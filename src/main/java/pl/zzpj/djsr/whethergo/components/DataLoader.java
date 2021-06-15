package pl.zzpj.djsr.whethergo.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.services.WeatherService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
    final WeatherService weatherService;
    final LocationRepository locationRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Charset charset = StandardCharsets.UTF_8;
        if (locationRepository.count() == 0) {
            log.info("==================");
            log.info("Reading city names file...");
            try (var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/city_names.txt"), charset))) {
                locationRepository.saveAll(
                        reader.lines()
                                .map(LocationEntity::new)
                                .collect(Collectors.toList())
                );
            }
            log.info("Done, number of locations: " + locationRepository.count());
        }
        log.info("==================");
    }
}
