package pl.zzpj.djsr.whethergo.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.accounts.entities.AuthorityEnum;
import pl.zzpj.djsr.whethergo.accounts.repositories.AccountRepository;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;
import pl.zzpj.djsr.whethergo.repositories.LocationRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
    final LocationRepository locationRepository;
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;

    String defaultLocation = "Łódź";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Charset charset = StandardCharsets.UTF_8;
        if (locationRepository.count() == 0) {
            log.info("==================");
            log.info("Reading city names file...");
            try (var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/city_names.txt"), charset))) {
                locationRepository.saveAll(
                        reader.lines()
                                .map(LocationEntity::new)
                                .collect(Collectors.toList())
                );
                locationRepository.findByName(defaultLocation)
                        .ifPresent(l -> {
                            log.info("Found default location \"" + defaultLocation + "\"; setting it active");
                            l.setImporting(true);
                            locationRepository.save(l);
                        });
                log.info("Done, number of locations: " + locationRepository.count());
            }
            log.info("==================");
        }
        if (accountRepository.findByUsername("admin").isEmpty()) {
            log.info("Creating admin account");
            accountRepository.save(AccountEntity.builder()
                    .withUsername("admin")
                    .withPassword(passwordEncoder.encode("admin"))
                    .withEmail("admin@eg.com")
                    .withAuthority(AuthorityEnum.ROLE_CLIENT)
                    .withAuthority(AuthorityEnum.ROLE_ADMIN)
                    .build()
            );
        }
    }
}
