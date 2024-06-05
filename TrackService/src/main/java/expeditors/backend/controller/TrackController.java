package expeditors.backend.controller;

import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import expeditors.backend.service.ArtistService;
import expeditors.backend.service.TrackService;
import expeditors.backend.utils.UriCreator;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
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
    private UriCreator uriCreator;
    @GetMapping(path = "/holamundo")
    public ResponseEntity<?> getHolaMundo() {
        return ResponseEntity.ok("Hola mundo!!");
    }
    //Added Vincent
    @PostMapping("/createTrack/{artistId}")
    public ResponseEntity<?> createTrack(@RequestBody Track entity,@PathVariable(name = "artistId") int artistId) {
        System.out.println("\nCreate a new Track.\n");

        Track track = trackService.addTrack(entity);

        System.out.println("\nSaved Track :: " + track + "\n");
        Artist artist = artistService.getArtist(artistId);
        Set<Track> tracks = new HashSet<>();
        tracks.add(track);

        artist.setTracks(tracks);

        artistService.addArtist(artist);

        URI newResource = uriCreator.getURI(track.getId());
        return ResponseEntity.created(newResource).build();

    }

//    @GetMapping
//    //get tracks longer/shorter/equal to specific duration
//    public List<Track> getAllTracks(@RequestParam Map<String,String> queryStrings) {
//        List<Track> tracks = null;
//        if(queryStrings.isEmpty()) {
//            tracks = trackService.getAllTracks();
//        } else {
//            tracks = trackService.getAllTracksByQueryParams(queryStrings);
//        }
//
//        return tracks;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrack(@PathVariable("id") int id){
        Track track = trackService.getTrack(id);
        if (track == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with id: " + id);
        }
        return ResponseEntity.ok(track);
    }

//    @GetMapping("/{id}/artists")
//    public ResponseEntity<?> getArtists(@PathVariable("id") int id){
//        List<Artist> artists = trackService.getArtistsByTrack(id);
//        if (artists == null) {
//            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with id: " + id);
//        }
//        return ResponseEntity.ok(artists);
//    }



//    @PostMapping
//    public ResponseEntity<?> insertTrack(@RequestBody Track track){
//        Track newTrack = trackService.addTrack(track);
//        if (newTrack != null) {
//            URI newResource = uriCreator.getURI(newTrack.getId());
//            return ResponseEntity.created(newResource).build();
//        } else {
//            // If the adopter was not added (for example, if it already exists), return HTTP status 409 Conflict
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable("id") int id){
        boolean result = trackService.deleteTrack(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Track track){
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

