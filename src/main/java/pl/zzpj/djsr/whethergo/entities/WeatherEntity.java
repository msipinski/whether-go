package pl.zzpj.djsr.whethergo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WeatherEntity extends AbstractPersistable<Long> {
    double temp;
    double pressure;
    double humidity;
    Instant createdDate;
}
