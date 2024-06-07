package expeditors.backend.trackservice.dao;

import expeditors.backend.dao.TrackRepo;

import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import expeditors.backend.service.TrackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class TrackRepoTests {

    @Autowired
    TrackRepo trackRepo;

    @Test
    public void testInsertTrack() {
        Track track1 = new Track();
        track1.setTitle("Standing Next to You");
        trackRepo.save(track1);
        Track track2 = trackRepo.findById(track1.getId()).orElse(null);
        assertEquals(3, track2.getId());
    }

    @Test
    public void testFindByYear(){
        List<Track> trackList = trackRepo.findByYear(2024);
        assertEquals("Red", trackList.getFirst().getAlbum());
    }

    @Test
    public void testFindByAlbum(){
        List<Track> trackList = trackRepo.findByAlbum("Red");
        assertEquals("Red", trackList.getFirst().getAlbum());
    }


    @Test
//    @Rollback(false)
    public void testFindByDurationGreaterThanEqual(){
        Track track1 = new Track();
        track1.setTitle("Perfect");
        track1.setDuration(Duration.parse( "PT0H2M44S" ));
        trackRepo.save(track1);
        Track track2 = new Track();
        track2.setTitle("Love Only Knows");
        track2.setDuration(Duration.ofMinutes(2));
        trackRepo.save(track2);
//        List<Track> trackList = trackRepo.findByDurationGreaterThanEqual(Duration.ofMinutes(3));
        List<Track> trackList = trackRepo.findByDurationGreaterThanEqual(Duration.ofMinutes(3));
        assertEquals(2, trackList.size());
    }

    @Test
    public void testFindByDurationLessThanEqual(){
        Track track1 = new Track();
        track1.setTitle("Perfect");
        track1.setDuration(Duration.ofMinutes(4));
        trackRepo.save(track1);
        Track track2 = new Track();
        track2.setTitle("Love Only Knows");
        track2.setDuration(Duration.ofMinutes(2));
        trackRepo.save(track2);
        List<Track> trackList = trackRepo.findByDurationLessThanEqual(Duration.ofMinutes(2));
        assertEquals(1, trackList.size());
    }

    @Test
    public void testUpdateTrack(){
        Track track1 = trackRepo.findById(2).orElse(null);
        track1.setTitle("Another Brick on the Wall");
        trackRepo.save(track1);
        assertEquals("Another Brick on the Wall", trackRepo.findById(2).get().getTitle());
    }
    @Test
    public void testDeleteTrack(){
        Track track1 = trackRepo.findById(2).orElse(null);
        trackRepo.delete(track1);
        Track track2 = trackRepo.findById(2).orElse(null);
        assertEquals(null, track2);
    }

}
