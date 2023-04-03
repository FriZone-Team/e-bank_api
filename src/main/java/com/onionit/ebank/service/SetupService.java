package com.onionit.ebank.service;

import com.onionit.ebank.model.Card;
import com.onionit.ebank.model.role.Role;
import com.onionit.ebank.model.role.RoleName;
import com.onionit.ebank.model.user.User;
import com.onionit.ebank.payload.ExchangeRequest;
import com.onionit.ebank.payload.request.CardRequest;
import com.onionit.ebank.repository.RoleRepository;
import com.onionit.ebank.security.UserPrincipal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class SetupService implements InitializingBean {
    protected final RoleRepository roleRepository;

    protected final UserService userService;

    protected final CardService cardService;

    protected final ExchangeService exchangeService;

    public SetupService(RoleRepository roleRepository, UserService userService, CardService cardService, ExchangeService exchangeService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.cardService = cardService;
        this.exchangeService = exchangeService;
    }

    @Override
    public void afterPropertiesSet() {
        if (this.roleRepository.count() > 0) {
            return;
        }

        for (RoleName name : RoleName.values()) {
            Role role = this.roleRepository.findByName(name).orElse(new Role(name));
            if (Objects.isNull(role.getId())) {
                this.roleRepository.save(role);
            }
        }

        User adminUser = this.userService.addUser(new User("CHIEN", "NGUYEN DUC", "admin", "admin@onionit.com", "admin"));
        User khaiUser = this.userService.addUser(new User("KHAI", "MAU DUY", "khai", "khai@onionit.com", "khai"));
        User duyUser = this.userService.addUser(new User("DUY", "LE HONG", "duy", "duy@onionit.com", "duy"));
        List<User> users = Arrays.asList(adminUser, khaiUser, duyUser);

        this.userService.giveAdmin(adminUser.getUsername());

        Random rand = new Random();
        for (User user : users) {
            if (user.getCards().isEmpty()) {
                int cardAdded = 0;
                while (cardAdded == 0 || rand.nextInt(5) > 1) {
                    CardRequest cardRequest = new CardRequest() {
                        {
                            setRandomCode();
                            setName(user.getFullName());
                            setExpiredInYears(3);
                            setAmount(rand.nextLong(1000 * 1000) * 1000);
                        }
                    };
                    ResponseEntity<Card> result = this.cardService.addCard(cardRequest, UserPrincipal.create(user));
                    cardAdded++;

                    Card card = Objects.requireNonNull(result.getBody());
                    if (card.getExchanges().isEmpty()) {
                        int exchangeAdded = 0;
                        while (exchangeAdded == 0 || rand.nextInt(10) > 1) {
                            ExchangeRequest exchangeRequest = new ExchangeRequest() {
                                {
                                    setAmount(rand.nextInt(10 * 1000) * 1000);
                                    setMessage("Fake exchange");
                                    setCardId(card.getId());
                                }
                            };
                            this.exchangeService.addExchange(exchangeRequest, UserPrincipal.create(user));
                            exchangeAdded++;
                        }
                    }
                }
            }
        }
    }
}