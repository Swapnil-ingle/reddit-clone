package com.github.swapnil.reddit.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.swapnil.reddit.dto.AuthenticationResponse;
import com.github.swapnil.reddit.dto.LoginRequest;
import com.github.swapnil.reddit.dto.RefreshTokenRequest;
import com.github.swapnil.reddit.dto.RegisterRequest;
import com.github.swapnil.reddit.exception.SpringRedditException;
import com.github.swapnil.reddit.model.NotificationEmail;
import com.github.swapnil.reddit.model.User;
import com.github.swapnil.reddit.model.VerificationToken;
import com.github.swapnil.reddit.repository.UserRepository;
import com.github.swapnil.reddit.repository.VerificationTokenRepository;
import com.github.swapnil.reddit.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepo;

	private final VerificationTokenRepository verificationTokenRepo;

	private final MailService mailSvc;

	private final RefreshTokenService refreshTokenSvc;

	private final AuthenticationManager authManager;

	private final JwtProvider jwtProvider;

	@Transactional
	public void signUp(RegisterRequest request) throws SpringRedditException {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);

		userRepo.save(user);

		String token = generateVerificationToken(user);

		mailSvc.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),
				"Thanks for signing up.\n" + "\n" + "Please click the below URL to to activate your account:\n" + "\n"
						+ "http://localhost:8080/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();

		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepo.save(verificationToken);

		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepo.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));
		fetchUserAndEnable(verificationToken.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new SpringRedditException("User not found with name " + username));
		user.setEnabled(true);
		userRepo.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);

		return AuthenticationResponse.builder().authenticationToken(token)
				.refreshToken(refreshTokenSvc.generateRefreshToken().getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(loginRequest.getUsername()).build();
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		return userRepo.findByUsername(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}

	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		refreshTokenSvc.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenRequest.getRefreshToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(refreshTokenRequest.getUsername())
				.build();
	}
}
