package knight.nameless.starter.repositories;

import knight.nameless.starter.entities.Accounts;
import knight.nameless.starter.entities.LoginActivityLog;
import knight.nameless.starter.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoginActivityLogRepository extends JpaRepository<LoginActivityLog, Long> {

    Optional<LoginActivityLog> findFirstByAccountAndLogoutTimeIsNull(Accounts accounts);

    List<LoginActivityLog> findAllByMachine_IdAndLogoutTimeIsNull(int machineId);

    Optional<LoginActivityLog> findFirstByLogoutTimeIsNullAndMachine_Id(int machineId);

    Optional<LoginActivityLog> findFirstByLogoutTimeIsNullAndMachine_IdAndAccount_Role(int machineId, Role role);

    Optional<LoginActivityLog> findFirstByMachine_IdAndAccount_RoleOrderByLoginTimeDesc(int machineId, Role role);

    Optional<LoginActivityLog> findFirstByMachine_IdAndAccount_RoleAndLoginTimeGreaterThanEqualAndLoginTimeLessThanEqualOrderByLoginTime(int machineId, Role account_role, LocalDateTime loginTimeStart, LocalDateTime loginTimeEnd);
}
