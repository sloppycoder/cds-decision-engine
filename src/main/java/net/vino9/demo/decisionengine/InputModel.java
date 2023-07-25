package net.vino9.demo.decisionengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputModel {
    private Customer customer;
    private String eligibility;
}
