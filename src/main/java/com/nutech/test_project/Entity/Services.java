package com.nutech.test_project.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_services")
@Data
public class Services extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "service_code", length = 20, nullable = false, unique = true)
    private String serviceCode;

    @Column(name = "service_name", length = 20, nullable = false)
    private String serviceName;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String urlImage;

    @OneToMany(mappedBy = "services", fetch = FetchType.LAZY)
    private List<Transaction> transactions;


}
