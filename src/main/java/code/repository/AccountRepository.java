package code.repository;

import code.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

    Account findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Account a set a.name = :name, a.dob = :dob, a.gender = :gender, a.address = :address, a.phone = :phone where a.id = :id")
    void update(@Param("name") String name, @Param("dob") Date dob, @Param("gender") String gender, @Param("address") String address, @Param("phone") String phone, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Account a set a.password = :password where a.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);
}
