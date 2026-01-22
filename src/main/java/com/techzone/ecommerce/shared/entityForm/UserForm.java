package com.techzone.ecommerce.shared.entityForm;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserForm {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Le mot de passe doit respecter une longueur minimale de 8 caractères, inclure des lettres majuscules, des lettres minuscules, des caractères spéciaux et des chiffres."
    )
    private String password;

    @NotNull
    private String confirmPassword;

}
