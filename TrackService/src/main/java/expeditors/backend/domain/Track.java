package expeditors.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "Track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "album")
    private String album;

    @Column(name = "issueDate")
    private LocalDate issueDate;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "mediaType")
    private int mediaType;
    private transient MediaType mediaTypeEnum;

    @JacksonXmlElementWrapper(localName = "classes")
    @JacksonXmlProperty(localName = "class")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {  CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @Setter(onMethod_ = @__(@JsonProperty))
    @JoinTable(name = "Artist_Track", joinColumns = @JoinColumn(name = "track_id"), foreignKey = @ForeignKey(name = "fk_artist_track_track"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"), inverseForeignKey = @ForeignKey(name = "fk_artist_track_artist"))
    private Set<Artist> artists = new HashSet<>();

    public Track(String title, String album, LocalDate issueDate, Duration duration, int mediaType, String price) {
        this.title = title;
        this.album = album;
        this.issueDate = issueDate;
        this.duration = duration;
        this.mediaType = mediaType;
        this.price = price;
    }

    @Column(name = "price")
    private String price;


}
