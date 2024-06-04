package expeditors.backend.dao;

import expeditors.backend.domain.Artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistRepo extends JpaRepository<Artist, Integer> {
    @Query("select a from Artist a left join fetch a.tracks")
    List<Artist> findAllWithTracks();

}
