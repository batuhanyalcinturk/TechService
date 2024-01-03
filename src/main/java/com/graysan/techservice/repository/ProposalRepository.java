package com.graysan.techservice.repository;

import com.graysan.techservice.dto.ProposalAdminDto;
import com.graysan.techservice.dto.ProposalDto;
import com.graysan.techservice.model.Proposal;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProposalRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ProposalRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, UserRepository userRepository, ProductRepository productRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Proposal> getAllForUser(){
        String sql = "SELECT * FROM \"PROPOSAL\" WHERE \"user_id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", userRepository.getCurrentUserId());
        return namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Proposal.class));
    }

    public List<ProposalAdminDto> getAllDto(){
        String sql = "SELECT \"PROPOSAL\".id, \"PROPOSAL\".note, \"PROPOSAL\".price, \"PROPOSAL\".status, \"PROPOSAL\".user_id, \"PROPOSAL\".product_id, \"USERS\".username, \"PRODUCT\".name FROM \"PROPOSAL\" " +
                "INNER JOIN \"USERS\" ON \"USERS\".id = \"PROPOSAL\".user_id " +
                "INNER JOIN \"PRODUCT\" ON \"PRODUCT\".id = \"PROPOSAL\".product_id";

        RowMapper<ProposalAdminDto> rowMapper = new RowMapper<ProposalAdminDto>() {
            @Override
            public ProposalAdminDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                ProposalAdminDto proposalAdminDto = new ProposalAdminDto();
                proposalAdminDto.setId(rs.getLong("id"));
                proposalAdminDto.setNote(rs.getString("note"));
                proposalAdminDto.setPrice(rs.getLong("price"));
                proposalAdminDto.setStatus(rs.getString("status"));
                proposalAdminDto.setUser_id(rs.getLong("user_id"));
                proposalAdminDto.setProduct_id(rs.getLong("product_id"));
                proposalAdminDto.setUsername(rs.getString("username"));
                proposalAdminDto.setName(rs.getString("name"));
                return proposalAdminDto;
            }
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    public ProposalAdminDto getAllDtoById(long id) {
        String sql = "SELECT \"PROPOSAL\".id, \"PROPOSAL\".note, \"PROPOSAL\".price, \"PROPOSAL\".status, \"PROPOSAL\".user_id, \"PROPOSAL\".product_id, \"USERS\".username, \"PRODUCT\".name AS \"ProductName\" FROM \"PROPOSAL\" " +
                "INNER JOIN \"USERS\" ON \"USERS\".id = \"PROPOSAL\".user_id " +
                "INNER JOIN \"PRODUCT\" ON \"PRODUCT\".id = \"PROPOSAL\".product_id where \"PROPOSAL\".id= ?";

        RowMapper<ProposalAdminDto> rowMapper = new RowMapper<ProposalAdminDto>() {
            @Override
            public ProposalAdminDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                ProposalAdminDto proposalAdminDto = new ProposalAdminDto();
                proposalAdminDto.setId(rs.getLong("id"));
                proposalAdminDto.setNote(rs.getString("note"));
                proposalAdminDto.setPrice(rs.getLong("price"));
                proposalAdminDto.setStatus(rs.getString("status"));
                proposalAdminDto.setUser_id(rs.getLong("user_id"));
                proposalAdminDto.setProduct_id(rs.getLong("product_id"));
                proposalAdminDto.setUsername(rs.getString("username"));
                proposalAdminDto.setName(rs.getString("name"));
                return proposalAdminDto;
            }
        };
        List<ProposalAdminDto> result = jdbcTemplate.query(sql, rowMapper, id);
        if (result.size() == 1) {
            return result.getFirst();
        } else {
            return null;
        }
    }

    public boolean updateTrueStatus(long id)
    {
        String sql = "UPDATE public.\"PROPOSAL\" SET \"status\"=:STATUS WHERE \"id\"=:ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID",id );
        paramMap.put("STATUS","onaylandı");
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }
    public boolean updateFalseStatus(long id)
    {
        String sql = "UPDATE public.\"PROPOSAL\" SET \"status\"=:STATUS WHERE \"id\"=:ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID",id );
        paramMap.put("STATUS","reddedildi");
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public Proposal getById(long id) {
        String sql = "Select * From \"PROPOSAL\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return namedParameterJdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Proposal.class));
    }

    public boolean deleteById(long id) {
        String sql = "Delete From \"PROPOSAL\" Where \"id\" = :ID And \"user_id\" = :USER_ID";
        Map<String, Long> params = new HashMap<>();
        params.put("ID", id);
        params.put("USER_ID", userRepository.getCurrentUserId());

        return namedParameterJdbcTemplate.update(sql, params) == 1;
    }

    public boolean save(Proposal proposal) {
        String sql = "Insert Into \"PROPOSAL\"(\"note\", \"price\", \"user_id\", \"status\", \"product_id\") Values(:NOTE, :PRICE, :USER_ID, 'bekliyor', :PRODUCT_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("NOTE", proposal.getNote());
        params.put("PRICE", proposal.getPrice());
        params.put("USER_ID", userRepository.getCurrentUserId());
        params.put("PRODUCT_ID", proposal.getProductId());

        return namedParameterJdbcTemplate.update(sql, params) == 1;
    }
}
