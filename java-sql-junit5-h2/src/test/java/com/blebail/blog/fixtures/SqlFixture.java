package com.blebail.blog.fixtures;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Objects;
import java.util.Optional;

public final class SqlFixture implements BeforeEachCallback {

    private final SqlMemoryDatabase sqlMemoryDatabase;

    private final Operation initialOperation;

    public SqlFixture(SqlMemoryDatabase sqlMemoryDatabase) {
        this(sqlMemoryDatabase, null);
    }

    public SqlFixture(SqlMemoryDatabase sqlMemoryDatabase, Operation initialOperation) {
        this.sqlMemoryDatabase = Objects.requireNonNull(sqlMemoryDatabase);
        this.initialOperation = Objects.requireNonNull(initialOperation);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        Optional.ofNullable(initialOperation)
                .ifPresent(this::inject);
    }

    public void inject(Operation operation) {
        new DbSetup(new DataSourceDestination(sqlMemoryDatabase.dataSource()), operation)
                .launch();
    }
}