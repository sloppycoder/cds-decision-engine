package net.vino9.demo.decisionengine;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Customer {
    private String customerId;
    private String customerTypeCode;
    private String ownershipType;
    private String eligibility;
}
