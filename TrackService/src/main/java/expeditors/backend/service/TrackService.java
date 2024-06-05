package expeditors.backend.service;

import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.price.PriceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrackService {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private PriceProvider priceProvider;

    public Track addTrack(Track track){
        track.setMediaType(track.getMediaTypeEnum().ordinal());
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


    public List<Track> getTracksByAlbum(String album) {
        return trackRepo.findByAlbum(album);
    }

    public List<Track> getAlbumByYear(Integer year) {
        return trackRepo.findByYear(year);
    }

    public Track getArtistsByTrack(int id) {
        return trackRepo.findById(id).orElse(null);
    }
    public List<Track> getTrackByMediaType(MediaType mediaType) {
        List<Track> trackList = trackRepo.getTrackByMediaType(mediaType.ordinal());
        trackList.forEach(fe -> {priceProvider.addPriceToTrack(fe);fe.setMediaTypeEnum(mediaType);});
        return trackList;
    }
}
