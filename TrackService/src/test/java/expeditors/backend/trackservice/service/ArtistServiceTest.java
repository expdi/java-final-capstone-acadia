package expeditors.backend.trackservice.service;

import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import expeditors.backend.service.ArtistService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ArtistServiceTest {

    @Autowired
    private ArtistService artistService;

    @Test
    public void testAddArtist(){
        Artist artist1 = new Artist();
        artist1.setName("Jay Chou");
        artistService.addArtist(artist1);
        assertEquals(3, artistService.getAllArtists().size());
        assertEquals("Jay Chou",artistService.getArtist(3).getName());
    }

    @Test
    public void testDeleteArtist(){
        artistService.deleteArtist(1);
        assertEquals(1, artistService.getAllArtists().size());
    }

    @Test
    public void testUpdateArtist(){
        Artist artist1 = artistService.getArtist(2);
        artist1.setName("Jay Chou");
        artistService.updateArtist(artist1);
        assertEquals(2, artistService.getAllArtists().size());
        assertEquals("Jay Chou",artistService.getArtist(2).getName());
    }

    @Test
    public void testGetArtist(){
        Artist artist = artistService.getArtist(1);
        assertEquals(1, artist.getId());
    }

    @Test
    public void testGetArtistByName(){
        List<Artist> artist = artistService.getArtistByName("Taylor");
        assertEquals(1, artist.size());
    }

    @Test
    public void testGetAllArtists(){
        List<Artist> artists = artistService.getAllArtists();
        assertEquals(2, artists.size());
    }

    @Test
    public void testGetTrackByArtist(){
        List<Track> tracks = artistService.getTrackByArtist("Taylor Swift");
        assertEquals(1, tracks.size());
    }

}
