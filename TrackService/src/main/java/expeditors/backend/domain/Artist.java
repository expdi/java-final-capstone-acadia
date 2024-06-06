package expeditors.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "Artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    //Sean's code
    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY)
    @Getter(onMethod_ = @__(@JsonIgnore))
    @Setter(onMethod_ = @__(@JsonProperty))
    //@JsonProperty(access = Access.WRITE_ONLY)
    private Set<Track> tracks = new HashSet<>();


    //Vincent's code
//    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
//    @JoinTable(name = "artist_track", joinColumns = @JoinColumn(name = "artist_id"),
//            inverseJoinColumns = @JoinColumn(name = "track_id"))
//    private Set<Track> tracks;

    // Added by Vincent
    public Artist(String name) {
        this.name = name;
    }

    //    public static class ArtistBuilder {
//        private int id;
//        private String name;
//
//        public ArtistBuilder id(int id){
//            this.id = id;
//            return this;
//        }

//        public ArtistBuilder name(String name){
//            this.name = name;
//            return this;
//        }

//        public Artist build() {
//            return new Artist(id, name);
//        }

//    }


//    @Override
//    public String toString(){
//        return String.format("Artist{id=%d, name='%s'}", id, name);
//    }


}
