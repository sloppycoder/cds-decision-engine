package net.vino9.demo.decisionengine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerDecisionTests {

    @Autowired
    private DecisionService decisionService;

    @Test
    void test_eligible_customer(){
        var input = InputModel.builder().customer(customer1()).build();
        var result = decisionService.getDecision(input);
        assertEquals("YES", result.getEligibility());

    }

    private Customer customer1(){
        return Customer.builder().customerTypeCode("CAT1").ownershipType("INDIVIDUAL").build();
    }
}
