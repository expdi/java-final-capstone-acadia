package expeditors.backend.controller;

import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.domain.TypeDuration;
import expeditors.backend.price.PriceProvider;
import expeditors.backend.service.ArtistService;
import expeditors.backend.service.TrackService;
import expeditors.backend.utils.UriCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;
import java.time.Year;
import java.util.*;

@RestController
//@RequestMapping("/track")
@RequestMapping("/api/track")
public class TrackController {
    //Added Vincent
    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private PriceProvider priceProvider;

    @Autowired
    private UriCreator uriCreator;
    //Added Vincent
    @PostMapping("/createTrackWithArtists")
    public ResponseEntity<?> createTrack(@RequestBody Track entity) {
        try {
            Track track = trackService.addTrack(entity);
            priceProvider.addPriceToTrack(track);
            URI newResource = uriCreator.getURI(track.getId());
            return ResponseEntity.created(newResource).body(track);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error:" + e.getCause().getMessage());
        }

    }
    @GetMapping
    public ResponseEntity<?> getAllTracks() {
        List<Track> tracks = trackService.getAllTracks();
        if (tracks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Tracks");
        }
        tracks.forEach(track -> {priceProvider.addPriceToTrack(track);});
        return ResponseEntity.ok(tracks);
    }
    @GetMapping("/getTrack/{id}")
    public ResponseEntity<?> getTrack(@PathVariable("id") int id){
        Track track = trackService.getTrack(id);
        if (track == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track with id: " + id);
        }
        return ResponseEntity.ok(track);
    }
    @GetMapping("/getTracksByAlbum/{album}")
    public ResponseEntity<?> getTracksByAlbum(@PathVariable("album") String album) {
        List<Track> tracks = trackService.getTracksByAlbum(album);
        return ResponseEntity.ok(tracks);

    }
    @GetMapping("/getArtistsByTrack/{id}")
    public ResponseEntity<?> getArtists(@PathVariable("id") int id){
        Track track = trackService.getArtistsByTrack(id);
        if (track == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track with id: " + id);
        }
        return ResponseEntity.ok(track.getArtists());
    }
    @GetMapping("/getTracksByMediaType/{mediaType}")
    public ResponseEntity<?> getTracksByMediaType(@PathVariable("mediaType") MediaType mediaType) {
        List<Track> track = trackService.getTracksByMediaType(mediaType);
        if (track.isEmpty()) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with Media Type: " + mediaType);
        }
        return ResponseEntity.ok(track);
    }
    @GetMapping("/getTracksByYear/{issueDate}")
    public ResponseEntity<?> getTracksByYear(@PathVariable("issueDate") Year issueDate) {
        List<Track> track = trackService.getTracksByYear(issueDate.getValue());
        if (track.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track with Year: " + issueDate);
        }
        return ResponseEntity.ok(track);
    }
    @GetMapping("/getTracksByDuration")
    public ResponseEntity<?> getTracksByDuration(@RequestParam TypeDuration typeDuration, @RequestParam Duration duration) {
        if (!typeDuration.toString().isEmpty() && !duration.toString().isEmpty()) {
            List<Track> track = trackService.getTracksByDuration(typeDuration, duration);
            if (track.isEmpty()) {
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with this TypeDuration: " + typeDuration);
            }
            return ResponseEntity.ok(track);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No track with this TypeDuration: " + typeDuration);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable("id") int id){
        boolean result = trackService.deleteTrack(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateTrack(@RequestBody Track track){
        boolean result = trackService.updateTrack(track);
        if (track.getTitle() == null){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Track needs at least a title");
        }
        if(!result) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                    .body("No track with id: " + track.getId());
        }

        return ResponseEntity.noContent().build();
    }
}

