package com.blebail.blog.sample1.account;

import com.blebail.blog.sample1.fixtures.CustomFixtures;
import com.blebail.blog.sample1.fixtures.SharedFixtures;
import com.blebail.blog.sample1.fixtures.SqlFixture;
import com.blebail.blog.sample1.fixtures.SqlMemoryDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @RegisterExtension
    public static SqlMemoryDatabase sqlMemoryDatabase = new SqlMemoryDatabase();

    @RegisterExtension
    public SqlFixture sqlFixture = new SqlFixture(
            sqlMemoryDatabase,
            SharedFixtures.insertDefaultAccounts()
    );

    @BeforeEach
    void setUp() {
        // Here JdbcAccountRepository is a plain simple implementation of AccountRepository,
        // nothing interesting in the context of this post
        accountRepository = new JdbcAccountRepository(sqlMemoryDatabase.dataSource());
    }

    @Test
    void shouldFindAllAccounts() {
        sqlFixture.readOnly();

        assertThat(accountRepository.findAll())
                .containsOnly(SharedFixtures.adminAccount, SharedFixtures.publisherAccount);
    }

    @Test
    void shouldFindAccountByUsername() {
        sqlFixture.readOnly();

        assertThat(accountRepository.findByUsername("admin"))
                .contains(SharedFixtures.adminAccount);
    }

    @Test
    void shouldFindInactiveAccounts() {
        sqlFixture.inject(CustomFixtures.insertInactiveAccount());

        assertThat(accountRepository.findInactives())
                .containsOnly(CustomFixtures.inactiveAccount);
    }
}
