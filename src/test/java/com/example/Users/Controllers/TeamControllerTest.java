package com.example.Users.Controllers;

import com.example.Users.Services.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TeamController.class)
public class TeamControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeamService teamService;

    @Test
    public void addTeam() throws Exception {
        mvc.perform(post("/teams/add_team"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'data':'test'}"));
    }

}