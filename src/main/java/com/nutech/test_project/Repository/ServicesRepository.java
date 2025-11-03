package com.nutech.test_project.Repository;

import com.nutech.test_project.Entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services, String> {

    @Query(value = "SELECT * FROM tb_services WHERE service_code = :service_code LIMIT 1", nativeQuery = true)
    Optional<Services> finByServiceCode(@Param("service_code") String service_code);
}
