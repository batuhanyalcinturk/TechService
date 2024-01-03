package com.graysan.techservice.repository;

import com.graysan.techservice.model.Services;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ServicesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ServicesRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Services> getAll(){
        return jdbcTemplate.query("SELECT * FROM public.\"SERVICE\"", BeanPropertyRowMapper.newInstance(Services.class));
    }

    public String getServiceNameById(long id){
        String sql = "SELECT \"name\" FROM public.\"SERVICE\" WHERE \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }

    public int getServiceDurationById(long id){
        String sql = "SELECT \"duration\" FROM public.\"SERVICE\" WHERE \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);
        return jdbcTemplate.queryForObject(sql, param, Integer.class);
    }
}
