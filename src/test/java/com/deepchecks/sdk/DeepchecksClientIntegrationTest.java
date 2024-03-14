package com.deepchecks.sdk;

import com.deepchecks.sdk.types.AnnotationType;
import com.deepchecks.sdk.types.LogInteractionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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