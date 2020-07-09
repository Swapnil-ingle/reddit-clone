package com.github.swapnil.reddit.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.swapnil.reddit.dto.AuthenticationResponse;
import com.github.swapnil.reddit.dto.LoginRequest;
import com.github.swapnil.reddit.dto.RefreshTokenRequest;
import com.github.swapnil.reddit.dto.RegisterRequest;
import com.github.swapnil.reddit.service.AuthService;
import com.github.swapnil.reddit.service.RefreshTokenService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authSvc;

	private final RefreshTokenService refreshTokenSvc;

	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		authSvc.verifyAccount(token);
		return new ResponseEntity<>("User Account Verified!", HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
		authSvc.signUp(registerRequest);
		return new ResponseEntity<>("User Registration Successful!", HttpStatus.OK);
	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authSvc.login(loginRequest);
	}

	@PostMapping("/refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authSvc.refreshToken(refreshTokenRequest);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		refreshTokenSvc.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return new ResponseEntity<>("Refresh token deleted successfully!", HttpStatus.OK);
	}

}
