package com.example.phonebooking.service;

import com.example.phonebooking.model.Phone;
import com.example.phonebooking.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Booking implements BookingService{

    private ConcurrentHashMap<Integer, Phone> phones;

    @Autowired
    public Booking( PhoneRepository phoneRepository) {
        phones =  new ConcurrentHashMap<>();
        phoneRepository.getAllAvailablePhones().forEach(phone ->phones.putIfAbsent(phone.getId(),phone));
    }

    @Override
    public List<Phone> getAllAvailablePhones() {
        return phones.values().stream().toList();
    }

    @Override
    public boolean bookPhone(int phoneId, String clientName) {
        Optional<Phone> phone = getPhoneById(phoneId);
        if (phone.isPresent() && phone.get().isAvailable()){
            phones.computeIfPresent(phoneId, (k, phoneBooked) -> {
                phoneBooked.setBookedBy(clientName);
                phoneBooked.setAvailable(false);
                phoneBooked.setBookingDate(getNow());
                return phoneBooked;
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean returnPhone(int phoneId) {
        Optional<Phone> phone = getPhoneById(phoneId);
        if (phone.isPresent() && !phone.get().isAvailable()){
            phones.computeIfPresent(phoneId, (k, phoneBooked) -> {
                phoneBooked.setAvailable(true);
                phoneBooked.setBookingDate(getNow());
                return phoneBooked;
            });
            return true;
        }
        return false;
    }

    @Override
    public Optional<Phone> getPhoneById(int phoneId) {
        Phone phone =phones.getOrDefault(phoneId,null);
        return phone!=null ? Optional.of(phone) :  Optional.empty() ;
    }

    private String getNow() {
        return LocalDate.now().format(DateTimeFormatter.ofLocalizedDate( FormatStyle.SHORT ) );
    }
}
