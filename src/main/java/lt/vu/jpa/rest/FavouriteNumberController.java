package lt.vu.jpa.rest;

import lt.vu.jpa.service.FavouriteNumberService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/favouriteNumber")
public class FavouriteNumberController {
    @Inject
    private FavouriteNumberService numberService;

    @POST
    public Response initiate() {
        numberService.initiateSuggestionTask();
        return Response.noContent().build();
    }

    @GET
    public Response getNumber() {
        Integer number = numberService.getFavouriteNumber();
        return Response.ok(number).build();
    }
}
