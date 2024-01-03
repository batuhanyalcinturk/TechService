package com.graysan.techservice.controller;

import com.graysan.techservice.model.Booking;
import com.graysan.techservice.repository.BookingRepository;
import com.graysan.techservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingController(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<List<Booking>> getAllForUser() {
        return ResponseEntity.ok(bookingRepository.getAllForUser(userRepository.getCurrentUserId()));
    }

    @GetMapping("/user/getById/{id}")
    public ResponseEntity<Booking> getById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(bookingRepository.getById(id));
    }

    @DeleteMapping("/user/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") long id) {
        try{
            boolean result = bookingRepository.deleteById(id);
            if(result){
                return ResponseEntity.ok("id: " + id + " Booking deleted successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id: " + id + " Booking is not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("id: " + id + " Booking could not be deleted");
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<String> appointment(@RequestBody Booking booking) {
        try{
            long max = bookingRepository.getCurrentId();
            booking.setId(max);
            boolean result = bookingRepository.save(booking);
            if(result){
                return ResponseEntity.ok("Booking saved successfully");
            }else {
                return ResponseEntity.internalServerError().body("Opss! Booking could not be saved");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Opss! Booking could not be saved");
        }
    }

    @GetMapping("/admin/sortBookings/{sortType}")
    public ResponseEntity<List<Booking>> sortBookings(@PathVariable(name = "sortType") String sortType) {
        try{
            if (sortType.equalsIgnoreCase("asc") || sortType.equalsIgnoreCase("ascending")) {
                return ResponseEntity.ok(bookingRepository.sortAscBooking());
            }else if (sortType.equalsIgnoreCase("desc") || sortType.equalsIgnoreCase("descending")) {
                return ResponseEntity.ok(bookingRepository.sortDescBooking());
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/admin/searchBookingByUsername/{username}")
    public ResponseEntity<List<Booking>> searchBookingsByUsername(@PathVariable(name = "username") String username) {
        try{
            List<Booking> bookings = bookingRepository.searchBookingsByUserName(username.toLowerCase());
            if (bookings != null){
                return ResponseEntity.ok(bookings);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/admin/bookingProcess/{id}")
    public ResponseEntity<String> updateStatusToProcess(@PathVariable(name = "id") long id) {
        try{
            boolean result = bookingRepository.updateToProcessing(id);
            if(result){
                return ResponseEntity.ok("Status updated to processing successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status is not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Status could not be updated");
        }
    }

    @PutMapping("/admin/bookingComplete/{id}")
    public ResponseEntity<String> updateStatusToComplete(@PathVariable(name = "id") long id) {
        try{
            boolean result = bookingRepository.updateToDone(id);
            if(result){
                return ResponseEntity.ok("Status updated to done successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status is not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Status could not be updated");
        }
    }
}
