package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.repositories;

import com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findByEmail(String email);
}
