package com.valarep.mariage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InviteStats {

    private long nbPeopleCeremonie;
    private long nbPeopleVinHonneur;
    private long nbPeopleRepas;

    private long nbPeopleBoeuf;
    private long nbPeoplePoisson;
    private long nbPeopleVege;

    public static InviteStats.InviteStatsBuilder inviteStatsBuilder(){
        return InviteStats.builder();
    }

}
