package net.vino9.demo.decisionengine.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Account {
    private String accountNumber;
    private String productType;
    private String productCode;
    private String ownershipType;
    private String status;
}
