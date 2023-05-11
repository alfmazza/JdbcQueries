package com.sa.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Properties {
    
   
    private Long properId;
    private double copay;
    private int medicalGuide;
    private int internation;
    private int doctorToHome;
    private String odontology; 
    private String orthodontics;
    private double refund;
}
