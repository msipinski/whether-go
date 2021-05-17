package pl.zzpj.djsr.whethergo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByName(String name);
}
