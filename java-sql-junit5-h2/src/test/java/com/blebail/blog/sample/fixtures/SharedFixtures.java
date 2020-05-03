package com.blebail.blog.sample.fixtures;

import com.blebail.blog.sample.account.Account;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public final class SharedFixtures {

    public static final Account adminAccount =
            new Account("admin_id", "admin", true);

    public static final Account userAccount =
            new Account("user_id", "user", true);

    public static Operation insertDefaultAccounts() {
        return Operations.sequenceOf(
                Operations.deleteAllFrom("account"),
                Operations.insertInto("account")
                        .columns("id", "username", "active")
                        .values(adminAccount.id(), adminAccount.username(), adminAccount.isActive())
                        .values(userAccount.id(), userAccount.username(), userAccount.isActive())
                        .build()
        );
    }
}
