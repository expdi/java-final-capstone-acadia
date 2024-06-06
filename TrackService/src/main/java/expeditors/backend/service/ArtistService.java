package expeditors.backend.service;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.Track;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@Transactional
public class ArtistService {
    @Autowired
    private ArtistRepo artistRepo;

    public Artist addArtist(Artist artist){
        return artistRepo.save(artist);
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

    public Artist getArtist(int id){
        return artistRepo.findById(id).orElse(null);
    }

    public List<Artist> getArtistByName(String name){
        return artistRepo.findArtistByNameContainingIgnoreCase(name);
    }
    public List<Artist> getAllArtists(){
        return artistRepo.findAllWithTracks();
    }

//    public List<Artist> getAllArtistsByQueryParams(Map<String, String> queryParams) {
//        Predicate<Artist> finalPred = null;
//        for(var entry : queryParams.entrySet()) {
//            var key = entry.getKey();
//            var value = entry.getValue();
//            if (key.equals("name")) {
//                Predicate<Artist> tmp = (a) -> a.getName().equals(value);
//                finalPred = finalPred == null ? tmp : finalPred.or(tmp);
//            }
//        }
//        finalPred = finalPred != null ? finalPred : (t) -> true;
//        List<Artist> result = getAllArtists().stream()
//                .filter(finalPred)
//                .toList();
//
//        return result;
//    }

    public List<Track> getTrackByArtist(String name){
        return  artistRepo.getTrackByArtist(name);
    }








}
