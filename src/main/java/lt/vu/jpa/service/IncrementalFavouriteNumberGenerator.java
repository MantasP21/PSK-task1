package lt.vu.jpa.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
public class IncrementalFavouriteNumberGenerator implements FavouriteNumberGenerator {
    private Integer count = 0;

    @Override
    public Integer generateNumber() {
        return count++;
    }
}
