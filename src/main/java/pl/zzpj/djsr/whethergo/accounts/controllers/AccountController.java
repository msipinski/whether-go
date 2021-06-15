package pl.zzpj.djsr.whethergo.accounts.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.djsr.whethergo.accounts.dtos.LoginAccountDTO;
import pl.zzpj.djsr.whethergo.accounts.dtos.RegisterAccountDTO;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.accounts.entities.AuthorityEnum;
import pl.zzpj.djsr.whethergo.accounts.repositories.AccountRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("${app.api.baseUri}/account")
public class AccountController {
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationProvider authenticationProvider;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountEntity> list() {
        return accountRepository.findAll();
    }

    @GetMapping("/get-mine")
    public AccountEntity getMine(Authentication authentication) {
        return (AccountEntity) authentication.getPrincipal();
    }

    @GetMapping("/username")
    public String getUsername(Authentication authentication) {
        return ((AccountEntity) authentication.getPrincipal()).getUsername();
    }

    @PreAuthorize("true")
    @PostMapping("/login")
    public void login(@RequestBody LoginAccountDTO loginAccountDTO) {
        var authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                loginAccountDTO.getUsername(),
                loginAccountDTO.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PreAuthorize("true")
    @PostMapping("/register")
    public void register(@RequestBody RegisterAccountDTO registerAccountDTO) {
        var accountEntity = AccountEntity.builder()
                .withUsername(registerAccountDTO.getUsername())
                .withPassword(passwordEncoder.encode(registerAccountDTO.getPassword()))
                .withEmail(registerAccountDTO.getEmail())
                .withAuthority(AuthorityEnum.ROLE_CLIENT)
                .build();
        accountEntity = accountRepository.save(accountEntity);
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(accountEntity, null));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
