package com.example.demo.infrestructure.controller.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoRequest {
    public int id;


    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100,
            message = "Name must contain between 2 and 100 characters")
    public String name;

    @Email
    public String email;
    public String password;
    public String phone;

}
