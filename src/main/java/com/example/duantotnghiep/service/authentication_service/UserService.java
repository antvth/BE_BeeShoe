package com.example.duantotnghiep.service.authentication_service;

import com.example.duantotnghiep.entity.RefreshToken;
import com.example.duantotnghiep.request.ForgotPassword;
import com.example.duantotnghiep.request.LoginRequest;
import com.example.duantotnghiep.request.RegisterRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TokenResponse;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    TokenResponse login(LoginRequest loginRequest);

    MessageResponse register(RegisterRequest registerRequest);

    MessageResponse forgotPassword(String email);

    MessageResponse datLaiMatKhau(UUID id, String password);

    MessageResponse sendConfirmEmailForgotPassWord(String email, UUID id);

    RefreshToken createToken(String username);

    RefreshToken verifyExpiration(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    String confirmationCodeRegister();

    String confirmationCodeForgotPassWord();

}
