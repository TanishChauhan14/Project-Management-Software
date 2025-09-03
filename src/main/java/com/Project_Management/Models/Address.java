package com.Project_Management.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {

    private String street;
    private String city;
    private String state;
    private String country; 
    private int pincode;
  }

