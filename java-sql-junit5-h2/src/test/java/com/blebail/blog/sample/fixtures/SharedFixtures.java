package com.blebail.blog.sample.fixtures;

import com.blebail.blog.sample.account.Account;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public final class SharedFixtures {

    public static final Account adminAccount =
            new Account("a288fb96-8155-4537-b714-c6098131cee3", "admin", true);

    public static final Account userAccount =
            new Account("22a73d0d-84ed-4c85-9688-facb309faa7e", "user", true);

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
