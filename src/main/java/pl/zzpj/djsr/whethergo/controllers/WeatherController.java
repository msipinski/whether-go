package pl.zzpj.djsr.whethergo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;
import pl.zzpj.djsr.whethergo.services.LocationService;
import pl.zzpj.djsr.whethergo.services.WeatherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("${app.api.baseUri}/weather")
public class WeatherController {
    final WeatherRepository weatherRepository;
    final LocationRepository locationRepository;
    final WeatherService weatherService;
    final LocationService locationService;

    @Value("${app.location.default}")
    String defaultLocation;

    protected LocationEntity getLocation() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(AccountEntity.class::isInstance)
                .map(AccountEntity.class::cast)
                .map(AccountEntity::getPreferredLocation)
                .orElse(locationRepository.findByName(defaultLocation).orElse(null));
    }

    @GetMapping
    public List<WeatherEntity> list() {
        return weatherRepository.findAllByLocation(getLocation());
    }

    @GetMapping("/latest")
    public WeatherEntity latest() {
        return weatherRepository.findFirstByLocationOrderByCreatedDateDesc(getLocation());
    }

    @GetMapping("/getCities/active")
    public List<String> getActiveCities() {
        List<LocationEntity> locations = locationService.getActiveLocations();
        ArrayList<String> locationNames = new ArrayList<>();
        for(LocationEntity location : locations) {
            locationNames.add(location.getName());
        }
        log.debug(locationNames.size());
        return locationNames;
    }

    @GetMapping("/getCities/inactive")
    public List<String> getInactiveCities() {
        List<LocationEntity> locations = locationService.getInactiveLocations();
        ArrayList<String> locationNames = new ArrayList<>();
        for(LocationEntity location : locations) {
            locationNames.add(location.getName());
        }
        log.debug(locationNames.size());
        return locationNames;
    }

    @PostMapping("/addCity/{cityName}")
    public boolean addCity(@PathVariable String cityName) {
        return locationService.setLocationImporting(cityName, true);
    }

    @PostMapping("/removeCity/{cityName}")
    public boolean removeCity(@PathVariable String cityName) {
        return locationService.setLocationImporting(cityName, false);
    }

    @GetMapping("/importAll")
    public void importForChosenCities() {
        weatherService.importWeatherForChosenCities();
    }

    @GetMapping("/import/{cityName}")
    public void importForCity(@PathVariable String cityName) {
        weatherService.importWeatherDataForCity(cityName);
    }
}
