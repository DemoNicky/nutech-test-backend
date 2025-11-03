package com.nutech.test_project.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_balances")
@Data
public class Balances extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

}
