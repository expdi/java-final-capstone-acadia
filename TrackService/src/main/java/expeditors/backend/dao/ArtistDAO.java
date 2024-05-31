package expeditors.backend.dao;

import expeditors.backend.domain.Artist;
import java.util.List;

public interface ArtistDAO {

    Artist insert(Artist newArtist);
    Artist findById(int id);
    List<Artist> findAll();
    boolean update(Artist artist);
    boolean delete(int id);
}
