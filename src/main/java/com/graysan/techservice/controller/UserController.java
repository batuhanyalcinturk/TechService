package com.graysan.techservice.controller;

import com.graysan.techservice.model.MyUser;
import com.graysan.techservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody MyUser myUser) {
        try{
            boolean result = userRepository.save(myUser);

            if (result){
                return ResponseEntity.ok("User saved");
            }else{
                return ResponseEntity.internalServerError().body("User not saved");
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("User not saved " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MyUser>> getAll(){
        return ResponseEntity.ok(userRepository.getAll());
    }

}
