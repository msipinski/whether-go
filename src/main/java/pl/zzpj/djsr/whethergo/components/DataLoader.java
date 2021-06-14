package pl.zzpj.djsr.whethergo.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.services.WeatherService;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

@Log4j2
@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
    ArrayList<LocationEntity> locationList = new ArrayList<>();
    final WeatherService weatherService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Charset charset = StandardCharsets.UTF_8;
        log.debug("==================");
        log.debug("Reading city names file...");
        String filePath = "src/main/resources/city_names.txt";
        FileReader locationFile = new FileReader("src/main/resources/city_names.txt", charset);
        Scanner scanner = new Scanner(locationFile);
        while (scanner.hasNextLine()) {
            String scannedLocationName = scanner.nextLine();
            LocationEntity newLocation = new LocationEntity(scannedLocationName, false);
            locationList.add(newLocation);
        }
        log.debug("Done, number of locations: " + locationList.size());
        log.debug("==================");
        weatherService.setLocationList(locationList);

        // Na razie kilka miast ustawionych na sztywno
        weatherService.setLocationImporting("Łódź", true);
        weatherService.setLocationImporting("London", true);
        weatherService.setLocationImporting("Warszawa", true);
        weatherService.setLocationImporting("İstanbul", true);
    }
}
