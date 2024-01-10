package com.digilab.springboottokencookies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digilab.springboottokencookies.models.RefreshToken;
import com.digilab.springboottokencookies.models.User;
import com.digilab.springboottokencookies.repos.RefreshTokenRepository;
import com.digilab.springboottokencookies.repos.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

   // @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs=180000L; // 3 minutes in milliseconds;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            refreshToken.setUser(user.get());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());

            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            // Handle user not found
            throw new RuntimeException("User not found");
        }
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(String userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return refreshTokenRepository.deleteByUser(user.get());
        } else {
            // Handle user not found
            throw new RuntimeException("User not found");
        }
    }
}