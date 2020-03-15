package com.blebail.blog.sample1.fixtures;

import com.blebail.blog.sample1.account.Account;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public final class CustomFixtures {

    public static final Account johnDoeAccount =
            new Account("59b2c4d8-75f8-4ef0-8e2e-f577da66182a", "johndoe", false);

    public static Operation insertJohnDoeAccount() {
        return Operations.insertInto("account")
                .columns("id", "username", "active")
                .values(johnDoeAccount.id(), johnDoeAccount.username(), johnDoeAccount.isActive())
                .build();
    }
}
