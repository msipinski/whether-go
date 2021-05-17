package pl.zzpj.djsr.whethergo.accounts.entities;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityEnum implements GrantedAuthority {
    ROLE_CLIENT,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
