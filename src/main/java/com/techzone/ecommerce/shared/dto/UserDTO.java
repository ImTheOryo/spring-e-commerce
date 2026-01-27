package com.techzone.ecommerce.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
}
