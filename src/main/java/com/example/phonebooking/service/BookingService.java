package com.example.phonebooking.service;

import com.example.phonebooking.model.Phone;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Phone> getAllAvailablePhones();

    boolean bookPhone(int phoneId, String clientName);

    boolean returnPhone(int phoneId);

    Optional<Phone> getPhoneById(int phoneId);
}
