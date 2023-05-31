package lt.vu.jpa.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

@Specializes
@ApplicationScoped
public class DelayedRandomFavouriteNumberGenerator extends RandomFavouriteNumberGenerator {
    @Override
    public Integer generateNumber() {
        try {
            Thread.sleep(3000); // Simulate intensive work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return super.generateNumber();
    }
}
