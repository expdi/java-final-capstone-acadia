package expeditors.backend.service;

import expeditors.backend.dao.TrackDAO;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.price.PriceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class TrackService {

    @Autowired
    private TrackDAO trackDAO;

    @Autowired
    private PriceProvider priceProvider;

    public Track addTrack(Track track){
        return trackDAO.insert(track);
    }
    public Track getTrack(int id){
        Track track = trackDAO.findById(id);
        if (track != null){
            priceProvider.addPriceToTrack(track);
        }
        return track;
    }
    public List<Track> getAllTracks(){
        List<Track> allTracks = trackDAO.findAll();
        allTracks.forEach(priceProvider :: addPriceToTrack);
        return allTracks;
    }
    public List<Track> getAllTracksByQueryParams(Map<String, String> queryParams) {
        Predicate<Track> finalPred = null;
        if(queryParams.containsKey("durationOption") && queryParams.containsKey("minutes") && queryParams.containsKey("seconds")){
            int totalSeconds = Integer.parseInt(queryParams.get("minutes")) * 60 + Integer.parseInt(queryParams.get("seconds"));
            if(queryParams.get("durationOption").equals("longer")){
                Predicate<Track> tmp = (t) -> t.getDuration().getSeconds() > totalSeconds;
                finalPred = finalPred == null ? tmp : finalPred.or(tmp);
            }
            if(queryParams.get("durationOption").equals("equal")){
                Predicate<Track> tmp = (t) -> t.getDuration().getSeconds() == totalSeconds;
                finalPred = finalPred == null ? tmp : finalPred.or(tmp);
            }
            if(queryParams.get("durationOption").equals("shorter")){
                Predicate<Track> tmp = (t) -> t.getDuration().getSeconds() < totalSeconds;
                finalPred = finalPred == null ? tmp : finalPred.or(tmp);
            }
        }
        for(var entry : queryParams.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            if (key.equals("mediaType")) {
                var mediaTypeEnum = MediaType.valueOf(value);
                Predicate<Track> tmp = (t) -> t.getMediaType().equals(mediaTypeEnum);
                finalPred = finalPred == null ? tmp : finalPred.or(tmp);
            }
            if (key.equals("year")){
                int yearInt = Integer.valueOf(value);
                Predicate<Track> tmp = (t) -> Integer.valueOf(t.getIssueDate().getYear()).equals(yearInt);
                finalPred = finalPred == null ? tmp : finalPred.or(tmp);
            }
        }
        finalPred = finalPred != null ? finalPred : (t) -> true;
        List<Track> result = getAllTracks().stream()
                .filter(finalPred)
                .toList();

        return result;
    }



    public List<Artist> getArtistsByTrack(int id){
        Track track = trackDAO.findById(id);
        return track.getArtists();
    }
    public boolean deleteTrack(int id){
        return trackDAO.delete(id);
    }

    public boolean updateTrack(Track track){
        return trackDAO.update(track);
    }

    public void deleteAllTracks() {trackDAO.reset();}

}
