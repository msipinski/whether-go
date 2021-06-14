package pl.zzpj.djsr.whethergo.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubscriptionEntity extends AbstractPersistable<Long> {
    @ManyToOne
    AccountEntity user;
    @Min(0)
    @Max(23)
    Integer hour;
    @ManyToOne
    @Cascade(CascadeType.REFRESH)
    LocationEntity location;
    Instant lastNotificationDate;
}
