package com.blebail.blog.account;

import com.blebail.blog.fixtures.CustomFixtures;
import com.blebail.blog.fixtures.SharedFixtures;
import com.blebail.blog.fixtures.SqlFixture;
import com.blebail.blog.fixtures.SqlMemoryDatabase;
import com.ninja_squad.dbsetup.Operations;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Optional;
import java.util.Set;

public final class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @RegisterExtension
    public static SqlMemoryDatabase sqlMemoryDatabase = new SqlMemoryDatabase();

    @RegisterExtension
    public SqlFixture sqlFixture = new SqlFixture(sqlMemoryDatabase,
            Operations.sequenceOf(
                    SharedFixtures.deleteAccounts(),
                    SharedFixtures.adminAccount()
            )
    );

    @BeforeEach
    void setUp() {
        accountRepository = new JdbcAccountRepository(sqlMemoryDatabase.dataSource());
    }

    @Test
    void shouldFindAccountByUsername() {
        Account expectedAccount = new Account("a288fb96-8155-4537-b714-c6098131cee3", "admin", true);

        Optional<Account> maybeAdminAccount = accountRepository.findByUsername("admin");

        Assertions.assertThat(maybeAdminAccount).contains(expectedAccount);
    }

    @Test
    void shouldFindInactiveAccounts() {
        sqlFixture.inject(CustomFixtures.accountJohnDoe());

        Account johndoeAccount = new Account("59b2c4d8-75f8-4ef0-8e2e-f577da66182a", "johndoe", false);

        Set<Account> inactiveAccounts = accountRepository.findInactives();

        Assertions.assertThat(inactiveAccounts).containsOnly(johndoeAccount);
    }
}
