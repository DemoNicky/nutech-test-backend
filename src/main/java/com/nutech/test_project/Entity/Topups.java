package com.nutech.test_project.Entity;

import com.nutech.test_project.Entity.Enum.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_topups")
public class Topups extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;
}
