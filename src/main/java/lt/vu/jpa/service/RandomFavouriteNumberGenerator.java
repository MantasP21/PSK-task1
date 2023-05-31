package lt.vu.jpa.service;

import org.apache.commons.lang3.RandomUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RandomFavouriteNumberGenerator implements FavouriteNumberGenerator {
    @Override
    public Integer generateNumber() {
        return RandomUtils.nextInt(1_000_000, 10_000_000);
    }
}
