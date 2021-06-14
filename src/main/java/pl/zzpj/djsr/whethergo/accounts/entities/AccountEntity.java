package pl.zzpj.djsr.whethergo.accounts.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.userdetails.UserDetails;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.entities.SubscriptionEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountEntity extends AbstractPersistable<Long> implements UserDetails {
    @Column(unique = true)
    String username;
    String password;
    String email;
    @Enumerated(EnumType.STRING)
    Set<AuthorityEnum> authorities;
    @ManyToOne
    LocationEntity preferredLocation;
    @OneToMany( mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    List<SubscriptionEntity> subscriptions;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
