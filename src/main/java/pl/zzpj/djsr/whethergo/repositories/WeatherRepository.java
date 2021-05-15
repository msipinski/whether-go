package pl.zzpj.djsr.whethergo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    WeatherEntity findFirstByOrderByCreatedDateDesc();
}
