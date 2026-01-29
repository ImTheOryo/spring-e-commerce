package com.techzone.ecommerce.shared.dto;

import com.techzone.ecommerce.shared.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.address = user.getAddress();
        this.phone = user.getPhone();
    }
}
