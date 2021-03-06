package pl.zzpj.djsr.whethergo.entities;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WeatherEntity extends AbstractPersistable<Long> {
    Double temp;
    Double pressure;
    Double humidity;
    Instant createdDate;
    @ManyToOne
    LocationEntity location;
}
