package com.valarep.mariage;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.valarep.mariage.InviteDB.inviteDBBuilder;

@Component
public class InviteMapper {

    InviteDB map(Invite invite){

        return inviteDBBuilder()
                .nom(invite.getNom())
                .prenom(invite.getPrenom())
                .ceremonie(invite.isCeremonie())
                .vinHonneur(invite.isVinHonneur())
                .repas(invite.isRepas())
                .plat(invite.getPlat())
                .build();

    }

    InviteDB map(InviteDB inviteToUpdate, Invite invite) {
        inviteToUpdate.setNom(invite.getNom());
        inviteToUpdate.setPrenom(invite.getPrenom());
        inviteToUpdate.setCeremonie(invite.isCeremonie());
        inviteToUpdate.setVinHonneur(invite.isVinHonneur());
        inviteToUpdate.setRepas(invite.isRepas());
        inviteToUpdate.setPlat(invite.getPlat());
        return inviteToUpdate;
    }
}
