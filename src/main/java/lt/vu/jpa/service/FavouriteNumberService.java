package lt.vu.jpa.service;

import lt.vu.common.interceptor.LoggedInvocation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class FavouriteNumberService {
    @Inject
    private FavouriteNumberGenerator numberGenerator;
    private CompletableFuture<Integer> favouriteNumberGenerationTask = CompletableFuture.completedFuture(null);

    @LoggedInvocation
    public void initiateSuggestionTask() {
        if (favouriteNumberGenerationTask.isDone()) {
            favouriteNumberGenerationTask = CompletableFuture.supplyAsync(numberGenerator::generateNumber);
        }
    }

    @LoggedInvocation
    public Integer getFavouriteNumber() {
        return favouriteNumberGenerationTask.getNow(null);
    }
}
