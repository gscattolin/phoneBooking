package com.example.phonebooking.repository;

import com.example.phonebooking.model.Phone;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class PhoneRepositoryImpl implements PhoneRepository {
    @Override
    public List<Phone> getAllAvailablePhones() {
        ArrayList<Phone> phones =  new ArrayList<>();
        phones.add(
                Phone.builder().
                        Id(1).
                        modelName("Samsung Galaxy S9").build());
        phones.add(
                Phone.builder().
                        Id(2).
                        modelName("2x Samsung Galaxy S8").build());
        phones.add(
                Phone.builder().
                        Id(3).
                        modelName("Motorola Nexus 6").build());
        phones.add(
                Phone.builder().
                        Id(4).
                        modelName("Oneplus 9").build());
        phones.add(
                Phone.builder().
                        Id(5).
                        modelName("Apple iPhone 13").build());
        phones.add(
                Phone.builder().
                        Id(6).
                        modelName("Apple iPhone 12").build());
        phones.add(
                Phone.builder().
                        Id(7).
                        modelName("Apple iPhone 11").build());
        phones.add(
                Phone.builder().
                        Id(8).
                        modelName("iPhone X").build());
        phones.add(
                Phone.builder().
                        Id(9).
                        modelName("Nokia 3310").build());
        return phones;
    }
}
