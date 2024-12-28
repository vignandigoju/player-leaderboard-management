package com.example.player_leaderboard_management.repository;

import com.example.player_leaderboard_management.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByName(String username);

    boolean existsByEmail(String email);

    Optional<Player> findByEmail(String email);
    // You can define custom queries if needed (e.g., sorting by score)
}