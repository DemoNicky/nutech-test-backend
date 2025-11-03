package com.nutech.test_project.Repository;

import com.nutech.test_project.Entity.Balances;
import com.nutech.test_project.Entity.Topups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface TopupsRepository extends JpaRepository<Topups, String> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE tb_balances " +
            "SET amount = amount + :balance, updated_at = NOW()" +
            "WHERE user_id = :user_id",
            nativeQuery = true)
    int saveBalance(@Param("user_id") String id, @Param("balance") BigDecimal balance);


    @Query(value = "SELECT * FROM tb_balances WHERE user_id = :user_id LIMIT 1", nativeQuery = true)
    Optional<Balances> getBalanceByUserId(@Param("user_id") String userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_topups (id, amount, status, users_id, created_at, updated_at) " +
            "VALUES (UUID(), :amount, 'PAYMENT', :userId, NOW(), NOW())",
            nativeQuery = true)
    void saveTopup(@Param("amount") BigDecimal topUpAmount, @Param("userId") String userId);
}
