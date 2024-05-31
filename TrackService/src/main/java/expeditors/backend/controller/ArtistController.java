package expeditors.backend.controller;

import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import expeditors.backend.utils.UriCreator;
import expeditors.backend.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private UriCreator uriCreator;

    @GetMapping("/artists")
    //get tracks longer/shorter/equal to specific duration
    public List<Artist> getAllArtists(@RequestParam Map<String,String> queryStrings) {
        List<Artist> artists = null;
        if(queryStrings.isEmpty()) {
            artists = artistService.getAllArtists();
        } else {
            artists = artistService.getAllArtistsByQueryParams(queryStrings);
        }

        return artists;
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<?> getArtist(@PathVariable("id") int id){
        Artist artist = artistService.getArtist(id);
        if (artist == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No artist with id: " + id);
        }
        return ResponseEntity.ok(artist);
    }

//    @GetMapping("/artist/{id}/tracks")
//    public ResponseEntity<?> getArtists(@PathVariable("id") int id){
//        //do
//        List<Track> tracks = artistService.getTracksByArtist(id);
//        if (tracks == null) {
//            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No artist with id: " + id);
//        }
//        return ResponseEntity.ok(tracks);
//    }

    @PostMapping("/artist")
    public ResponseEntity<?> insertArtist(@RequestBody Artist artist){
        Artist newArtist = artistService.addArtist(artist);
        if (newArtist != null) {
            URI newResource = uriCreator.getURI(newArtist.getId());
            return ResponseEntity.created(newResource).build();
        } else {
            // If the adopter was not added (for example, if it already exists), return HTTP status 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable("id") int id){
        boolean result = artistService.deleteArtist(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No artist with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/artist")
    public ResponseEntity<?> updateStudent(@RequestBody Artist artist){
        boolean result = artistService.updateArtist(artist);
        if (artist.getName() == null){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Artist needs at least a title");
        }
        if(!result) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                    .body("No artist with id: " + artist.getId());
        }

        return ResponseEntity.noContent().build();
    }

}
