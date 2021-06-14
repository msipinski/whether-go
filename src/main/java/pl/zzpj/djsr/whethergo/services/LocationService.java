package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty("app.scheduler.enabled")
@Service
public class LocationService {
    final LocationRepository locationRepository;

    public boolean setLocationImporting(String name, boolean importing) {
        Optional<LocationEntity> optionalLocation = this.locationRepository.findByName(name);
        if(optionalLocation.isPresent()) {
            LocationEntity location = optionalLocation.get();
            location.setImporting(importing);
            this.locationRepository.save(location);
            log.debug("Successfully updated importing for " + name);
            return true;
        }
        return false;
    }

    public List<LocationEntity> getLocationsByImporting(boolean importing) {
        List<LocationEntity> result = this.locationRepository.findAllByImporting(importing);
        log.info(result.size());
        return result;
    }

    public List<LocationEntity> getActiveLocations() {
        return getLocationsByImporting(true);
    }

    public List<LocationEntity> getInactiveLocations() {
        return getLocationsByImporting(false);
    }
    public Optional<LocationEntity> getLocationByName(String name){
        return locationRepository.findByName(name);
    }
}
