package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    /*

     */

    /**
     * Handles routes and filters:
     *     GET /whiskies
     *     GET /whiskies?year=1995
     *     GET /whiskies?distilleryName=Glendronach&age=15
     *     GET /whiskies?distilleryRegion=Speyside
     * @return `ResponseEntity<List<Whisky>>`
     */
    @GetMapping(value = "/whiskies")
    public ResponseEntity getAllWhiskiesAndFilters(
            @RequestParam(required = false, name = "year") Integer year,
            @RequestParam(required = false, name = "distilleryName") String distName,
            @RequestParam(required = false, name = "age") Integer age,
            @RequestParam(required = false, name = "distilleryRegion") String distRegion
    ){

        // if we have the year string then do the year query
        if (year != null){
            return new ResponseEntity(whiskyRepository.findByYear(year), HttpStatus.OK);
        }
        // if we have the distilleryName string AND age  do the distilleryName AND age query
        if (distName != null && age != null){
            List<Whisky> foundWhisky = whiskyRepository.findByDistilleryNameAndAge(distName, age);
            return new ResponseEntity(foundWhisky, HttpStatus.OK);
        }
        // if we have the distilleryRegion string do the region query
        if (distRegion != null){
            return new ResponseEntity(whiskyRepository.findByDistilleryRegion(distRegion), HttpStatus.OK);
        }

        // default: we have none of the query strings GET /whiskies
        return new ResponseEntity(whiskyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/whiskies/{id}")
    public ResponseEntity getWhisky(@PathVariable Long id) {
        return new ResponseEntity(whiskyRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/whiskies")
    public ResponseEntity<Whisky> postDistillery(@RequestBody Whisky whisky) {
        whiskyRepository.save(whisky);
        return new ResponseEntity<>(whisky, HttpStatus.CREATED);
    }

    @PutMapping(value = "/whiskies/{id}")
    public ResponseEntity<Whisky> putDistillery(@RequestBody Whisky whisky,@PathVariable Long id) {
        whiskyRepository.save(whisky);
        return new ResponseEntity<>(whisky, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/whiskies/{id}")
    public ResponseEntity<List<Whisky>> deleteDistillery(@PathVariable Long id) {
        whiskyRepository.deleteById(id);
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }

}
