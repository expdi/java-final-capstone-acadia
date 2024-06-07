package expeditors.backend.service;

import expeditors.backend.dao.ArtistRepo;
import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.domain.TypeDuration;
import expeditors.backend.price.PriceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrackService {

    @Autowired
    private TrackRepo trackRepo;
    @Autowired
    private ArtistRepo artistRepo;

    @Autowired
    private PriceProvider priceProvider;

    public Track addTrack(Track track) {
        Track savedTracks = trackRepo.save(track);

        savedTracks.getArtists().forEach(artist -> {
            Artist artist1 = artistRepo.findById(artist.getId()).get();
            artist.setName(artist1.getName());

        });
        return savedTracks;
    }

    public Track getTrack(int id) {
        Track track = trackRepo.findById(id).orElse(null);
        if (track != null) {
            track.setMediaTypeEnum(MediaType.values()[track.getMediaType()]);
            priceProvider.addPriceToTrack(track);
        }
        return track;
    }

    public List<Track> getAllTracks() {
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
        trackRepo.deleteAll();
    }

    public List<Track> getTracksByAlbum(String album) {
        return trackRepo.findByAlbum(album);
    }

    public List<Track> getTracksByYear(Integer year) {
        return trackRepo.findByYear(year);
    }

    //TO DO: Fix corresponding controller
    public Set<Artist> getArtistsByTrack(int id) {
        Track track = trackRepo.findById(id).orElse(null);
        if (track != null) {
            Set<Artist> artists = track.getArtists();
            return artists;
        }
        return null;
    }

    //    public Track getArtistsByTrack(int id) {
//        return trackRepo.findById(id).orElse(null);
//    }
    public List<Track> getTracksByMediaType(MediaType mediaType) {
        List<Track> trackList = trackRepo.getTracksByMediaType(mediaType.ordinal());
        trackList.forEach(fe -> {
            priceProvider.addPriceToTrack(fe);
            fe.setMediaTypeEnum(mediaType);
        });
        return trackList;
    }

    public List<Track> getTracksByDuration(TypeDuration typeDuration, Duration duration) {
        return switch (typeDuration) {
            case Longer ->
                    trackRepo.findAll().stream().filter(f -> Time.valueOf(formatDuration(f.getDuration())).after(Time.valueOf(formatDuration(duration)))).collect(Collectors.toList());
            case Shorted ->
                    trackRepo.findAll().stream().filter(f -> Time.valueOf(formatDuration(f.getDuration())).before(Time.valueOf(formatDuration(duration)))).collect(Collectors.toList());
            case Equal ->
                    trackRepo.findAll().stream().filter(f -> Time.valueOf(formatDuration(f.getDuration())).equals(Time.valueOf(formatDuration(duration)))).collect(Collectors.toList());
        };
    }

    public TrackRepo getTrack() {
        return trackRepo;
    }

    public void setTrack(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}
