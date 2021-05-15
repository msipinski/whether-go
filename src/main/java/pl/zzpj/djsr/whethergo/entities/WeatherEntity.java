package pl.zzpj.djsr.whethergo.entities;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WeatherEntity extends AbstractPersistable<Long> {
    double temp;
    double pressure;
    double humidity;
    Date createdDate;
}
