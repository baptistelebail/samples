package com.blebail.blog.sample.fixtures;

import com.blebail.blog.sample.account.Account;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public final class CustomFixtures {

    public static final Account inactiveAccount =
            new Account("inactive_id", "johndoe", false);

    public static Operation insertInactiveAccount() {
        return Operations.insertInto("account")
                .columns("id", "username", "active")
                .values(inactiveAccount.id(), inactiveAccount.username(), inactiveAccount.isActive())
                .build();
    }
}
