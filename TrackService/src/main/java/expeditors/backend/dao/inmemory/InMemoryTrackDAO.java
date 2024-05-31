package expeditors.backend.dao.inmemory;

import expeditors.backend.domain.Track;
import expeditors.backend.dao.TrackDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryTrackDAO implements TrackDAO {
    private Map<Integer, Track> tracks = new HashMap<>();
    private int nextId = 1;
    @Override
    public Track insert (Track track){
        int newId = nextId++;
        track.setId(newId);
        tracks.put(track.getId(), track);
        return track;
    }

    @Override
    public Track findById(int id) {
        return tracks.get(id);
    }

    @Override
    public List<Track> findAll() {
        return new ArrayList<>(tracks.values());
    }

    @Override
    public boolean update(Track track) {
        return tracks.replace(track.getId(), track) != null;
    }

    @Override
    public boolean delete(int id) {
        return tracks.remove(id) != null;
    }

    @Override
    public void reset(){
        nextId = 1;
        tracks.clear();}
}
