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

@Component
class MyRunner implements CommandLineRunner {
    @Autowired
    private TrackService trackService;
    @Autowired
    private ArtistService artistService;
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Here we go with Spring Boot");
        var tracks = List.of(
                new Track("Standing Next to You", "COMING HOME",
                        List.of( new Artist.ArtistBuilder().id(1).name("JungKook").build(), new Artist.ArtistBuilder().id(2).name("Usher").build()),
                        LocalDate.of(2024,2,9), Duration.ofMinutes(3).plusSeconds(35), MediaType.OGG),
                new Track("Cruel Summer", "Lover",
                        List.of( new Artist.ArtistBuilder().id(3).name("Taylor Swift").build()),
                        LocalDate.of(2023,8,23), Duration.ofMinutes(2).plusSeconds(59), MediaType.MP3),
                new Track("Cruel Summer - Remix(?)", null,
                        null,
                        LocalDate.of(2024,4,24), Duration.ofMinutes(1).plusSeconds(19), MediaType.FLAC)
        );
        tracks.forEach(trackService::addTrack);

        var artists = List.of(new Artist.ArtistBuilder().id(1).name("JungKook").build(), new Artist.ArtistBuilder().id(2).name("Usher").build(),new Artist.ArtistBuilder().id(3).name("Taylor Swift").build());
        artists.forEach(artistService::addArtist);

        System.out.println(tracks);
        System.out.println(artists);
    }

}