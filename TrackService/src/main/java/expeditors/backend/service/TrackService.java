package expeditors.backend.service;

import expeditors.backend.dao.TrackRepo;
import expeditors.backend.domain.Artist;
import expeditors.backend.domain.MediaType;
import expeditors.backend.domain.Track;
import expeditors.backend.domain.TypeDuration;
import expeditors.backend.price.PriceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrackService {

    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private PriceProvider priceProvider;

    public Track addTrack(Track track){

//        long time = track.getDurationAux().getSeconds();
//        track.setDuration((double) time);
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
//    public List<Track> getTrackByDuration(TypeDuration typeDuration, Duration duration) {
//        String time = formatDuration(duration);
//        Time time1 = Time.valueOf(time);
//        return switch (typeDuration) {
//            case Longer ->
//                    trackRepo.findAll().stream().filter(f -> Time.valueOf(formatDuration(f.getDuration()) > Time.valueOf(time).getTime()).collect(Collectors.toList());
//            case Shorted ->
//                    trackRepo.findAll().stream().filter(f -> f.getDuration().abs().getSeconds() < Time.valueOf(time).getTime()).collect(Collectors.toList());
//            case Equal ->
//                    trackRepo.findAll().stream().filter(f -> f.getDuration().abs().getSeconds() == Time.valueOf(time).getTime()).collect(Collectors.toList());
//        };
//    }
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
