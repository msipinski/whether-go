package pl.zzpj.djsr.whethergo.entities;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LocationEntity extends AbstractPersistable<Long> {
    @Column(unique = true)
    String name;
    boolean importing = false;
    @OneToMany
    Set<SubscriptionEntity> subscriptions;

    public LocationEntity(String name) {
        this.name = name;
    }
    public LocationEntity(String name, boolean bool) {
        this.name = name;
        boolean importing = bool;
    }
}
