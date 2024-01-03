package com.graysan.techservice.repository;

import com.graysan.techservice.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SaleRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public SaleRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Sale> getAll()
    {
        String sql = "SELECT * FROM public.\"SALE\" order by \"id\" asc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
    }

    public List<Sale> getAvailableSales()
    {
        String sql = "SELECT * FROM public.\"SALE\" WHERE \"is_sold\"= false ORDER BY \"id\" ASC";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
    }


    public boolean deleteById(long id)
    {
        String sql = "DELETE FROM public.\"SALE\" WHERE \"id\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Sale sale)
    {
        //String sql = "INSERT INTO public.\"SALE\"(\"note\", \"price\", \"product_id\") VALUES (:NOTE, :PRICE, :PRODUCT_ID)";
        String sql = "INSERT INTO public.\"SALE\"(note, price, product_id) VALUES (:NOTE, :PRICE, :PRODUCT_ID)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("NOTE", sale.getNote());
        paramMap.put("PRICE", sale.getPrice());
        paramMap.put("PRODUCT_ID", sale.getProduct_id());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public List<Sale> getSaleByProductId(Long productId) {
        String sql = "SELECT * FROM public.\"SALE\" WHERE \"is_sold\"= false AND \"product_id\" = :productId";
        Map<String, Long> param = new HashMap<>();
        param.put("productId", productId);

        return namedParameterJdbcTemplate.query(sql, param,BeanPropertyRowMapper.newInstance(Sale.class));
    }


}
