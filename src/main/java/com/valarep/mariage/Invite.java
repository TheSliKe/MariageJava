package com.valarep.mariage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Invite {

    private String nom;
    private String prenom;
    private boolean ceremonie;
    private boolean vinHonneur;
    private boolean repas;
    private String plat;

    public static Invite.InviteBuilder inviteBuilder(){
        return Invite.builder();
    }

}
