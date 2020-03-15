package com.blebail.blog.sample1.fixtures;

import com.blebail.blog.sample1.account.Account;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public final class SharedFixtures {

    public static final Account adminAccount =
            new Account("a288fb96-8155-4537-b714-c6098131cee3", "admin", true);

    public static final Account publisherAccount =
            new Account("22a73d0d-84ed-4c85-9688-facb309faa7e", "publisher", true);

    public static Operation insertDefaultAccounts() {
        return Operations.sequenceOf(
                Operations.deleteAllFrom("account"),
                Operations.insertInto("account")
                        .columns("id", "username", "active")
                        .values(adminAccount.id(), adminAccount.username(), adminAccount.isActive())
                        .values(publisherAccount.id(), publisherAccount.username(), publisherAccount.isActive())
                        .build()
        );
    }
}
