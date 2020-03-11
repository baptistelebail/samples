package com.blebail.blog.account;

import java.util.Optional;
import java.util.Set;

public interface AccountRepository {

    Optional<Account> findByUsername(String username);

    Set<Account> findInactives();
}
