package com.valarep.mariage;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = "Invite")
@Builder
public class InviteDB {

    @Id
    private UUID id;
    private Date creationDate;
    private Date updateDate;

    private String nom;
    private String prenom;
    private boolean ceremonie;
    private boolean vinHonneur;
    private boolean repas;
    private String plat;

    public static InviteDB.InviteDBBuilder inviteDBBuilder(){
        return InviteDB.builder();
    }

}
