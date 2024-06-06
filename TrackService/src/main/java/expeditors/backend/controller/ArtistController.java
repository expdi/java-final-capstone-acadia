package expeditors.backend.controller;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import expeditors.backend.utils.UriCreator;
import expeditors.backend.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public String createArtist(@RequestBody Artist entity) {
        System.out.println("\nCreate a new Artist." + "\n");

        // Create a new artist
        Artist artist = new Artist(entity.getName());

        // Save the artist
        artist = artistRepo.save(artist);
        System.out.println("\nSaved artist :: " + artist + "\n");
        return "Artist saved!!!";
    }

    @PostMapping("createArtistForTrack/{trackId}")
    public String createArtistForTrack(@RequestBody Artist entity, @PathVariable(name = "trackId") Integer trackId) {
        //Create a new artist
        Artist artist = new Artist(entity.getName());

        //Save the artist
        artist = artistRepo.save(artist);
        System.out.println("\nSaved artist :: " + artist + "\n");

        //Get the track based on track id in the url
        Track track = this.trackRepo.getById(Integer.valueOf(trackId));
        System.out.println("\nTrack details :: " + track.toString() + "\n");

        //Create Artist set
        Set<Artist> artists = new HashSet<>();
        artists.add(artist);

        //Assign Track to Artist
        track.setArtists(artists);

        //Save Track
        track = trackRepo.save(track);

        System.out.println("\nArtist assigned to the Track." + "\n");

        return "Artist saved!!!";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable("id") int id){
        boolean result = artistService.deleteArtist(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No artist with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable("id") int id){
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

    //    @GetMapping("/artist/{id}/tracks")
//    public ResponseEntity<?> getArtists(@PathVariable("id") int id){
//        //do
//        List<Track> tracks = artistService.getTracksByArtist(id);
//        if (tracks == null) {
//            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No artist with id: " + id);
//        }
//        return ResponseEntity.ok(tracks);
//    }

    //Sean's code
    /*
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
    */
}
