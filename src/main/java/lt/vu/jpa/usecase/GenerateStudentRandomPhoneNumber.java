package lt.vu.jpa.usecase;

import lt.vu.common.interceptor.LoggedInvocation;
import lt.vu.jpa.service.PhoneNumberService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GenerateStudentRandomPhoneNumber implements Serializable {
    @Inject
    private PhoneNumberService phoneNumberService;
    private CompletableFuture<String> phoneNumberGenerationTask = null;

    @LoggedInvocation
    public String generateNewPhoneNumber() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String studentId = requestParameters.get("studentId");

        phoneNumberGenerationTask = CompletableFuture.supplyAsync(() -> phoneNumberService.generatePhoneNumber());
        return  "/studentDetails.xhtml?faces-redirect=true&studentId=" + studentId;
    }
    public String getPhoneNumberGenerationStatus() throws ExecutionException, InterruptedException {
        if (phoneNumberGenerationTask == null) {
            return null;
        } else if (isPhoneNumberGenerating()) {
            return "Phone number generation in progress";
        }
        return "Suggested phone number: " + phoneNumberGenerationTask.get();
    }

    public boolean isPhoneNumberGenerating() {
        return phoneNumberGenerationTask != null && !phoneNumberGenerationTask.isDone();
    }
}
