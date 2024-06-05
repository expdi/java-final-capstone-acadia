package expeditors.backend.service;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.domain.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepo artistRepo;

    public Artist addArtist(Artist artist){
        return artistRepo.save(artist);
    }


    public List<Artist> getAllArtists(){
        List<Artist> allArtists = artistRepo.findAllWithTracks();
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
        Artist artist = artistRepo.findById(id).orElse(null);;
        return artist;
    }

    public boolean deleteArtist(int id) {
        Artist artist = artistRepo.findById(id).orElse(null);
        if (artist != null) {
            artistRepo.delete(artist);
            return true;
        }
        return false;
    }

    public boolean updateArtist(Artist artist) {
        Artist oldArtist = artistRepo.findById(artist.getId()).orElse(null);
        if (oldArtist != null) {
            artistRepo.save(artist);
            return true;
        }
        return false;
    }



}
