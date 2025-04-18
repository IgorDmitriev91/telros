package com.example.telrostest.integration;

import com.example.telrostest.config.IntegrationTestConfig;
import com.example.telrostest.dto.UserDTO;
import com.example.telrostest.stubs.UserStubs;
import com.example.telrostest.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 class UserIntegrationTest extends IntegrationTestConfig {

    private final String urlTemplate = "/api/v1/user";
    @Autowired
    private MockMvc mvc;

    @Test
    void getUserPositiveTest() throws Exception {
        UserDTO userDTO = UserStubs.getTrueUserDTOStubs();

        mvc.perform(MockMvcRequestBuilders.get(urlTemplate +"/"+ UserStubs.userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(UserStubs.userId.toString()))
                .andExpect(jsonPath("$.first_name").value(userDTO.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(userDTO.getLastName()));
    }

    @Test
    void getUserNegativeTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(urlTemplate +"/"+ UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(Utils.USER_NOT_FOUND_WITH_ID));
    }

     @Test
     void shouldReturnStatusCreated() throws Exception {
         mvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(UserStubs.jsonUser()))
                 .andExpect(status().isCreated());
     }

     @Test
     void shouldReturnStatusBadRequestWhenEmptyBody() throws Exception {
         mvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(UserStubs.notValidJsonUser()))
                 .andExpect(status().isBadRequest());
     }


}

