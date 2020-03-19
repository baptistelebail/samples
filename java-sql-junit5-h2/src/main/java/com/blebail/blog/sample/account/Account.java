package com.blebail.blog.sample.account;

import java.util.Objects;

public final class Account {

    private final String id;

    private final String username;

    private final boolean active;

    public Account(String id, String username, boolean active) {
        this.id = Objects.requireNonNull(id);
        this.username = Objects.requireNonNull(username);
        this.active = active;
    }

    public String id() {
        return id;
    }

    public String username() {
        return username;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return active == account.active &&
                Objects.equals(id, account.id) &&
                Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, active);
    }
}
