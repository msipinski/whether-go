package pl.zzpj.djsr.whethergo.accounts.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.zzpj.djsr.whethergo.accounts.repositories.AccountRepository;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
