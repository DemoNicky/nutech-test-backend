package com.nutech.test_project.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_banner")
@Data
public class Banner extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "banner_name", length = 20, unique = true, nullable = false)
    private String bannerName;

    @Column(name = "description", length = 35, nullable = false)
    private String description;

    @Column(nullable = false)
    private String urlImage;

}
