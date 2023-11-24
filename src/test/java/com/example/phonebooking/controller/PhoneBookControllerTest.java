package com.example.phonebooking.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.phonebooking.model.Client;
import com.example.phonebooking.model.Phone;
import com.example.phonebooking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PhoneBookController.class)
class PhoneBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    private Phone phone1;
    private Phone phone2;
    private Phone phone3;

    private String now;

    private void setup(){
        now = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate( FormatStyle.SHORT ));
        phone1= Phone.builder().Id(1).modelName("Samsung Galaxy S9").available(true).build();
        phone2 = Phone.builder().Id(2).modelName("2x Samsung Galaxy S8").available(true).build();
        phone3 = Phone.builder().Id(3).modelName("Motorola Nexus 6").available(false).bookedBy("Rick Sanchez").bookingDate(now).build();
    }

     private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldGetAllThePhones() throws Exception {
        setup();
        Mockito.when(bookingService.getAllAvailablePhones()).thenReturn(Arrays.asList(phone1,phone2,phone3));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/phones").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected =  "[{\"modelName\":\"Samsung Galaxy S9\",\"available\":true,\"bookingDate\":null,\"bookedBy\":null,\"id\":1}," +
                "{\"modelName\":\"2x Samsung Galaxy S8\",\"available\":true,\"bookingDate\":null,\"bookedBy\":null,\"id\":2}," +
                "{\"modelName\":\"Motorola Nexus 6\",\"available\":false,\"bookingDate\":\""+now+"\",\"bookedBy\":\"Rick Sanchez\",\"id\":3}]";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    void shouldGetPhoneByIdReturnPhoneDetail() throws Exception {
        setup();
        Mockito.when(bookingService.getPhoneById(3)).thenReturn(Optional.of(phone3));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/phones/3").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"modelName\":\"Motorola Nexus 6\",\"available\":false,\"bookingDate\":\""+now+"\",\"bookedBy\":\"Rick Sanchez\",\"id\":3}]";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);

    }

    @Test
    void shouldReturnTrueIfBookAvailablePhone() throws Exception {
        setup();
        Mockito.when(bookingService.bookPhone(1,"Rick Sanchez")).thenReturn(true);
        Mockito.when(bookingService.getPhoneById(1)).thenReturn(Optional.of(phone1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/phones/available/1").content(asJsonString(new Client("Rick Sanchez")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldReturnErrorIfBookWithEmptyClientName() throws Exception {
        setup();
        Mockito.when(bookingService.bookPhone(1,"Rick Sanchez")).thenReturn(true);
        Mockito.when(bookingService.getPhoneById(1)).thenReturn(Optional.of(phone1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/phones/available/1").content(asJsonString(new Client("")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getResponse().getErrorMessage()).isEqualTo("Invalid request content.");
    }
}
