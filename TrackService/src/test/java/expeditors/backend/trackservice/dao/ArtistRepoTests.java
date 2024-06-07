package expeditors.backend.trackservice.dao;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ArtistRepoTests {

    @Autowired
    ArtistRepo artistRepo;

    @Test
    public void testInsertArtist() {
        Artist artist1 = new Artist();
        artist1.setName("Jay Chou");
        artistRepo.save(artist1);
        Artist artist2 = artistRepo.findById(artist1.getId()).orElse(null);
        assertEquals(5, artist2.getId());
    }
    @Test
    public void testFindAllByNameContainingIgnoreCase(){
        List<Artist> artistList = artistRepo.findAllByNameContainingIgnoreCase("ay");
        assertEquals("Taylor Swift", artistList.getFirst().getName());
    }
    @Test
    public void testFindAllWithTracks(){
        List<Artist> artistList = artistRepo.findAllWithTracks();
        assertEquals(2,artistList.size());
    }

    @Test
    public void testUpdateArtist(){
        Artist artist1 = artistRepo.findById(2).orElse(null);
        artist1.setName("Jay Chou");
        artistRepo.save(artist1);
        assertEquals("Jay Chou", artistRepo.findById(2).get().getName());
    }
    @Test
    public void testDeleteArtist(){
        Artist artist1 = artistRepo.findById(2).orElse(null);
        artistRepo.delete(artist1);
        Artist artist2 = artistRepo.findById(2).orElse(null);
        assertEquals(null, artist2);
    }



//    @Test
//    public void testUpdate() {
//        int newId = studentRepo.save(student1).getId();
//
//        Student resultStudent = studentRepo.findById(newId).orElse(null);
//
//        assertEquals(newId, resultStudent.getId());
//
//        resultStudent.setName(newName);
//        studentRepo.save(resultStudent);
//
//        resultStudent = studentRepo.findById(newId).orElse(null);
//        assertEquals(newName, resultStudent.getName());
//    }


}
