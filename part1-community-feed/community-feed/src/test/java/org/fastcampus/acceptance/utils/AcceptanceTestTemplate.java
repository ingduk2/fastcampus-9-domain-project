package org.fastcampus.acceptance.utils;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AcceptanceTestTemplate {

    @Autowired
    private DataBaseCleanUp dataBaseCleanUp;

    @Autowired
    private AcceptanceDataLoader acceptanceDataLoader;

    @BeforeEach
    void init() {
        dataBaseCleanUp.execute();
    }

    protected void cleanUp() {
        dataBaseCleanUp.execute();
    }

    protected String getEmailToken(String email) {
        return acceptanceDataLoader.getEmailToken(email);
    }

    protected boolean isEmailVerified(String email) {
        return acceptanceDataLoader.isEmailVerified(email);
    }

    protected Long getUserId(String email) {
        return acceptanceDataLoader.getUserId(email);
    }

    protected void createUser(String email) {
        acceptanceDataLoader.createUser(email);
    }
}
