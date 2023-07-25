package net.vino9.demo.decisionengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private String customerId;
    private String customerTypeCode;
    private String ownershipType;
}
