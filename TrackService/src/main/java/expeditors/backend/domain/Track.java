package expeditors.backend.domain;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class Track {
    private int id;
    private String title;
    private String album;
    private LocalDate issueDate;
    private Duration duration;
    private MediaType mediaType;
    private List<Artist> artists;

    private String price;
    public Track(){

    }
    public Track( String title, String album, List<Artist> artists, LocalDate issueDate, Duration duration, MediaType mediaType){
        this.title = title;
        this.album = album;
        this.artists = artists;
        this.issueDate = issueDate;
        this.duration = duration;
        this.mediaType = mediaType;
    }

    public Track(int id, String title, String album, List<Artist> artists, LocalDate issueDate, Duration duration, MediaType mediaType){
        this.id = id;
        this.title = title;
        this.album = album;
        this.artists = artists;
        this.issueDate = issueDate;
        this.duration = duration;
        this.mediaType = mediaType;
    }
    public Track(String title){
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtist(List<Artist> artists) {
        this.artists = artists;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Track{id=%d, title='%s', album='%s' artists=[", this.getId(), this.getTitle(), this.getAlbum()));
        if (this.artists != null) {
            for (int i = 0; i < this.artists.size(); i++) {
                sb.append(String.format("Artist{id=%d, name='%s'}", this.artists.get(i).getId(), this.artists.get(i).getName()));

                if (i < artists.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append("], ");
        sb.append(String.format("price='%s'", this.getPrice()));
        return sb.toString();
    }

}
