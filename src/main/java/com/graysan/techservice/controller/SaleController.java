package com.graysan.techservice.controller;

import com.graysan.techservice.model.Sale;
import com.graysan.techservice.repository.SaleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleRepository saleRepository;

    public SaleController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /*
    Get all sales
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Sale>> getAll(){
        try {
            List<Sale> temp = saleRepository.getAll();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
    Get all available sales
     */
    @GetMapping("/getAvailableSales")
    public ResponseEntity<List<Sale>> getAvailableSales(){
        try {
            List<Sale> temp = saleRepository.getAvailableSales();
            return ResponseEntity.ok(temp);
            }catch (Exception e){
                return ResponseEntity.internalServerError().build();
            }
    }

    /*
    Get all sales by product id
     */
    @GetMapping("/getSaleByProductId/{productId}")
    public ResponseEntity<List<Sale>> getSaleByProductId(@PathVariable(name = "productId") Long productId){
        try{
            List<Sale> temp = saleRepository.getSaleByProductId(productId);
            if (temp != null){
                return ResponseEntity.ok(temp);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
    Delete a sale by id from the sale table. Only admin users can delete.
     */
    @DeleteMapping("/admin/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") long id){
        try{
            boolean result = saleRepository.deleteById(id);
            if(result){
                return ResponseEntity.ok("id: " + id + " Sale deleted successfully.");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id: " + id + " Sale is not found.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("id: " + id + " Sale could not be deleted.");
        }
    }

    /*
    Save a new sale to the sale table. Only admin users can save.
     */

    @PostMapping("/admin/save")
    public ResponseEntity<String> save(@RequestBody Sale sale){
        try{
            boolean result = saleRepository.save(sale);
            if(result){
                return ResponseEntity.ok("Sale saved successfully.");
            }else {
                return ResponseEntity.internalServerError().body("Opss! Sale could not be saved.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Opss! Sale could not be saved.");
        }
    }
}
