package com.yassine.users.service.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
    private String adress;
    private String tel;
    private String prenom;
    private String siret;

    
    private String roles; // Rôle sélectionné par l'utilisateur
}
