package com.example.phonebooking.controller;

import com.example.phonebooking.model.Client;
import com.example.phonebooking.model.Phone;
import com.example.phonebooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PhoneBookController {

    private final BookingService bookingService;

    public PhoneBookController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @RequestMapping("/")
    public ResponseEntity<String> Index(){
        return new ResponseEntity<>("Hello traveller in a demo of phone booking.", HttpStatus.OK);
    }

    @RequestMapping("/phones")
    public ResponseEntity<List<Phone>> getPhones(){
        return new ResponseEntity<>(bookingService.getAllAvailablePhones(), HttpStatus.OK);
    }

    @RequestMapping("/phones/{id}")
    public ResponseEntity<?> getPhoneById(@PathVariable Integer id){
        Optional<Phone> result = bookingService.getPhoneById(id);
        if(result.isPresent()){
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("No phone has id "+id,HttpStatus.OK);
    }

    @PostMapping("/phones/available/{id}")
    public ResponseEntity<?> bookPhone(@PathVariable Integer id, @Valid @RequestBody Client client){
        if(bookingService.bookPhone(id,client.getName())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Booked not possible PhoneId="+id,HttpStatus.OK);
    }

    @PostMapping("/phones/booked/{id}")
    public ResponseEntity<?> returnPhone(@PathVariable Integer id){
        if(bookingService.returnPhone(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("return not possible PhoneId= "+id,HttpStatus.OK);
    }
}
