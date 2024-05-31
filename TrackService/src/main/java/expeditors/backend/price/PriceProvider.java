package expeditors.backend.price;

import expeditors.backend.domain.Track;

public interface PriceProvider {
    void addPriceToTrack(Track track);
}
