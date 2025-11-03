package com.nutech.test_project.Repository;

import com.nutech.test_project.Entity.Balances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BalancesRepository extends JpaRepository<Balances, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_balances (id, user_id, amount, created_at, updated_at)"+
            "VALUES (UUID(), :user_id, 0, NOW(), NOW())",
            nativeQuery = true)
    void createBalance(@Param("user_id") String id);


    @Query(value = "SELECT * FROM tb_balances WHERE user_id= :user_id", nativeQuery = true)
    Optional<Balances> findBalanceByUserId(@Param("user_id") String user_id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE tb_balances " +
            "SET amount = :finalAmount, updated_at = NOW() " +
            "WHERE user_id = :user_id", nativeQuery = true)
    int afterPay(@Param("user_id") String userId, @Param("finalAmount") BigDecimal finalAmount);
}
