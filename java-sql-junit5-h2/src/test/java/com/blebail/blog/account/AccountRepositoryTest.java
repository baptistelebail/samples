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
                    SharedFixtures.insertDefaultAccounts()
            )
    );

    @BeforeEach
    void setUp() {
        accountRepository = new JdbcAccountRepository(sqlMemoryDatabase.dataSource());
    }

    @Test
    void shouldFindAllAccounts() {
        Set<Account> accounts = accountRepository.findAll();

        Assertions.assertThat(accounts).containsOnly(SharedFixtures.adminAccount, SharedFixtures.publisherAccount);
    }

    @Test
    void shouldFindAccountByUsername() {
        Optional<Account> maybeAdminAccount = accountRepository.findByUsername("admin");

        Assertions.assertThat(maybeAdminAccount).contains(SharedFixtures.adminAccount);
    }

    @Test
    void shouldFindInactiveAccounts() {
        sqlFixture.inject(CustomFixtures.insertJohnDoeAccount());

        Set<Account> inactiveAccounts = accountRepository.findInactives();

        Assertions.assertThat(inactiveAccounts).containsOnly(CustomFixtures.johnDoeAccount);
    }
}
