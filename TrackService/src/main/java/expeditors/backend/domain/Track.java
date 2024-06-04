package expeditors.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="Track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="album")
    private String album;

    @Column(name="issueDate")
    private LocalDate issueDate;

    @Column(name="duration")
    private Duration duration;

    @Column(name="mediaType")
    private MediaType mediaType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "Artist_Track", joinColumns = @JoinColumn(name = "track_id"), foreignKey = @ForeignKey(name="fk_artist_track_track"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"), inverseForeignKey = @ForeignKey(name="fk_artist_track_artist"))
    private List<Artist> artists;


    @Column(name="price")
    private String price;





}
