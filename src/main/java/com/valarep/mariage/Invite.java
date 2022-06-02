package com.valarep.mariage;

import lombok.Data;

@Data
public class Invite {


    private String nom;
    private String prenom;
    private boolean ceremonie;
    private boolean vinHonneur;
    private boolean repas;
    private String plat;

}
