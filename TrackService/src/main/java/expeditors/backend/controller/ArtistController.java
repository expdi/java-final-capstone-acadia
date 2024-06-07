package expeditors.backend.controller;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import expeditors.backend.service.ArtistService;
import expeditors.backend.utils.UriCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {
    //Added Vincent
    @Autowired
    private ArtistRepo artistRepo;

    //Added Vincent
    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private UriCreator uriCreator;

    @PostMapping("/createArtist")
    //Vincent's code
    public ResponseEntity<?> createArtist(@RequestBody Artist entity) {
        try {

            Artist artist = artistService.addArtist(entity);
            URI newResource = uriCreator.getURI(artist.getId());
            return ResponseEntity.created(newResource).body(artist);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error:" + e.getCause().getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable("id") int id) {
        boolean result = artistService.deleteArtist(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artist with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<?> updateArtist(@RequestBody Artist artist) {
        boolean result = artistService.updateArtist(artist);
        if (artist.getName() == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Artist needs at least a title");
        }
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No artist with id: " + artist.getId());
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable("id") int id) {
        Artist artist = artistService.getArtist(id);
        if (artist == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No artist with id: " + id);
        }
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/getArtistByName/{name}")
    public ResponseEntity<?> getArtistByName(@PathVariable("name") String name) {
        List<Artist> artistList = artistService.getArtistByName(name);
        if (artistList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artists with name: " + name);
        }
        return ResponseEntity.ok(artistList);
    }

    @GetMapping("/getTrackByArtist/{name}")
    public ResponseEntity<?> getTrackByArtist(@PathVariable("name") String name) {
        List<Track> artistList = artistService.getTrackByArtist(name);
        if (artistList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tracks with artist name: " + name);
        }
        return ResponseEntity.ok(artistList);
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        List<Artist> artists = null;
        artists = artistService.getAllArtists();
        return artists;
    }

}
