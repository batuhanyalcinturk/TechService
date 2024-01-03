package com.graysan.techservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.graysan.techservice.model.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token != null){
            try{
                String myToken = JWT.require(Algorithm.HMAC512("MY_SECRET_KEY"
                        .getBytes())).build().verify(token.replace("Bearer ", "")).getSubject();
                if(myToken != null){
                    String username = myToken.split("-")[0];
                    List<Role> auth = Arrays.stream(myToken.split("-")[1].split(",")).map(Role::new).toList();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, auth);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    filterChain.doFilter(request, response);
                }
            }catch (Exception e){
                System.err.println(e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Token exception => " + e.getMessage());
            }
        }else {
            System.err.println("No token but has header");
            filterChain.doFilter(request, response);
        }

    }
}
