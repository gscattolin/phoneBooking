package com.example.phonebooking.service;

import com.example.phonebooking.model.Phone;
import com.example.phonebooking.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BookingTest {

    private Booking booking;
    private Phone phone1;
    private Phone phone2;
    private Phone phone3;

    private String now;

    private PhoneRepository repo;

    @BeforeEach
    public void setup(){
        repo = mock(PhoneRepository.class);
        now = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate( FormatStyle.SHORT ));
        phone1= Phone.builder().Id(1).modelName("Samsung Galaxy S9").available(true).build();
        phone2 = Phone.builder().Id(2).modelName("2x Samsung Galaxy S8").available(true).build();
        phone3 = Phone.builder().Id(3).modelName("Motorola Nexus 6").available(false).bookedBy("Rick Sanchez").bookingDate(now).build();
        Mockito.when(repo.getAllAvailablePhones()).thenReturn(
                Arrays.asList(phone1,phone2,phone3)
        );
        booking = new Booking(repo);
    }

    @DisplayName("getAllAvailablePhones method should return correct number of phones")
    @Test
    public void getAllAvailablePhonesShouldReturnCorrectNumberOfPhones(){
        assertThat(booking.getAllAvailablePhones().size()).isEqualTo(3);
    }

    @Test
    public void getAnAvailablePhoneShouldReturnPhoneInformation(){
        Optional<Phone> phoneOpt =booking.getPhoneById(1);
        assertThat(phoneOpt.isPresent()).isTrue();
        Phone phoneBooked = phoneOpt.get();
        assertThat(phoneBooked.isAvailable()).isTrue();
        assertThat(phoneBooked.getModelName()).isEqualTo(phone1.getModelName());
    }
    @Test
    public void getABookedPhoneShouldReturnPhoneInformation(){
        Optional<Phone> phoneOpt =booking.getPhoneById(3);
        assertThat(phoneOpt.isPresent()).isTrue();
        Phone phoneBooked = phoneOpt.get();
        assertThat(phoneBooked).isEqualTo(phone3);
        assertThat(phoneBooked.isAvailable()).isFalse();
    }


    @Test
    public void getAnNotExistingPhoneShouldReturnOptionalEmpty(){
        assertThat(booking.getPhoneById(4)).isEqualTo(Optional.empty());
    }

    @Test
    public void bookAnAvailablePhoneShouldReturnTrue(){
        assertThat(booking.bookPhone(1,"Rick Sanchez")).isTrue();
    }

    @Test
    public void bookAnAvailablePhoneShouldReturnThePhoneBookedByTheClient(){
        Optional<Phone> bookedPhone = booking.getPhoneById(3);
        assertThat(bookedPhone.isPresent()).isTrue();
        assertThat(bookedPhone.get().getBookedBy()).isEqualTo("Rick Sanchez");
        assertThat(bookedPhone.get().getBookingDate()).isEqualTo(now);
    }

    @Test
    public void bookAnAlreadyBookedPhoneShouldReturnFalse(){
        assertThat(booking.bookPhone(3,"Rick Sanchez")).isFalse();
    }

    @Test
    public void returnAnBookedPhoneShouldReturnTrue(){
        assertThat(booking.returnPhone(3)).isTrue();
    }

    @Test
    public void returnAnNotBookedPhoneShouldReturnFalse(){
        assertThat(booking.returnPhone(2)).isFalse();
    }


}
