package pl.zzpj.djsr.whethergo.accounts.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.userdetails.UserDetails;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;

import javax.persistence.*;
import java.util.EnumSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountEntity extends AbstractPersistable<Long> implements UserDetails {
    @Column(unique = true)
    String username;
    String password;
    @Column(unique = true)
    String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<AuthorityEnum> authorities = EnumSet.noneOf(AuthorityEnum.class);
    @ManyToOne
    LocationEntity preferredLocation;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String username;
        String password;
        String email;
        Set<AuthorityEnum> authorities = EnumSet.noneOf(AuthorityEnum.class);
        LocationEntity preferredLocation;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withAuthority(AuthorityEnum authority) {
            this.authorities.add(authority);
            return this;
        }

        public Builder withPrefferedLoaction(LocationEntity preferredLocation) {
            this.preferredLocation = preferredLocation;
            return this;
        }

        public AccountEntity build() {
            return new AccountEntity(username, password, email, authorities, preferredLocation);
        }
    }
}
