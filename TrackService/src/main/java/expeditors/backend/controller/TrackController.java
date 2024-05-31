package expeditors.backend.controller;

import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import expeditors.backend.service.TrackService;
import expeditors.backend.utils.UriCreator;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @Autowired
    private UriCreator uriCreator;

    @GetMapping
    //get tracks longer/shorter/equal to specific duration
    public List<Track> getAllTracks(@RequestParam Map<String,String> queryStrings) {
        List<Track> tracks = null;
        if(queryStrings.isEmpty()) {
            tracks = trackService.getAllTracks();
        } else {
            tracks = trackService.getAllTracksByQueryParams(queryStrings);
        }

        return tracks;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrack(@PathVariable("id") int id){
        Track track = trackService.getTrack(id);
        if (track == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with id: " + id);
        }
        return ResponseEntity.ok(track);
    }

    @GetMapping("/{id}/artists")
    public ResponseEntity<?> getArtists(@PathVariable("id") int id){
        List<Artist> artists = trackService.getArtistsByTrack(id);
        if (artists == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No track with id: " + id);
        }
        return ResponseEntity.ok(artists);
    }



    @PostMapping
    public ResponseEntity<?> insertTrack(@RequestBody Track track){
        Track newTrack = trackService.addTrack(track);
        if (newTrack != null) {
            URI newResource = uriCreator.getURI(newTrack.getId());
            return ResponseEntity.created(newResource).build();
        } else {
            // If the adopter was not added (for example, if it already exists), return HTTP status 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
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

