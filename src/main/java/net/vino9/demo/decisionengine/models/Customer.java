package net.vino9.demo.decisionengine.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Customer {
    private String customerId;
    private String typeCode;
}
