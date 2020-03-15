package com.blebail.blog.sample1.account;

import java.util.Optional;
import java.util.Set;

public interface AccountRepository {

    Set<Account> findAll();

    Optional<Account> findByUsername(String username);

    Set<Account> findInactives();
}