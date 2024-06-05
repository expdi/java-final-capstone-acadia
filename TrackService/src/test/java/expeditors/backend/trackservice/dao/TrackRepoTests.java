package expeditors.backend.trackservice.dao;

import expeditors.backend.dao.TrackRepo;

import expeditors.backend.domain.Track;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrackRepoTests {

    @Autowired
    TrackRepo trackRepo;

    @Test
    public void testFindByYear(){
        List<Track> trackList = trackRepo.findByYear(2024);
        assertEquals("Red", trackList.getFirst().getAlbum());
    }
}
