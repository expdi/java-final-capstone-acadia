package expeditors.backend.dao;

import expeditors.backend.domain.Artist;

import expeditors.backend.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistRepo extends JpaRepository<Artist, Integer> {
    @Query("select a from Artist a left join fetch a.tracks")
    List<Artist> findAllWithTracks();

    List<Artist> findAllByNameContainingIgnoreCase(String name);

    List<Artist> findArtistByNameContainingIgnoreCase(String name);

    @Query("select t from Track t join fetch t.artists a where a.name= :name")
    List<Track> getTrackByArtist(String name);

}
