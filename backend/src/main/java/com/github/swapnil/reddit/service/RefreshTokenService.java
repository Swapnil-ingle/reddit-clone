package com.github.swapnil.reddit.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.swapnil.reddit.exception.SpringRedditException;
import com.github.swapnil.reddit.model.RefreshToken;
import com.github.swapnil.reddit.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepo;

	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(Instant.now());
		return refreshTokenRepo.save(refreshToken);
	}

	public void validateRefreshToken(String token) {
		refreshTokenRepo.findByToken(token).orElseThrow(() -> new SpringRedditException("Invalid Refresh Token"));
	}

	public void deleteRefreshToken(String token) {
		refreshTokenRepo.deleteByToken(token);
	}
}
