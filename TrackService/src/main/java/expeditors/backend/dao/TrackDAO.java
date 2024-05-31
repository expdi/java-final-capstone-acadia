package expeditors.backend.dao;
import expeditors.backend.domain.Track;

import java.util.List;

public interface TrackDAO {
    Track insert(Track newTrack);
    Track findById(int id);
    List<Track> findAll();
    boolean update(Track track);
    boolean delete(int id);
    void reset();

}
