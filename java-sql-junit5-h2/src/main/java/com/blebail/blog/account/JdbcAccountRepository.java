package com.blebail.blog.account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class JdbcAccountRepository implements AccountRepository {

    private final DataSource dataSource;

    public JdbcAccountRepository(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
    }

    @Override
    public Set<Account> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                Set<Account> accounts = new HashSet<>();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM account");

                while (resultSet.next()) {
                    accounts.add(fromResultSet(resultSet));
                }

                return accounts;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM account WHERE username = ?")) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Account> findInactives() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                Set<Account> inactiveAccounts = new HashSet<>();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE active = false");

                while (resultSet.next()) {
                    inactiveAccounts.add(fromResultSet(resultSet));
                }

                return inactiveAccounts;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Account fromResultSet(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getString("id"),
                resultSet.getString("username"),
                resultSet.getBoolean("active")
        );
    }
}
