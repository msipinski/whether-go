package pl.zzpj.djsr.whethergo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.repositories.WeatherRepository;
import pl.zzpj.djsr.whethergo.services.SchedulerService;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("${app.api.baseUri}/weather")
public class WeatherController {
    final WeatherRepository weatherRepository;
    final LocationRepository locationRepository;

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

    @GetMapping("/setCity/{cityName}")
    public void setCity(@PathVariable String cityName) {
        log.debug("Chosen city " + cityName);
        SchedulerService.setSelectedCityName(cityName);
    }
}
