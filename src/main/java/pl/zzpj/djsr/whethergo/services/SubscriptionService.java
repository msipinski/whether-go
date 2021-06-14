package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.entities.SubscriptionEntity;
import pl.zzpj.djsr.whethergo.repositories.SubscriptionRepository;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class SubscriptionService {
    final SubscriptionRepository subscriptionRepository;

    public List<SubscriptionEntity> getSubscriptionEntitiesByUser(AccountEntity accountEntity){
        return subscriptionRepository.getSubscriptionEntitiesByUser(accountEntity);
    }

    public SubscriptionEntity createSubscription(SubscriptionEntity subscriptionEntity){
        subscriptionEntity = subscriptionRepository.saveAndFlush(subscriptionEntity);
        return subscriptionEntity;
    }

}
