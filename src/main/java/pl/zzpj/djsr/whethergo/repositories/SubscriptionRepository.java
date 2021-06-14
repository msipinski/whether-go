package pl.zzpj.djsr.whethergo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.entities.SubscriptionEntity;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    List<SubscriptionEntity> getSubscriptionEntitiesByUser(AccountEntity accountEntity);

}
