package com.gxlpes.poc.auth.api.web.controller;

import com.gxlpes.poc.auth.api.web.request.ChangePasswordRequest;
import com.gxlpes.poc.auth.api.web.response.AuthenticationResponse;
import com.gxlpes.poc.auth.api.web.request.AuthenticationRequest;
import com.gxlpes.poc.auth.api.web.request.RegisterRequest;
import com.gxlpes.poc.auth.api.web.request.VerificationRequest;
import com.gxlpes.poc.auth.api.web.service.AuthenticationService;
import com.gxlpes.poc.auth.api.web.utils.ApiRoutes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(ApiRoutes.SYSTEM.AUTH)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(ApiRoutes.AUTH.REGISTER)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(ApiRoutes.AUTH.AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(ApiRoutes.AUTH.REFRESH_TOKEN)
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping(ApiRoutes.AUTH.VERIFY)
    public ResponseEntity<?> verifyCode(
            @RequestBody VerificationRequest verificationRequest
    ) {
        return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
    }

    @PostMapping(ApiRoutes.AUTH.LOGOUT_USER)
    public ResponseEntity<?> logoutUser(
            @RequestBody VerificationRequest verificationRequest
    ) {
        return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
    }

    @PatchMapping(ApiRoutes.AUTH.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        authenticationService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}