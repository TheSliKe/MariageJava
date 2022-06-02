package com.valarep.mariage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
public class InviteController {

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private InviteMapper inviteMapper;


    @GetMapping("/invite")
    Iterable<InviteDB> getInvite(@RequestHeader(value="api-key") String apikey){
        return inviteRepository.findAll();
    }

    @GetMapping("/invite/{inviteId}")
    Optional<InviteDB> getInvite(@RequestHeader(value="api-key") String apikey, @PathVariable(value="inviteId") UUID inviteId){
        return inviteRepository.findById(inviteId);
    }


    @PostMapping("/invite")
    public ResponseEntity<String> postInvite(@RequestHeader(value="api-key") String apikey, @RequestBody Invite invite){

        if (!isPlatValid(invite)){
            return badPlatError();
        }

        InviteDB inviteDB = inviteMapper.map(invite);
        inviteDB.setId(UUID.randomUUID());
        inviteDB.setCreationDate(new Date());

        inviteRepository.save(inviteDB);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Saved invité : " + inviteDB.getId());
    }

    @PutMapping("/invite/{inviteId}")
    public ResponseEntity<String> putInvite(@RequestHeader(value="api-key") String apikey ,@PathVariable(value="inviteId") UUID inviteId, @RequestBody Invite invite){

        if (!isPlatValid(invite)){
            return badPlatError();
        }

        Optional<InviteDB> inviteToUpdate = inviteRepository.findById(inviteId);

        if (inviteToUpdate.isEmpty()){
            return badIdError(inviteId.toString());
        }

        InviteDB inviteDB = inviteMapper.map(inviteToUpdate.get(), invite);
        inviteDB.setUpdateDate(new Date());
        inviteRepository.save(inviteDB);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Updated invité : " + inviteId);
    }

    @DeleteMapping("/invite/{inviteId}")
    public ResponseEntity<String> deleteInvite(@RequestHeader(value="api-key") String apikey, @PathVariable(value="inviteId") UUID inviteId){

        Optional<InviteDB> inviteToDelete = inviteRepository.findById(inviteId);

        if (inviteToDelete.isEmpty()){
            return badIdError(inviteId.toString());
        }

        inviteRepository.delete(inviteToDelete.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Deleted invité : " + inviteId);

    }

    @GetMapping("/invite/stats")
    InviteStats getInviteStats(@RequestHeader(value="api-key") String apikey){
        return InviteStats.inviteStatsBuilder()
                .nbPeopleCeremonie(inviteRepository.countByCeremonie(true))
                .nbPeopleVinHonneur(inviteRepository.countByVinHonneur(true))
                .nbPeopleRepas(inviteRepository.countByRepas(true))
                .nbPeopleBoeuf(inviteRepository.countByPlat("boeuf"))
                .nbPeoplePoisson(inviteRepository.countByPlat("poisson"))
                .nbPeopleVege(inviteRepository.countByPlat("végé"))
                .build();
    }

    private boolean isPlatValid(Invite invite) {
        return invite.getPlat().equals("boeuf") || invite.getPlat().equals("poisson") || invite.getPlat().equals("végé");
    }

    private ResponseEntity<String> badPlatError() {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Plat must be one of the following value : boeuf, poisson, végé");
    }

    private ResponseEntity<String> badIdError(String id) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No invité with id : " + id);
    }

}
