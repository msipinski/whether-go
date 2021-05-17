package pl.zzpj.djsr.whethergo.accounts.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.accounts.entities.AuthorityEnum;
import pl.zzpj.djsr.whethergo.accounts.repositories.AccountRepository;

import java.util.List;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("${app.api.baseUri}/account")
public class AccountController {
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountEntity> list() {
        return accountRepository.findAll();
    }

    @GetMapping("/get-mine")
    public AccountEntity getMine(Authentication authentication) {
        return (AccountEntity) authentication.getPrincipal();
    }

    @PreAuthorize("true")
    @PostMapping("/register")
    public AccountEntity register(@RequestParam String username,
                                  @RequestParam String password) {
        var accountEntity = AccountEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .authorities(Set.of(AuthorityEnum.ROLE_ADMIN))
                .build();
        accountEntity = accountRepository.save(accountEntity);
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(accountEntity, null));
        return accountEntity;
    }
}
