package com.graysan.techservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graysan.techservice.model.MyUser;
import com.graysan.techservice.model.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        System.err.println("şifre kontrol");
        try
        {
            // here is /login
            MyUser creds = new ObjectMapper().readValue(req.getInputStream(), MyUser.class);
            // invoke userservice
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<GrantedAuthority>()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        // MY_SECRET_KEY
        User principal = ((User) auth.getPrincipal());

        String rolestring = principal.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.joining(","));
        // rolestring = ROLE_USER,ROLE_ADMIN,ROLE_CONTRIBUTOR
        String str = principal.getUsername() + "-" + rolestring;

        //JWT tokenın oluşturulması ve kaç dk sonra geçerliliğinin yitirilmesini yazdık , algoritmmasını da verdik
        String token = JWT.create().withSubject(str).withExpiresAt(new Date(System.currentTimeMillis() + 3000000)).sign(Algorithm.HMAC512("MY_SECRET_KEY".getBytes()));
        //		System.err.println(token);
        // token = 30948hgb57gbhg9wpuısgh==
        List<String> list = principal.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        TokenInfo body = new TokenInfo(principal.getUsername(), list, token);

        // I have to set content type to json, otherwise default stream will be used
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write(body.toString());
        res.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        System.err.println("Wrong password");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (failed.getClass() == DisabledException.class) {
            response.getWriter().write("User is disabled");
        }
        response.getWriter().flush();
    }
}
