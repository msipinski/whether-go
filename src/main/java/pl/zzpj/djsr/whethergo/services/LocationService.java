package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty("app.scheduler.enabled")
@Service
public class LocationService {
    final LocationRepository locationRepository;

    public Optional<LocationEntity> getLocationByName(String name){
        return locationRepository.findByName(name);
    }
}
