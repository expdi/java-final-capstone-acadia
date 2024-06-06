package expeditors.backend.trackservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    void createArtistTest() throws Exception {
        Artist artist = new Artist();
        artist.setName("New Artist");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(artist);

        ResultActions actions = mockMvc
                .perform(post("/api/artist/createArtist", artist)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString));
        actions = actions.andExpect(status().isCreated());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println(reo);
    }

    @Test
    void createArtistForTrackTest() throws Exception {
        Artist artist = new Artist();
        artist.setName("New Artist");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(artist);

        ResultActions actions = mockMvc
                .perform(post("/api/artist/createArtistForTrack/1", artist)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString));
        actions = actions.andExpect(status().isCreated());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println(reo);
    }

    @Test
    void deleteArtistTest() throws Exception {
        int id = 1;
        ResultActions actions = mockMvc
                .perform(delete("/api/artist/{id}", id)
                        .accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNoContent());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
    }

    @Test
    void updateArtistTest() throws Exception {
        Artist artistUpdate = new Artist();
        artistUpdate.setId(1);
        artistUpdate.setName("Artist Update");

        String jsonString = mapper.writeValueAsString(artistUpdate);

        ResultActions actions = mockMvc.perform(put("/api/artist/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions = actions.andExpect(status().isNoContent());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
    }

    @Test
    void getArtistByIdTest() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/artist/1").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Artist is " + reo);
    }

    @Test
    void getArtistByNameTest() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/artist/getArtistByName/Taylor%20Swift").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Artist is " + reo);
    }

    @Test
    void getTrackByArtistTest() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/artist/getTrackByArtist/taylor").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Artist is " + reo);
    }

    @Test
    void getAllArtistTest() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/artist").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = mvcr.getResponse().getContentAsString();
        System.out.println("Artist " + reo);
    }
}
