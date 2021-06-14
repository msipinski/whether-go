package pl.zzpj.djsr.whethergo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.djsr.whethergo.accounts.entities.AccountEntity;
import pl.zzpj.djsr.whethergo.dtos.SubscriptionDTO;
import pl.zzpj.djsr.whethergo.services.LocationService;
import pl.zzpj.djsr.whethergo.services.SubscriptionService;
import pl.zzpj.djsr.whethergo.utils.converters.SubscriptionConverter;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("${app.api.baseUri}/subscriptions")
public class SubscriptionController {

    final SubscriptionService subscriptionService;
    final LocationService locationService;

    protected AccountEntity getUser(){
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(AccountEntity.class::isInstance)
                .map(AccountEntity.class::cast)
                .orElse(null);
    }

    @GetMapping
    public List<SubscriptionDTO> getSubscriptions(){
        return SubscriptionConverter.subscriptionDTOsFromEntities(subscriptionService.getSubscriptionEntitiesByUser(getUser()));
    }

    @RequestMapping(
            value = { "/subscribe" },
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public SubscriptionDTO subscribe(@RequestBody SubscriptionDTO subscriptionDTO){
        return SubscriptionConverter.subscriptionDTOFromEntity(
                subscriptionService.createSubscription(SubscriptionConverter.subscriptionEntityFromDTO(subscriptionDTO,
                        getUser(), locationService.getLocationByName(subscriptionDTO.getLocation().getName()).orElse(null)))
        );
    }
}
