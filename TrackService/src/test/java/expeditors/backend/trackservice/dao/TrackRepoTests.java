package expeditors.backend.trackservice.dao;

import expeditors.backend.dao.TrackRepo;

import expeditors.backend.domain.Track;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrackRepoTests {

    @Autowired
    TrackRepo trackRepo;

    @Test
    public void testInsertTrack() {
        Track track1 = new Track();
        track1.setTitle("Standing Next to You");
        trackRepo.save(track1);
        Track track2 = trackRepo.findById(track1.getId()).orElse(null);
        System.out.println(track2);
    }

    @Test
    public void testFindByYear(){
        List<Track> trackList = trackRepo.findByYear(2024);
        assertEquals("Red", trackList.getFirst().getAlbum());
    }

    @Test
    public void testFindByDurationGreaterThanEqual(){
        Track track1 = new Track();
        track1.setTitle("Perfect");
        track1.setDuration(Duration.ofMinutes(4));
        trackRepo.save(track1);
        Track track2 = new Track();
        track2.setTitle("Love Only Knows");
        track2.setDuration(Duration.ofMinutes(2));
        trackRepo.save(track2);
        List<Track> trackList = trackRepo.findByDurationGreaterThanEqual(3);
        assertEquals(3, trackList.size());
    }

}
