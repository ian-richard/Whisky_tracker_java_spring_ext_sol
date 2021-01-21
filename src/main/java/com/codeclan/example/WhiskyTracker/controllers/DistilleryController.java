package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

@RestController
public class DistilleryController {

    @Autowired
    DistilleryRepository distilleryRepository;


    /**
     * Handles routes and filters:
     *  GET  /distilleries
     *  GET  /distilleries?region=Speyside
     *  GET  /distilleries?whiskyAge=12
     * @return `ResponseEntity<List<Distillery>>`
     */
    @GetMapping(value = "/distilleries")
    public ResponseEntity getAllDistilleriesAndFilters(
            @RequestParam(required = false, name = "region") String region,
            @RequestParam(required = false, name = "whiskyAge") Integer whiskyAge

    ){
        // GET  /distilleries?region=Speyside
        if (region != null){
            return new ResponseEntity(distilleryRepository.findByRegion(region), HttpStatus.OK);
        }
        // GET /distilleries?whiskyAge=12
        if (whiskyAge != null){
            return new ResponseEntity(distilleryRepository.findByWhiskiesAge(whiskyAge), HttpStatus.OK);
        }
        // GET /distilleries
        return new ResponseEntity(distilleryRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping(value = "/distilleries/{id}")
    public ResponseEntity getDistillery(@PathVariable Long id) {
        return new ResponseEntity(distilleryRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/distilleries")
    public ResponseEntity<Distillery> postDistillery(@RequestBody Distillery distillery) {
        distilleryRepository.save(distillery);
        return new ResponseEntity<>(distillery, HttpStatus.CREATED);
    }

    @PutMapping(value = "/distilleries/{id}")
    public ResponseEntity<Distillery> putDistillery(@RequestBody Distillery distillery, @PathVariable Long id) {
        distilleryRepository.save(distillery);
        return  new ResponseEntity<>(distillery, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/distilleries/{id}")
    public ResponseEntity<List<Distillery>> deleteDistillery(@PathVariable Long id) {
        distilleryRepository.deleteById(id);
        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }

}
