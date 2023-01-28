package io.drdroid.api.data;

import io.drdroid.api.models.ClientConfig;

public class BaseTestDataSupplier {

    public static ClientConfig getMockClientConfig() {
        return new ClientConfig("test_org", "http://localhost:8000", 8080,
                "test_service");
    }

}
