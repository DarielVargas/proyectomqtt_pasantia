package knight.nameless.starter.services;

import knight.nameless.starter.entities.Accounts;
import knight.nameless.starter.entities.enums.Role;
import knight.nameless.starter.repositories.AccountsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsServices {

    private final AccountsRepository accountsRepository;

    public AccountsServices(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Optional<Accounts> findByPin(String pin) {
        return accountsRepository.findByPin(pin);
    }

    public Optional<Accounts> findByPinAndRole(String pin, Role role) {
        return accountsRepository.findByPinAndRole(pin, role);
    }
}
