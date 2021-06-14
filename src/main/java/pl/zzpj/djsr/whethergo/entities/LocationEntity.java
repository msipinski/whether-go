package pl.zzpj.djsr.whethergo.entities;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LocationEntity extends AbstractPersistable<Long> {
    String name;
    boolean importing = false;

    public LocationEntity(String name) {
        this.name = name;
    }
}
