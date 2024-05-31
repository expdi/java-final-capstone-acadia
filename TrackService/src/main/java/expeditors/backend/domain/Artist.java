package expeditors.backend.domain;

public class Artist {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist( int id, String name){
        this.id = id;
        this.name = name;
    }
    public Artist(){

    }
    public Artist(String name){
        this.name = name;
    }

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
        return String.format("Artist{id=%d, name='%s'}", this.getId(), this.getName());
    }


}
