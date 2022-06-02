package com.valarep.mariage;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static com.valarep.mariage.InviteDB.inviteDBBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InviteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InviteRepository inviteRepository;

    @BeforeEach
    public void initEach(){

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(2022, 6, 2);

        inviteRepository.deleteAll();

        inviteRepository.save(inviteDBBuilder()
                .id(UUID.fromString("f542ac18-5205-9b0c-9629-35a9959a058d"))
                .creationDate(Date.from(localDate.atStartOfDay(defaultZoneId).toInstant()))
                .updateDate(null)
                .nom("nom")
                .prenom("prenom")
                .ceremonie(true)
                .vinHonneur(false)
                .repas(false)
                .plat("boeuf")
                .build());

    }

    @Test
    void shouldPostInviteAndSendHTTP200() throws Exception {

        mockMvc.perform(post("/invite").header("api-key", "1234").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                          "nom": "dupale",
                          "prenom": "julien",
                          "ceremonie": true,
                          "vinHonneur": true,
                          "repas": true,
                          "plat": "poisson"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldGet404_whenPutAndIdNotExist() throws Exception {
        mockMvc.perform(put("/invite/3fa85f64-5717-4562-b3fc-2c963f66afa6").header("api-key", "1234").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                          "nom": "dupale",
                          "prenom": "julien",
                          "ceremonie": true,
                          "vinHonneur": true,
                          "repas": true,
                          "plat": "poisson"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldGetAllInviteAndSendHTTP200() throws Exception {
        mockMvc.perform((get("/invite").header("api-key", "1234")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    [
                      {
                        "id": "f542ac18-5205-9b0c-9629-35a9959a058d",
                        "creationDate": "2022-06-01T22:00:00.000+00:00",
                        "updateDate": null,
                        "nom": "nom",
                        "prenom": "prenom",
                        "ceremonie": true,
                        "vinHonneur": false,
                        "repas": false,
                        "plat": "boeuf"
                      }
                    ]
                    """));

    }

    @Test
    void shouldGetOneInviteAndSendHTTP200() throws Exception {
        mockMvc.perform((get("/invite/f542ac18-5205-9b0c-9629-35a9959a058d").header("api-key", "1234")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    
                    {
                        "id": "f542ac18-5205-9b0c-9629-35a9959a058d",
                        "creationDate": "2022-06-01T22:00:00.000+00:00",
                        "updateDate": null,
                        "nom": "nom",
                        "prenom": "prenom",
                        "ceremonie": true,
                        "vinHonneur": false,
                        "repas": false,
                        "plat": "boeuf"
                    }
                    
                    """));

    }



}