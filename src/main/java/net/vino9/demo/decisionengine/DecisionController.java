package net.vino9.demo.decisionengine;

import lombok.Getter;
import lombok.Setter;
import net.vino9.demo.decisionengine.models.Account;
import net.vino9.demo.decisionengine.models.Customer;
import net.vino9.demo.decisionengine.models.Decision;
import net.vino9.demo.decisionengine.service.DecisionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DecisionController {
    DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping(path = "/decision", consumes = "application/json", produces = "application/json")
    public Decision getDecision(@RequestBody DecisionRequestPayload payload) {
        return decisionService.getDecision(payload.getCustomer(), payload.getAccounts());
    }
}

@Getter
@Setter
class DecisionRequestPayload {
    private Customer customer;
    private List<Account> accounts;
}