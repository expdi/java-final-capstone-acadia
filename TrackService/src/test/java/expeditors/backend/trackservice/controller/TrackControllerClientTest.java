package expeditors.backend.trackservice.controller;

import expeditors.backend.domain.Track;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrackControllerClientTest {
    @LocalServerPort
    private int port;
    private RestClient restClient;

    private String baseUrl;
    private String rootUrl;
    private String oneAdopterUrl;

    @PostConstruct
    public void init() {
        baseUrl = "http://localhost:" + port;
        rootUrl = "/track";
        oneAdopterUrl = rootUrl + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void testGetAll(){
        ParameterizedTypeReference<List<Track>> ptr =
                new ParameterizedTypeReference<List<Track>>() {};
        ResponseEntity<List<Track>> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(ptr);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testGetOneWithExistingId() {
        ResponseEntity<Track> response = restClient.get()
                .uri(oneAdopterUrl, 1)
                .retrieve()
                .toEntity(Track.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Track track = response.getBody();

        System.out.println(track);
    }

}
