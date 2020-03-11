package com.blebail.blog.fixtures;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public final class CustomFixtures {

    public static Operation accountJohnDoe() {
        return Operations.insertInto("account")
                .columns("id", "username", "active")
                .values("59b2c4d8-75f8-4ef0-8e2e-f577da66182a", "johndoe", false)
                .build();
    }
}
