package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.accounts.repositories.AccountRepository;
import pl.zzpj.djsr.whethergo.entities.SubscriptionEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;
import pl.zzpj.djsr.whethergo.repositories.SubscriptionRepository;

import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class SubscriptionService {
    final SubscriptionRepository subscriptionRepository;
    final AccountRepository accountRepository;
    final LocationRepository locationRepository;

    public List<SubscriptionEntity> getSubscriptionEntitiesByUser(AccountEntity accountEntity){
        return subscriptionRepository.getSubscriptionEntitiesByUser(accountEntity);
    }

    public SubscriptionEntity createSubscription(SubscriptionEntity subscriptionEntity){
        subscriptionEntity.setUser(accountRepository.findById(Objects.requireNonNull(subscriptionEntity.getUser().getId())).orElse(null));
        subscriptionEntity.setLocation(locationRepository.findByName(Objects.requireNonNull(subscriptionEntity.getLocation().getName())).orElse(null));
        subscriptionEntity = subscriptionRepository.saveAndFlush(subscriptionEntity);
        return subscriptionEntity;
    }

    public boolean removeSubscription(AccountEntity accountEntity ,Long id){
        accountEntity = accountRepository.findById(accountEntity.getId()).orElse(null);
        var subscriptionEntity = accountEntity.getSubscriptions().stream().filter(s -> Objects.equals(s.getId(), id)).findFirst().orElse(null);
        if(subscriptionEntity == null){
            return false;
        }
        subscriptionRepository.delete(subscriptionEntity);
        return true;
    }

    public List<SubscriptionEntity> getAll(){
        return subscriptionRepository.findAll();
    }

}
