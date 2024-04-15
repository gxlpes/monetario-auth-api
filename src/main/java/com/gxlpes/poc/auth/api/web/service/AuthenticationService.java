package com.gxlpes.poc.auth.api.web.service;

import com.gxlpes.poc.auth.api.web.request.AuthenticationRequest;
import com.gxlpes.poc.auth.api.web.request.ChangePasswordRequest;
import com.gxlpes.poc.auth.api.web.response.AuthenticationResponse;
import com.gxlpes.poc.auth.api.web.request.RegisterRequest;
import com.gxlpes.poc.auth.api.web.request.VerificationRequest;
import com.gxlpes.poc.auth.api.config.jwt.JwtUtils;
import com.gxlpes.poc.auth.api.models.Token;
import com.gxlpes.poc.auth.api.models.User;
import com.gxlpes.poc.auth.api.config.tfa.TwoFactorAuthenticationService;
import com.gxlpes.poc.auth.api.web.repository.TokenRepository;
import com.gxlpes.poc.auth.api.web.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager, TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new Exception("User with email " + request.getEmail() + " " + "already exists.");
        }

        User user = new User(request.getFirstname(), request.getLastname(), passwordEncoder.encode(request.getPassword()), request.getEmail(), request.getRole());

        user.setSecret(twoFactorAuthenticationService.generateNewSecret());
        var savedUser = userRepository.save(user);
        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken, twoFactorAuthenticationService.generateQrCodeImageUri(user.getSecret()));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("User not found"));

        if (twoFactorAuthenticationService.isOtpNotValid(user.getSecret(), request.getTfaCode())) {
            throw new BadCredentialsException("Invalid verification code");
        }

        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = new Token(null, jwtToken, "Bearer", false, false, user);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshToken, user)) {
                var accessToken = jwtUtils.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public AuthenticationResponse verifyCode(VerificationRequest verificationRequest) {
        User user = userRepository.findByEmail(verificationRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException(String.format("No user found with %S", verificationRequest.getEmail())));
        if (twoFactorAuthenticationService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {
            throw new BadCredentialsException("Code is not correct");
        }
        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    public void logout(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            storedToken.setLogout(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
