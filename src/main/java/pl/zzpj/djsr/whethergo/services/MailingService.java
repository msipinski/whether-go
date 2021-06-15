package pl.zzpj.djsr.whethergo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class MailingService {

    private String username = "whether.go@gmail.com";
    private String password = "jkjsnmlfiprfgogk";

    private String host ="smtp.gmail.com";
    private String port = "465";

    private final String subscriptionTemplate = "templates/subscription.html";

    private boolean isEnabled = true;

    private final Properties properties = new Properties();

    @PostConstruct
    public void init() {
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    public String getSubscriptionTemplate(){
        return getResourceAsString(subscriptionTemplate);
    }

    private String getResourceAsString(String path) {
        return new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)),
                        StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public void sendMail(String recipient, String subject, String content) {
        sendMail(recipient, subject, content, null, null);
    }

    public void sendMail(String recipient, String subject, String content, List<String> CC, List<String> BCC) {
        if (!isEnabled)
            return;
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient)
            );
            if (CC != null) {
                message.setRecipients(
                        Message.RecipientType.CC,
                        InternetAddress.parse(String.join(",", CC))
                );
            }
            if (BCC != null) {
                message.setRecipients(
                        Message.RecipientType.BCC,
                        InternetAddress.parse(String.join(",", BCC))
                );
            }
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
