package lt.vu.jpa.service;

import org.apache.commons.lang3.RandomUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PhoneNumberService {

    public String generatePhoneNumber() {
        try {
            Thread.sleep(3000); // Simulate intensive work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "86" + RandomUtils.nextInt(1_000_000, 10_000_000);
    }
}
