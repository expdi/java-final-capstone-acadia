package expeditors.backend.trackservice.dao;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.domain.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ArtistRepoTests {

    @Autowired
    ArtistRepo artistRepo;

    @Test
    public void testFindAllByNameContainingIgnoreCase(){
        List<Artist> artistList = artistRepo.findAllByNameContainingIgnoreCase("ay");
        assertEquals("Taylor Swift", artistList.getFirst().getName());
    }
}
