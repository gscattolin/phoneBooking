package com.example.phonebooking.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Phone {
    private Integer Id;
    private final String modelName;
    @Builder.Default
    private boolean available = true;
    private String bookingDate;
    private String bookedBy;
}