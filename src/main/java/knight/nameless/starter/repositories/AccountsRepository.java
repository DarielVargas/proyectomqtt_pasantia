package knight.nameless.starter.repositories;

import knight.nameless.starter.entities.Accounts;
import knight.nameless.starter.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByPin(String pin);

    Optional<Accounts> findByPinAndRole(String pin, Role role);
}
