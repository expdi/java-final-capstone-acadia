package expeditors.backend.trackservice.service;

import expeditors.backend.dao.TrackDAO;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.price.PriceProvider;
import expeditors.backend.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("localprice")
public class TrackServiceUnitTest {

    @Mock
    private TrackDAO trackDAO;

    @Mock
    private PriceProvider priceProvider;

    @InjectMocks
    private TrackService trackservice;

    List<Track> tracks = List.of(
            new Track(1,"Standing Next to You", "COMING HOME",
                    List.of( new Artist.ArtistBuilder().id(1).name("JungKook").build(), new Artist.ArtistBuilder().id(2).name("Usher").build()),
                    LocalDate.of(2024,2,9), Duration.ofMinutes(3).plusSeconds(35), MediaType.OGG),
            new Track(2,"Cruel Summer", "Lover",
                    List.of( new Artist.ArtistBuilder().id(3).name("Taylor Swift").build()),
                    LocalDate.of(2023,8,23), Duration.ofMinutes(2).plusSeconds(59), MediaType.MP3),
            new Track(3,"Cruel Summer - Remix(?)", null,
                    null,
                    LocalDate.of(2024,4,24), Duration.ofMinutes(1).plusSeconds(19), MediaType.FLAC)
    );
    @Test
    public void testAddAdopter() {
        int id = 0 ;
        Mockito.when(trackDAO.insert(tracks.getFirst())).thenReturn(tracks.getFirst());
        Track track = trackservice.addTrack(tracks.getFirst());
        assertEquals(id + 1,track.getId());
        Mockito.verify(trackDAO).insert(tracks.getFirst());

    }

    @Test
    public void testGetAll() {
        Mockito.when(trackDAO.findAll()).thenReturn(tracks);
        Mockito.doNothing().when(priceProvider).addPriceToTrack(Mockito.any(Track.class));
        List<Track> tracks = trackservice.getAllTracks();
        tracks.forEach(System.out::println);
        assertEquals(3,tracks.size());
        Mockito.verify(trackDAO).findAll();
    }

    @Test
    public void testGetValidId() {
        Mockito.when(trackDAO.findById(1)).thenReturn(tracks.getFirst());
        Mockito.doNothing().when(priceProvider).addPriceToTrack(Mockito.any(Track.class));
        Track track = trackservice.getTrack(1);
        assertNotNull(track);
        Mockito.verify(trackDAO).findById(1);
    }
    @Test
    public void testGetNonexistentId() {
        int id = 1000;
        Mockito.when(trackDAO.findById(id)).thenReturn((null));
        Track track = trackservice.getTrack(id);
        assertNull(track);
        Mockito.verify(trackDAO).findById(id);
    }

    //test delete existing

    // test delete non existing

    // test update existing

    // test update non existing
}
