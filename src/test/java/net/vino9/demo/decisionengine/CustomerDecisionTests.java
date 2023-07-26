package net.vino9.demo.decisionengine;

import net.vino9.demo.decisionengine.models.Account;
import net.vino9.demo.decisionengine.models.Customer;
import net.vino9.demo.decisionengine.models.Decision;
import net.vino9.demo.decisionengine.service.DecisionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerDecisionTests {

    @Autowired
    private DecisionService decisionService;

    @Test
    void test_eligible_customer_and_accounts(){
        // customer has eligible type code
        var customer = Customer.builder().customerId("123").typeCode("ZZ").build();

        var accounts = List.of(
                // account is eligible
                Account.builder()
                        .accountNumber("12301")
                        .productType("Saving")
                        .ownershipType("Individual")
                        .productCode("A9")
                        .status("Open")
                        .build(),
                // account is ineligible due to status
                Account.builder()
                        .accountNumber("12302")
                        .productType("Saving")
                        .ownershipType("Individual")
                        .productCode("A9")
                        .status("Closed")
                        .build()
        );

        Decision decision = decisionService.getDecision(customer, accounts);
        var eligibleAccounts = decision.getEligibleAccounts();

        assertTrue(decision.isCustomerEligible());
        assertTrue(eligibleAccounts.contains("12301"));
        assertFalse(eligibleAccounts.contains("12302"));
    }
}
