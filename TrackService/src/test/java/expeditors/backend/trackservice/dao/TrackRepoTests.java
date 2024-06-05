package expeditors.backend.trackservice.dao;

import expeditors.backend.dao.TrackRepo;

import expeditors.backend.domain.Track;
import expeditors.backend.service.TrackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrackRepoTests {

    @Autowired
    TrackRepo trackRepo;

    @Autowired
    TrackService trackService;

    @Test
    public void testFindByYear(){
        //Use repository
        //List<Track> trackList = trackRepo.findByYear(1983);

        //Use service
        List<Track> trackList = trackService.getAlbumByYear(2024);

        assertEquals(1, trackList.size());
    }

    @Test
    public void testFindByAlbum() {
        //Use repository
        //List<Track> trackList = trackRepo.findByAlbum("Dark Side Of The Moon");

        //Use service
        List<Track> trackList = trackService.getTracksByAlbum("Red");

        assertEquals(1, trackList.size());
    }
}
