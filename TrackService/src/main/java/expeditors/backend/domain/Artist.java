package expeditors.backend.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="Artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;


    public static class ArtistBuilder {
        private int id;
        private String name;

        public ArtistBuilder id(int id){
            this.id = id;
            return this;
        }

        public ArtistBuilder name(String name){
            this.name = name;
            return this;
        }

        public Artist build() {
            return new Artist(id, name);
        }

    }


    @Override
    public String toString(){
        return String.format("Artist{id=%d, name='%s'}", id, name);
    }


}
