package pl.zzpj.djsr.whethergo.utils.converters;

import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.dtos.SubscriptionDTO;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.entities.SubscriptionEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SubscriptionConverter {
    public static SubscriptionDTO subscriptionDTOFromEntity(SubscriptionEntity subscriptionEntity){
        return new SubscriptionDTO(subscriptionEntity.getId(),
                subscriptionEntity.getHour(),
                LocationConverter.locationDTOFromEntity(subscriptionEntity.getLocation()),
                subscriptionEntity.getLastNotificationDate());
    }
    public static List<SubscriptionDTO> subscriptionDTOsFromEntities(List<SubscriptionEntity> subscriptionEntities){
        return subscriptionEntities
                .stream()
                .filter(Objects::nonNull)
                .map(SubscriptionConverter::subscriptionDTOFromEntity)
                .collect(Collectors.toList());
    }
    public static SubscriptionEntity subscriptionEntityFromDTO(SubscriptionDTO subscriptionDTO, AccountEntity accountEntity, LocationEntity locationEntity){
        return new SubscriptionEntity(accountEntity,
                subscriptionDTO.getHour(),
                locationEntity,
                subscriptionDTO.getLastNotificationDate());
    }
}
