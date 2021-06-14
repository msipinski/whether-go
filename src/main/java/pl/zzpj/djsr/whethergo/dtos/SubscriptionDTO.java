package pl.zzpj.djsr.whethergo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Data
@AllArgsConstructor
public class SubscriptionDTO {
    @Nullable
    Long id;
    @Min(0)
    @Max(23)
    Integer hour;
    LocationDTO location;
    @Nullable
    Instant lastNotificationDate;
}
