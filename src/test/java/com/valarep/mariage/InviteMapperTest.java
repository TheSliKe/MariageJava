package com.valarep.mariage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static com.valarep.mariage.Invite.inviteBuilder;
import static com.valarep.mariage.InviteDB.inviteDBBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InviteMapperTest {

    private final InviteMapper inviteMapper = new InviteMapper();

    @Test
    void shouldMapFromInviteToInviteDB_whenInviteIsGiven(){

        //Arrange
        Invite invite =  inviteBuilder()
                .nom("nom")
                .prenom("prenom")
                .ceremonie(true)
                .vinHonneur(false)
                .repas(true)
                .plat("boeuf")
                .build();

        //Act
        InviteDB inviteDB = inviteMapper.map(invite);

        //Assert
        assertThat(inviteDB).isEqualTo(inviteDBBuilder()
                .id(null)
                .creationDate(null)
                .updateDate(null)
                .nom("nom")
                .prenom("prenom")
                .ceremonie(true)
                .vinHonneur(false)
                .repas(true)
                .plat("boeuf")
                .build());

    }

    @Test
    void shouldUpdateInviteDB_whenInviteIsGiven(){

        //Arrange

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(2022, 6, 2);

        InviteDB inviteDB_old = inviteDBBuilder()
                .id(UUID.fromString("f542ac18-5205-9b0c-9629-35a9959a058d"))
                .creationDate(Date.from(localDate.atStartOfDay(defaultZoneId).toInstant()))
                .updateDate(null)
                .nom("nom_old")
                .prenom("prenom_old")
                .ceremonie(true)
                .vinHonneur(true)
                .repas(true)
                .plat("boeuf_old")
                .build();

        Invite invite =  inviteBuilder()
                .nom("nom_new")
                .prenom("prenom_new")
                .ceremonie(false)
                .vinHonneur(false)
                .repas(false)
                .plat("boeuf_new")
                .build();

        //Act
        InviteDB inviteDB = inviteMapper.map(inviteDB_old, invite);

        //Assert
        assertThat(inviteDB).isEqualTo(inviteDBBuilder()
                .id(UUID.fromString("f542ac18-5205-9b0c-9629-35a9959a058d"))
                .creationDate(Date.from(localDate.atStartOfDay(defaultZoneId).toInstant()))
                .updateDate(null)
                .nom("nom_new")
                .prenom("prenom_new")
                .ceremonie(false)
                .vinHonneur(false)
                .repas(false)
                .plat("boeuf_new")
                .build());

    }

}