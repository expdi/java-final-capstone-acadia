package expeditors.backend.trackservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import expeditors.backend.domain.Track;
import expeditors.backend.domain.TypeDuration;
import expeditors.backend.service.TrackService;
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
class TrackControllerTest {
    @Autowired
    private TrackService trackService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    void createTrack() throws Exception {
        Track track = new Track();
        track.setTitle("Test Title");
        track.setAlbum("Test Album");
        track.setIssueDate(LocalDate.of(2024, 4, 23));
        track.setDuration(Duration.ofMinutes(5));
        track.setMediaType(0);
        track.setMediaTypeEnum(expeditors.backend.domain.MediaType.MP3);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(track);

        ResultActions actions = mockMvc
                .perform(post("/api/track/createTrackWithArtists", track)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString));
        actions = actions.andExpect(status().isCreated());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println(reo);
    }

    @Test
    void createTrackWithMissingData() throws Exception {
        Track track = new Track();
        //track.setTitle("Test Title");
        track.setAlbum("Test Album");
        track.setIssueDate(LocalDate.of(2024, 4, 23));
        track.setDuration(Duration.ofMinutes(5));
        track.setMediaType(0);
        track.setMediaTypeEnum(expeditors.backend.domain.MediaType.MP3);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(track);

        ResultActions actions = mockMvc
                .perform(post("/api/track/createTrackWithArtists", track)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString));
        actions = actions.andExpect(status().isBadRequest());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println(reo);
    }

    @Test
    void getAllTracks() throws Exception {

        ResultActions actions = mockMvc
                .perform(get("/api/track").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }

    @Test
    void getAllTracksEmpty() throws Exception {
        trackService.deleteAllTracks();
        ResultActions actions = mockMvc
                .perform(get("/api/track").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNotFound());

        MvcResult mvcr = actions.andReturn();
        String reo = mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }

    @Test
    void getTrack() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTrack/1").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Track is " + reo);
    }

    @Test
    void getTrackNotExist() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTrack/1000").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNotFound());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Track is " + reo);
    }

    @Test
    void getTracksByAlbum() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByAlbum/Red").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Track is " + reo);
    }

    @Test
    void getTracksByAlbumNotExist() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByAlbum/White").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNotFound());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Track is " + reo);
    }

    @Test
    void getArtists() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getArtistsByTrack/1").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Artists " + reo);
    }

    @Test
    void getArtistsNotExistTrack() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getArtistsByTrack/999").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNotFound());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Artists " + reo);
    }

    @Test
    void getTracksByMediaType() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByMediaType/MP3").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }

    @Test
    void getTracksByMediaTypeNotExist() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByMediaType/OGG").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNotFound());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }

    @Test
    void getTrackByYear() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByYear/2024").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }

    @Test
    void getTrackByYearNotExist() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByYear/2026").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNotFound());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }

    @Test
    void getTrackByDuration() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByDuration?typeDuration={typeDuration}&duration={duration}", TypeDuration.Shorted, "PT4M").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }

    @Test
    void getTrackByDurationNotFound() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/api/track/getTracksByDuration?typeDuration={typeDuration}&duration={duration}", TypeDuration.Longer, "PT50M").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNoContent());

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Tracks " + reo);
    }


    @Test
    void deleteTrack() throws Exception {
        int id = 1;
        ResultActions actions = mockMvc
                .perform(delete("/api/track/{id}", id).accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNoContent());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
    }

    @Test
    void deleteTrackNotExist() throws Exception {
        int id = 9999;
        ResultActions actions = mockMvc
                .perform(delete("/api/track/{id}", id).accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isNotFound());

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
    }

    @Test
    void updateStudent() throws Exception {
        Track newTrack = new Track();
        newTrack.setId(1);
        newTrack.setTitle("New Title Updated");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newTrack);
//        ResultActions actions = mockMvc
//                .perform(put("/api/track", newTrack)
//                        .accept(MediaType.APPLICATION_JSON));
        ResultActions putActions = mockMvc.perform(put("/api/track")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        putActions = putActions.andExpect(status().isNoContent());

        MvcResult mvcr = putActions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
    }

    @Test
    void updateStudentWithOutTitle() throws Exception {
        Track newTrack = new Track();
        newTrack.setId(1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newTrack);

        ResultActions putActions = mockMvc.perform(put("/api/track")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        putActions = putActions.andExpect(status().isBadRequest());

        MvcResult mvcr = putActions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
    }

    @Test
    void updateStudentNotExistId() throws Exception {
        Track newTrack = new Track();
        newTrack.setId(999);
        newTrack.setTitle("New Title Updated");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newTrack);

        ResultActions putActions = mockMvc.perform(put("/api/track")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        putActions = putActions.andExpect(status().isNotFound());

        MvcResult mvcr = putActions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
    }
}