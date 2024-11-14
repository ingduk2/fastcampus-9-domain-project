package org.fastcampus.acceptance.utils;

import org.fastcampus.acceptance.DataBaseCleanUp;
import org.fastcampus.acceptance.DataLoader;
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
    private DataLoader dataLoader;

    @BeforeEach
    void init() {
        dataBaseCleanUp.execute();
        dataLoader.loadData();
    }
}
