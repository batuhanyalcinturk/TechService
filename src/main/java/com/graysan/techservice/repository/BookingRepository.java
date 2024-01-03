package com.graysan.techservice.repository;

import com.graysan.techservice.model.Booking;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final ServicesRepository servicesRepository;

    public BookingRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository, ServicesRepository servicesRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.servicesRepository = servicesRepository;
    }

    public long getCurrentId() {
        try {
            long numb = jdbcTemplate.queryForObject("Select Max(\"id\") + 1 From \"BOOKING\"", new MapSqlParameterSource(), Long.class);
            return numb;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public long getLastId() {
        try {
            long numb = jdbcTemplate.queryForObject("Select Max(\"id\") From \"BOOKING\"", new MapSqlParameterSource(), Long.class);
            return numb;
        } catch (Exception e) {
            return 1;
        }
    }

    public List<Booking> getAll() {
        return jdbcTemplate.query("SELECT * FROM \"BOOKING\"", BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public Booking getById(long id) {
        String sql = "SELECT * FROM \"BOOKING\" WHERE \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);
        return jdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public List<Booking> getAllForUser(long userId) {
        String sql = "SELECT * FROM \"BOOKING\" WHERE \"user_id\" = :USER_ID ORDER BY \"booking_date\" ASC";
        Map<String, Long> param = new HashMap<>();
        param.put("USER_ID", userId);
        return jdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public boolean deleteById(long id) {
        long currentUserId = userRepository.getCurrentUserId();
        Booking booking = getById(id);
        if(booking.getUser_id() == currentUserId) {
            String sql = "DELETE FROM \"BOOKING\" WHERE \"id\" = :ID";
            Map<String, Long> param = new HashMap<>();
            param.put("ID", id);
            return jdbcTemplate.update(sql, param) == 1;
        }else{
            return false;
        }
    }

    public boolean save(Booking booking) {
        Date date = getBookingDate(booking);

        String sql = "INSERT INTO public.\"BOOKING\"(note, booking_date, status, service_id, user_id) VALUES (:NOTE, :BOOKING_DATE, :STATUS, :SERVICE_ID, :USER_ID)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NOTE", booking.getNote());
        paramMap.put("BOOKING_DATE", date);
        paramMap.put("STATUS", "PENDING");
        paramMap.put("SERVICE_ID", booking.getService_id());
        paramMap.put("USER_ID", userRepository.getCurrentUserId());

        return jdbcTemplate.update(sql, paramMap) == 1;
    }

    public Date getBookingDate(Booking booking) {
        Date date = new Date(); // Start with the current date
        Calendar calendar = Calendar.getInstance();

        while (true) {
            if (isAvailable(booking, date)) {
                return date; // Return the date if it has enough available hours
            }
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Move to the next day
            date = calendar.getTime();
        }
    }

    public int sumOfHours(Date date) {
        String sql = "Select SUM(\"duration\") From \"SERVICE\" Inner Join \"BOOKING\" On \"BOOKING\".\"service_id\" = \"SERVICE\".\"id\" Where \"BOOKING\".\"booking_date\" = :SYSTEM_DATE";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("SYSTEM_DATE", date);

        Integer sum = jdbcTemplate.queryForObject(sql, parameters, Integer.class); // Use the parameters object
        if (sum == null) {
            return 0; // Use 0 hours if system has no record
        }
        return sum;
    }

    public boolean isAvailable(Booking booking, Date date) {
        int currentBookingDuration = servicesRepository.getServiceDurationById(booking.getService_id());
        int totalBookingDurationInQueue = sumOfHours(date);

        if(totalBookingDurationInQueue + currentBookingDuration > 10)
            return false;
        else
            return true;
    }

    public List<Booking> sortDescBooking()
    {
        String sql = "select * from public.\"BOOKING\" order by \"booking_date\" desc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public List<Booking> sortAscBooking()
    {
        String sql = "select * from public.\"BOOKING\" order by \"booking_date\" asc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public List<Booking> searchBookingsByUserName(String name){
        String sql = "SELECT  id, note, booking_date, status, service_id, user_id FROM public.\"BOOKING\" " +
                "INNER JOIN \"USER\" ON \"BOOKING\".\"user_id\" = \"USER\".\"id\" WHERE \"USER\".\"name\" = :NAME";
        Map<String, String> param = new HashMap<>();
        param.put("NAME", name);
        return jdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public boolean updateToProcessing(long id){
        String sql = "UPDATE public.\"BOOKING\" SET \"status\" = 'PROCESSING' WHERE \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);
        return jdbcTemplate.update(sql, param) == 1;
    }

    public boolean updateToDone(long id){
        String sql = "UPDATE public.\"BOOKING\" SET \"status\" = 'DONE' WHERE \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);
        return jdbcTemplate.update(sql, param) == 1;
    }

}
