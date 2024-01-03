package com.graysan.techservice.repository;

import com.graysan.techservice.model.SaleLog;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SaleLogRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRepository userRepository;



    public SaleLogRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRepository userRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRepository = userRepository;
    }

    public boolean updateExistingSale(long id) {
        String sql = "UPDATE public.\"SALE\" SET \"is_sold\" = :IS_SOLD WHERE \"id\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        paramMap.put("IS_SOLD", true);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(SaleLog saleLog){
        String sql = "INSERT INTO public.\"SALE_LOG\"(sale_log_date, credit_card, sale_id, user_id) VALUES (:SALELOGDATE, :CREDITCARD, :SALEID, :USERID)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("SALELOGDATE", saleLog.getSaleLogDate());
        paramMap.put("CREDITCARD", saleLog.getCreditCardNumber());
        paramMap.put("SALEID", saleLog.getSaleId());
        paramMap.put("USERID", userRepository.getCurrentUserId());
        boolean isUpdate = updateExistingSale(saleLog.getSaleId());
        if (isUpdate){
            return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
        }
        return false;
    }




}
