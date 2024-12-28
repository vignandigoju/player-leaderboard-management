package com.example.player_leaderboard_management.service;


import com.example.player_leaderboard_management.entity.Player;
import com.example.player_leaderboard_management.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create or update player
    public Player savePlayer(Player player) {
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        return playerRepository.save(player);
    }

    // Get player by ID
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    // Get all players
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Delete player by ID
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}