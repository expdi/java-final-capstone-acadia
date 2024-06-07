package expeditors.backend.dao;

import expeditors.backend.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
public interface TrackRepo extends JpaRepository<Track, Integer> {
    @Query("select t from Track t where extract(year from t.issueDate) = :year")
    List<Track> findByYear(@Param("year") int year);

    List<Track> findByDurationGreaterThanEqual(Duration duration);

    List<Track> findByDurationLessThanEqual(Duration duration);

    List<Track>findByAlbum(@Param("album") String album);

    @Query("select t from Track t where t.mediaType = :mediaType")
    List<Track> getTracksByMediaType(@Param("mediaType") int mediaType);

}
