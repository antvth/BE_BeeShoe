package com.example.duantotnghiep.config;

import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/hoa-don-chi-tiet/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/ql-khach-hang/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/manager/hoa-don/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/khach-hang/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/nhan-vien/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name())
                        .requestMatchers("/api/chi-tiet-sp/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/gio-hang/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/gio-hang-chi-tiet/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/gio-hang-chi-tiet-not-login/**").permitAll()
                        .requestMatchers("/api/gio-hang-not-login/**").permitAll()
                        .requestMatchers("/api/khach-hang-not-login/**").permitAll()
                        .requestMatchers("/api/checkout-not-login/**").permitAll()
                        .requestMatchers("/api/v1/san-pham/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/product/**").permitAll()
                        .requestMatchers("/api/v1/don-hang/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/hoa-don/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/kieu-de/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/mau-sac/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/size/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/thuong-hieu/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/danh-muc/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/xuat-xu/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/chat-lieu/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/san-pham-giam-gia/**").permitAll()
                        .requestMatchers("/api/v1/transaction/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/giam-gia/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/invoice/**").permitAll()
                        .requestMatchers("/api/v1/chat-lieu/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/payment/**").permitAll()
                        .requestMatchers("/api/v1/voucher/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/voucher-counter/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/v1/san-pham-chi-tiet/**").permitAll()
                        .requestMatchers("/api/v1/account/**").permitAll()
                        .requestMatchers("/api/v1/images/**").permitAll()
                        .requestMatchers("/api/v1/don-hang-khach-hang/**").permitAll()
                        .requestMatchers("/api/v1/don-hang-khach-hang-chi-tiet/**").permitAll()
                        .requestMatchers("/api/v1/voucher-login/**").permitAll()
                        .requestMatchers("/api/v1/audilog/**").permitAll()
                        .requestMatchers("/api/v1/thuoc-tinh/**").permitAll()
                        .requestMatchers("/api/v1/pdf/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/qrcode/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name(), TypeAccountEnum.STAFF.name())
                        .requestMatchers("/api/thong-ke/**").hasAnyAuthority(TypeAccountEnum.ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(
                        sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
