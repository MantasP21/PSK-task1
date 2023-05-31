package lt.vu.jpa.service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class DoublingFavouriteNumberGenerator implements FavouriteNumberGenerator {
    @Inject
    @Delegate
    private FavouriteNumberGenerator delegate;

    @Override
    public Integer generateNumber() {
        return delegate.generateNumber() * 2;
    }
}
