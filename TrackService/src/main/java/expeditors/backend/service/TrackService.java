package expeditors.backend.service;

import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.price.PriceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class TrackService {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private PriceProvider priceProvider;

    public Track addTrack(Track track){
        return trackRepo.save(track);
    }
    public Track getTrack(int id){
        Track track = trackRepo.findById(id).orElse(null);
        if (track != null){
            priceProvider.addPriceToTrack(track);
        }
        return track;
    }
    public List<Track> getAllTracks(){
        return trackRepo.findAll();
    }

    //TODO: getAllTracksByQueryParams

    //TODO: getArtistsByTrack

    public boolean deleteTrack(int id) {
        Track track = trackRepo.findById(id).orElse(null);
        if (track != null) {
            trackRepo.delete(track);
            return true;
        }
        return false;
    }

    public boolean updateTrack(Track track) {
        Track oldTrack = trackRepo.findById(track.getId()).orElse(null);
        if (oldTrack != null) {
            trackRepo.save(track);
            return true;
        }
        return false;
    }
    public void deleteAllTracks() {
        trackRepo.deleteAll();}

}
