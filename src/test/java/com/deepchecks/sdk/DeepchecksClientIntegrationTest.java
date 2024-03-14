package com.deepchecks.sdk;

import com.deepchecks.sdk.types.AnnotationType;
import com.deepchecks.sdk.types.EnvType;
import com.deepchecks.sdk.types.LogInteractionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeepchecksClientIntegrationTest {

    private static final String TEST_TOKEN = "Z2cud290bWFpbEBnbWFpbC5jb20=.b3JnX2FuZHJleV9qYXZhX3Nka19mOGY3Mzg1NDA2OTMzM2Y2.7AgksN5CnzUaehrIocvsdQ";
    private static final String TEST_APP_NAME = "python-to-java";
    private static final String TEST_VERSION_NAME = "1";

    DeepchecksClient client;

    @BeforeEach
    void setUp() {
        client = new DeepchecksClient(TEST_TOKEN, TEST_APP_NAME, TEST_VERSION_NAME);
    }

    @Test
    void initClient() {
        client = DeepchecksClient.builder()
                .host("https://app.llm.deepchecks.com")
                .token(TEST_TOKEN)
                .appName("python-to-java")
                .versionName("1")
                .envType(EnvType.EVAL)
                .build();
        String interactions = client.getData();
        assertNotNull(interactions);
    }

    @Test
    void changeVersion() {
        client = DeepchecksClient.builder()
                .host("https://app.llm.deepchecks.com")
                .token(TEST_TOKEN)
                .appName("python-to-java")
                .versionName("1")
                .envType(EnvType.EVAL)
                .build();
        String interactions = client.getData();
        assertNotNull(interactions);

        client = client.toBuilder()
                .appName("python-to-java")
                .versionName("bad_version")
                .envType(EnvType.PROD)
                .build();
        interactions = client.getData();
        assertTrue(interactions.contains("application_version_id value is not a valid integer"));
    }

    @Test
    void logBatchInteractions() {
        String result = client.logBatchInteractions(asList(
                LogInteractionType.builder()
                        .input("my user input1")
                        .output("my model output1")
                        .annotation(AnnotationType.GOOD)
                        .build(),
                LogInteractionType.builder()
                        .input("my user input2")
                        .output("my model output2")
                        .annotation(AnnotationType.BAD)
                        .build()
        ));
        assertNotNull(result);
    }
}