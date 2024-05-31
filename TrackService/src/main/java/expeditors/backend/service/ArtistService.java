package expeditors.backend.service;

import expeditors.backend.dao.ArtistDAO;
import expeditors.backend.dao.TrackDAO;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class ArtistService {
    @Autowired
    private ArtistDAO artistDAO;

    @Autowired
    private TrackDAO trackDAO;

    public Artist addArtist(Artist artist){
        return artistDAO.insert(artist);
    }


    public List<Artist> getAllArtists(){
        List<Artist> allArtists = artistDAO.findAll();
        return allArtists;
    }
    public List<Artist> getAllArtistsByQueryParams(Map<String, String> queryParams) {
        Predicate<Artist> finalPred = null;
        for(var entry : queryParams.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            if (key.equals("name")) {
                Predicate<Artist> tmp = (a) -> a.getName().equals(value);
                finalPred = finalPred == null ? tmp : finalPred.or(tmp);
            }
        }
        finalPred = finalPred != null ? finalPred : (t) -> true;
        List<Artist> result = getAllArtists().stream()
                .filter(finalPred)
                .toList();

        return result;
    }
    // TODO: did not complete this in time
//    public List<Track> getTracksByArtist(int id){
//        List<Track> tracks = trackDAO.findAll();
//    }
    public Artist getArtist(int id){
        Artist artist = artistDAO.findById(id);
        return artist;
    }
    public boolean deleteArtist(int id){
        return artistDAO.delete(id);
    }

    public boolean updateArtist(Artist artist){
        return artistDAO.update(artist);
    }



}
