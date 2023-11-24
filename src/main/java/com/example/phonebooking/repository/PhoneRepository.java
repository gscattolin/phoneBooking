package com.example.phonebooking.repository;

import com.example.phonebooking.model.Phone;

import java.util.List;

public interface PhoneRepository {

    List<Phone> getAllAvailablePhones();

}
