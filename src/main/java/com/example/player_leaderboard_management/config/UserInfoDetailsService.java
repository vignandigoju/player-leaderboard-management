package com.example.player_leaderboard_management.config;

import com.example.player_leaderboard_management.entity.Player;
import com.example.player_leaderboard_management.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserInfoDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private PlayerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Player> userInfo = repository.findByName(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
