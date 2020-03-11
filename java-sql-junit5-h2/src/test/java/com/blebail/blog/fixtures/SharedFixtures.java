package com.blebail.blog.fixtures;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public final class SharedFixtures {

    public static Operation deleteAccounts() {
        return Operations.deleteAllFrom("account");
    }

    public static Operation adminAccount() {
        return Operations.insertInto("account")
                .columns("id", "username", "active")
                .values("a288fb96-8155-4537-b714-c6098131cee3", "admin", true)
                .build();
    }
}
