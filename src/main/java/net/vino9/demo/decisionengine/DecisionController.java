package net.vino9.demo.decisionengine;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DecisionController {
    DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping(path = "/decision", consumes = "application/json", produces = "application/json")
    public Map<String, String> getDecision(@RequestBody InputModel input) {
        // execute the decision, the result will be in the model
        decisionService.getDecision(input);
        Map<String, String> response = Map.of("decision", input.getEligibility());
        return response;
    }
}
