package pl.zzpj.djsr.whethergo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;

@RepositoryRestResource(path = "weather", collectionResourceRel = "weather")
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    WeatherEntity findFirstByOrderByCreatedDateDesc();
}
