package pl.zzpj.djsr.whethergo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    WeatherEntity findFirstByLocationOrderByCreatedDateDesc(LocationEntity location);

    List<WeatherEntity> findTop500ByLocationOrderByCreatedDateDesc(LocationEntity location);
}
