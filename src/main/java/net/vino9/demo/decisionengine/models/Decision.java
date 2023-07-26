package net.vino9.demo.decisionengine.models;

import java.util.ArrayList;
import java.util.List;

public class Decision {
    private Boolean isCustomerEligible = Boolean.TRUE;
    private final ArrayList<String> eligibleAccounts = new ArrayList<>();
    private final int flag = 1;

    public void addAccount(String account, String flag) {
        if (flag.equalsIgnoreCase("Y")) {
            eligibleAccounts.add(account);
        }
    }

    public void markCustomerIneligible(String flag) {
        if (flag.equalsIgnoreCase("NO")
                || flag.equalsIgnoreCase("False")
                || flag.equalsIgnoreCase("N")) {
            isCustomerEligible = Boolean.FALSE;
        }
    }

    public boolean isCustomerEligible() {
        return isCustomerEligible;
    }

    public int getFlag() {
        return flag;
    }

    public List<String> getEligibleAccounts() {
        return eligibleAccounts;
    }
}
