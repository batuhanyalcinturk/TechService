package com.graysan.techservice.repository;

import com.graysan.techservice.model.MyUser;
import com.graysan.techservice.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public List<GrantedAuthority> getUserRoles(String username) {
        String sql = "SELECT \"authority\" FROM public.\"AUTHORITIES\" WHERE \"username\" = :USERNAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("USERNAME", username);
        List<String> list = namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : list) {
            roles.add(new Role(role));
        }
        return roles;
    }

    public MyUser getByUsername(String username) {
        String sql = "SELECT * FROM public.\"USERS\" WHERE \"username\" = :USERNAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("USERNAME", username);
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(MyUser.class));
    }

    public boolean save(MyUser myUser){
        String insertUserSql = "INSERT INTO public.\"USERS\"(\"username\", \"password\", \"email\") VALUES (:username, :password, :email)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("username", myUser.getUsername());
        paramMap.put("email", myUser.getEmail());
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        paramMap.put("password", myUser.getPassword());
        boolean result = namedParameterJdbcTemplate.update(insertUserSql, paramMap) == 1;

        String insertRoleSql = "INSERT INTO public.\"AUTHORITIES\"(\"username\", \"authority\") VALUES (:username, :authority)";
        Map<String,Object> paramMap2 = new HashMap<>();
        paramMap2.put("username", myUser.getUsername());
        paramMap2.put("authority", "ROLE_USER");

        if(result) {
            return namedParameterJdbcTemplate.update(insertRoleSql, paramMap2) == 1;
        }
        return false;
    }

    public List<MyUser> getAll(){
        String sql = "SELECT * FROM public.\"USERS\" order by \"id\" asc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MyUser.class));
    }

    public String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }

        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            return ((UserDetails)principal).getUsername();
        }else{
            return principal.toString();
        }
    }

    public long getCurrentUserId(){
        String username = getCurrentUsername();
        String sql = "SELECT \"id\" FROM public.\"USERS\" WHERE \"username\" = :username";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("username", username);

        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Long.class);
    }
}
