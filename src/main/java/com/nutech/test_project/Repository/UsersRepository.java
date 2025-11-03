package com.nutech.test_project.Repository;

import com.nutech.test_project.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    @Query(value = "SELECT * FROM tb_user WHERE email = :email", nativeQuery = true)
    Optional<Users> findByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_user (id, first_name, last_name, email, password, url_image, created_at) " +
            "VALUES (UUID(), :firstName, :lastName, :email, :password, 'https://yoururlapi.com/profile.jpeg', NOW())",
            nativeQuery = true)
    void registerUser(@Param("firstName") String firstName,
                      @Param("lastName") String lastName,
                      @Param("email") String email,
                      @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_user SET first_name = :firstName, last_name = :lastName WHERE email = :email", nativeQuery = true)
    int updateUser(@Param("firstName") String firstName,
                   @Param("lastName") String lastName,
                   @Param("email") String email);


    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_user SET url_image = :imageUrl, updated_at = NOW() WHERE email = :email", nativeQuery = true)
    int updateImage(@Param("imageUrl") String imageUrl, @Param("email") String email);
}
