package pl.zzpj.djsr.whethergo.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsername(String username);
}
