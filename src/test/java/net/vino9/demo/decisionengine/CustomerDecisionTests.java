package net.vino9.demo.decisionengine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerDecisionTests {

    @Autowired
    private DecisionService decisionService;

    @Test
    void test_eligible_customer(){
        var customer1 = Customer.builder().customerTypeCode("CA").build();
        decisionService.getDecision(customer1);
        assertEquals("NO", customer1.getEligibility());

        var customer2 = Customer.builder().customerTypeCode("ZZ").ownershipType("INDIVIDUAL").build();
        decisionService.getDecision(customer2);
        assertEquals("YES", customer2.getEligibility());

    }
}
