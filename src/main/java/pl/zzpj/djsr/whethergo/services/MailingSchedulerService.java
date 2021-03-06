package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.zzpj.djsr.whethergo.entities.SubscriptionEntity;
import pl.zzpj.djsr.whethergo.entities.WeatherEntity;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Log4j2
@RequiredArgsConstructor
@Service
public class MailingSchedulerService {
    final MailingService mailingService;
    final SubscriptionService subscriptionService;
    final WeatherService weatherService;
    final String subject = "Weather subscription for [CITY]";

    @Scheduled(fixedRate = 50_000)
    public void sendSubscription(){
        log.info("Searching for subscriptions...");
        for (SubscriptionEntity s : subscriptionService.getAll()) {
            if (s.getHour() == OffsetDateTime.now(ZoneId.of("UTC")).getHour()
                    && s.getMinute() == OffsetDateTime.now(ZoneId.of("UTC")).getMinute()) {
                log.info("Sending mail to user " + s.getUser().getEmail() + "...");
                WeatherEntity weatherEntity = weatherService.getLatestForLocalization(s.getLocation());
                mailingService.sendMail(
                        s.getUser().getEmail(),
                        subject.replace("[CITY]", s.getLocation().getName()),
                        mailingService.getSubscriptionTemplate()
                                .replace("[CITY]", s.getLocation().getName())
                                .replace("[TEMP]", String.valueOf(weatherEntity.getTemp() - 272.15))
                                .replace("[PRESSURE]", weatherEntity.getPressure().toString())
                                .replace("[HUMIDITY]", weatherEntity.getHumidity().toString())
                                .replace("[TIME]", OffsetDateTime
                                        .ofInstant(weatherEntity.getCreatedDate(), ZoneId.of("UTC"))
                                        .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                );
            }
        }
    }

}
