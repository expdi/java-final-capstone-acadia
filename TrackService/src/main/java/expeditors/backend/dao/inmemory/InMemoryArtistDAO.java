package expeditors.backend.dao.inmemory;

import expeditors.backend.dao.ArtistDAO;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryArtistDAO implements ArtistDAO {
    private Map<Integer, Artist> artists = new HashMap<>();
    private int nextId = 1;

    @Override
    public Artist insert (Artist artist){
        int newId = nextId++;
        artist.setId(newId);
        artists.put(artist.getId(), artist);
        return artist;
    }

    @Override
    public Artist findById(int id) {
        return artists.get(id);
    }

    @Override
    public List<Artist> findAll() {
        return new ArrayList<>(artists.values());
    }

    @Override
    public boolean update(Artist artist) {
        return artists.replace(artist.getId(), artist) != null;
    }

    @Override
    public boolean delete(int id) {
        return artists.remove(id) != null;
    }


}
