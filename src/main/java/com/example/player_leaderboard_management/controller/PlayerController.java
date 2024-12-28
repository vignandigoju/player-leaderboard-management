package com.example.player_leaderboard_management.controller;

import com.example.player_leaderboard_management.dto.AuthRequest;
import com.example.player_leaderboard_management.entity.Player;
import com.example.player_leaderboard_management.service.JwtService;
import com.example.player_leaderboard_management.service.LeaderboardService;
import com.example.player_leaderboard_management.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LeaderboardService leaderboardService;

    // Create a new player
    @PostMapping("/players/create")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player savedPlayer = playerService.savePlayer(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    // Get player by ID
    @GetMapping("/players/getById/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        Optional<Player> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all players
    @GetMapping("/players/getAll")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    // Update player
    @PutMapping("/players/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        Optional<Player> existingPlayer = playerService.getPlayerById(id);
        if (existingPlayer.isPresent()) {
            player.setId(id);
            return ResponseEntity.ok(playerService.savePlayer(player));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete player
    @DeleteMapping("/players/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    // Get top N players by score
    @GetMapping("/leaderboard/top/{n}")
    public List<Player> getTopPlayers(@PathVariable int n) {
        return leaderboardService.getTopPlayers(n);
    }

    // Get entire leaderboard sorted by score
    @GetMapping("/leaderboard")
    public List<Player> getLeaderboard() {
        return leaderboardService.getLeaderboard();
    }

    //login endpoint
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}
