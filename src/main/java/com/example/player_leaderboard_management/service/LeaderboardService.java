package com.example.player_leaderboard_management.service;

import com.example.player_leaderboard_management.entity.Player;
import com.example.player_leaderboard_management.exception.ServiceException;
import com.example.player_leaderboard_management.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private PlayerRepository playerRepository;

    // Get top N players sorted by score (descending)
    public List<Player> getTopPlayers(int topN) {
        try {
            List<Player> players = playerRepository.findAll();
            return players.stream()
                    .sorted(Comparator.comparingInt(Player::getScore).reversed()) // Sort by score (desc)
                    .limit(topN)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error fetching top " + topN + " players", e);
        }
    }

    // Get entire leaderboard sorted by score
    public List<Player> getLeaderboard() {
        try {
            List<Player> players = playerRepository.findAll();
            return players.stream()
                    .sorted(Comparator.comparingInt(Player::getScore).reversed()) // Sort by score (desc)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error fetching leaderboard", e);
        }
    }
}
