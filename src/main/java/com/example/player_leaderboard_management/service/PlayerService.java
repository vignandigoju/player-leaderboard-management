package com.example.player_leaderboard_management.service;


import com.example.player_leaderboard_management.entity.Player;
import com.example.player_leaderboard_management.exception.ServiceException;
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
        try {
            player.setPassword(passwordEncoder.encode(player.getPassword()));
            return playerRepository.save(player);
        } catch (Exception e) {
            throw new ServiceException("Error saving player: " + e.getMessage(), e);
        }
    }

    // Get player by ID
    public Optional<Player> getPlayerById(Long id) {
        try {
            return playerRepository.findById(id);
        } catch (Exception e) {
            throw new ServiceException("Error retrieving player with ID " + id, e);
        }
    }

    // Get all players
    public List<Player> getAllPlayers() {
        try {
            return playerRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error fetching players", e);
        }
    }

    // Delete player by ID
    public void deletePlayer(Long id) {
        try {
            playerRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Error deleting player with ID " + id, e);
        }
    }
}
