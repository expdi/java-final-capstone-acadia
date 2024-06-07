package expeditors.backend;

import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.service.ArtistService;
import expeditors.backend.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class TrackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackServiceApplication.class, args);
    }

}
