package expeditors.backend.trackservice.service;

import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.domain.TypeDuration;
import expeditors.backend.service.TrackService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@ActiveProfiles("localprice")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TrackServiceTest {

    @Autowired
    private TrackService trackService;

    @Test
    public void testAddTrack(){
        Track track1 = new Track();
        track1.setTitle("Money");
        track1.setAlbum("Dark Side of the Moon");
        track1.setMediaType(1);
        trackService.addTrack(track1);
        assertEquals(3, trackService.getAllTracks().size());
        assertEquals("Money",trackService.getTrack(3).getTitle());
    }

    @Test
    public void testGetTrack(){
        Track track = trackService.getTrack(1);
        assertEquals(1, track.getId());
    }

    @Test
    public void testGetNonExistingTrack(){
        Track track = trackService.getTrack(1000);
        assertNull(track);
    }

    @Test
    public void testGetAllTracks(){
        List<Track> tracks = trackService.getAllTracks();
        assertEquals(2, tracks.size());
    }

    @Test
    public void testDeleteTrack(){
        trackService.deleteTrack(1);
        assertEquals(1, trackService.getAllTracks().size());
    }

    @Test
    public void testUpdateTrack(){
        Track track1 = trackService.getTrack(2);
        track1.setTitle("Another Brick on the Wall");
        trackService.updateTrack(track1);
        assertEquals(2, trackService.getAllTracks().size());
        assertEquals("Another Brick on the Wall",trackService.getTrack(2).getTitle());
    }

    @Test
    public void testDeleteAllTracks(){
        trackService.deleteAllTracks();
        assertEquals(0, trackService.getAllTracks().size());
    }

    @Test
    public void testGetTracksByAlbum(){
        List<Track> tracks = trackService.getTracksByAlbum("Red");
        assertEquals(1, tracks.size());
    }

    @Test
//    @Transactional
    public void testGetTracksByYear(){
        List<Track> trackList = trackService.getTracksByYear(2024);

        assertEquals(1, trackList.size());
    }

    @Test
    public void testGetArtistsByTrack() {
        //TO DO:
    }

    @Test
    public void testGetTrackByMediaType() {
        List<Track> tracks = trackService.getTracksByMediaType(MediaType.MP3);
        assertEquals(2, tracks.size());
    }

    @Test
//    @Rollback(false)
    public void testGetTrackByDuration() {
        Track track1 = new Track();
        track1.setTitle("Money");
        track1.setAlbum("Dark Side of the Moon");
        track1.setDuration(Duration.parse("PT4M27S"));
        trackService.addTrack(track1);
        List<Track> tracks = trackService.getTracksByDuration(TypeDuration.Longer, Duration.parse("PT3M15S"));
        assertEquals(1, tracks.size());
    }


}
