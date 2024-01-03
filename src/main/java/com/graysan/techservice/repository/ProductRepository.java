package com.graysan.techservice.repository;

import com.graysan.techservice.model.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Product> getAll(){
        String sql = "SELECT * FROM public.\"PRODUCT\" order by \"id\" asc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
    }

    public Long getProductIdByName(String productName){
        String sql = "SELECT \"id\" FROM public.\"PRODUCT\" WHERE \"name\" = :PRODUCTNAME";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("PRODUCTNAME", productName);
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Long.class);

    }
}
