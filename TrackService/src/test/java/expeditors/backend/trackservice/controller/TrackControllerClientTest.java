package expeditors.backend.trackservice.controller;

import expeditors.backend.domain.Track;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TrackControllerClientTest {

    @Autowired
    private MockMvc mockMvc;



//    @PostConstruct
//    public void init() {
//        baseUrl = "http://localhost:" + port;
//        rootUrl = "/api/track";
//        oneAdopterUrl = rootUrl + "/{id}";
//
//        this.restClient = RestClient.builder()
//                .baseUrl(baseUrl)
//                .defaultHeader("Accept", "application/json")
//                .defaultHeader("Content-Type", "application/json")
//                .build();
//    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testGetAll() throws Exception {
        var actions = mockMvc.perform(get("/api/track"))
                .andExpect(status().isOk()).andReturn();
    }
//    @Test
//    @WithMockUser(roles = {"USER"})
//    public void testGetOneWithExistingId() {
//        ResponseEntity<Track> response = restClient.get()
//                .uri(oneAdopterUrl, 1)
//                .retrieve()
//                .toEntity(Track.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        Track track = response.getBody();
//
//        System.out.println(track);
//    }

}
