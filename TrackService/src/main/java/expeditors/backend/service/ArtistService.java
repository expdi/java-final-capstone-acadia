package expeditors.backend.service;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtistService {
    @Autowired
    private ArtistRepo artistRepo;
    @Autowired
    private TrackRepo tracksRepo;

    public Artist addArtist(Artist artist) {
        Artist artistSaved = artistRepo.save(artist);

        artistSaved.getTracks().forEach(track -> {
            Track track1 = tracksRepo.findById(track.getId()).get();
            track.setTitle(track1.getTitle());
            track.setAlbum(track1.getAlbum());
            track.setIssueDate(track1.getIssueDate());
            track.setDuration(track1.getDuration());
            track.setMediaType(track1.getMediaType());
            track.setPrice(track1.getPrice());
            track.setMediaTypeEnum(MediaType.values()[track1.getMediaType()]);
        });
        return artistSaved;
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

    public Artist getArtist(int id) {
        return artistRepo.findById(id).orElse(null);
    }

    public List<Artist> getArtistByName(String name) {
        return artistRepo.findArtistByNameContainingIgnoreCase(name);
    }

    public List<Artist> getAllArtists() {
        return artistRepo.findAllWithTracks();
    }

    public List<Track> getTrackByArtist(String name) {
        return artistRepo.getTrackByArtist(name);
    }


}
