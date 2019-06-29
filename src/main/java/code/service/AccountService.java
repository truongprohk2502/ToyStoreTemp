package code.service;

import code.model.Account;

public interface AccountService {

    Iterable<Account> findAll();

    Account findAccountByUsername(String username);

    Account findAccountByEmail(String email);

    String getPasswordById(Long id);

    void save(Account account);

    void update(Account account);

    void updatePassword(Long id, String password);
}
