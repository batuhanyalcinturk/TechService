package com.graysan.techservice.controller;

import com.graysan.techservice.dto.ProposalAdminDto;
import com.graysan.techservice.dto.ProposalDto;
import com.graysan.techservice.model.Proposal;
import com.graysan.techservice.repository.ProposalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private final ProposalRepository proposalRepository;

    public ProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<List<Proposal>> getAll() {
        return ResponseEntity.ok(proposalRepository.getAllForUser());
    }

    @DeleteMapping("/user/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") long id) {
        try{
            boolean result = proposalRepository.deleteById(id);
            if(result){
                return ResponseEntity.ok("id: " + id + " Proposal deleted successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id: " + id + " Proposal is not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("id: " + id + " Proposal could not be deleted");
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<String> save(@RequestBody Proposal proposal) {
        try{
            boolean result = proposalRepository.save(proposal);
            if(result){
                return ResponseEntity.ok("Proposal saved successfully");
            }else {
                return ResponseEntity.internalServerError().body("Opss! Proposal could not be saved");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Opss! Proposal could not be saved");
        }
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<List<ProposalAdminDto>> getAllDto(){
        return ResponseEntity.ok(proposalRepository.getAllDto());
    }

    @PutMapping("/admin/updateTrueStatus/{id}")
    public ResponseEntity<String> updateTrueStatus(@PathVariable(name = "id") long id){
        try{
            boolean result = proposalRepository.updateTrueStatus(id);
            if(result){
                return ResponseEntity.ok("id: " + id + " Proposal updated successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id: " + id + " Proposal is not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("id: " + id + " Proposal could not be updated");
        }
    }

    @PutMapping("/admin/updateFalseStatus/{id}")
    public ResponseEntity<String> updateFalseStatus(@PathVariable(name = "id") long id){
        try{
            boolean result = proposalRepository.updateFalseStatus(id);
            if(result){
                return ResponseEntity.ok("id: " + id + " Proposal updated successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id: " + id + " Proposal is not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("id: " + id + " Proposal could not be updated");
        }
    }

    @GetMapping("/admin/getById/{id}")
    public ResponseEntity<ProposalAdminDto> getById(@PathVariable(name = "id") long id){
        try{
            ProposalAdminDto proposalAdminDto = proposalRepository.getAllDtoById(id);
            if(proposalAdminDto != null){
                return ResponseEntity.ok(proposalAdminDto);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
