package com.deepchecks.sdk;

import com.deepchecks.sdk.types.AnnotationType;
import com.deepchecks.sdk.types.EnvType;
import com.deepchecks.sdk.types.LogInteractionType;
import com.deepchecks.sdk.types.Step;
import com.deepchecks.sdk.types.StepType;
import com.deepchecks.sdk.types.request.DataRequest;
import com.deepchecks.sdk.types.request.UpdateInteractionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

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

        client.setVersionName("22");
        client.setEnvType(null);
        client.setAppName("test-app");
        interactions = client.getData();
        assertTrue(interactions.contains("'ApplicationVersion' with next set of arguments does not exist: id=22"));
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

    @Test
    void uploadNewVersionOfInteractions() {
        client.setVersionName("2");
        String result = client.logBatchInteractions(asList(
                LogInteractionType.builder()
                        .input("my user input1")
                        .output("my model output1")
                        .userInteractionId("id1")
                        .annotation(AnnotationType.GOOD)
                        .build(),
                LogInteractionType.builder()
                        .input("my user input2")
                        .output("my model output2")
                        .userInteractionId("id2")
                        .annotation(AnnotationType.BAD)
                        .build()
        ));
        assertNotNull(result);
        assertTrue(result.contains("id1"));
        assertTrue(result.contains("id2"));
    }

    @Test
    void updateInteraction() {
        client.setVersionName("2");
        String result = client.logBatchInteractions(asList(
                LogInteractionType.builder()
                        .userInteractionId("MyInteractionId1")
                        .input("my user input3")
                        .output("my model output3")
                        .annotation(AnnotationType.GOOD)
                        .build()
        ));
        assertNotNull(result);
        assertTrue(result.contains("MyInteractionId1"));

        result = client.updateInteraction("MyInteractionId1", "2",
                UpdateInteractionRequest.builder()
                        .annotation(AnnotationType.BAD)
                        .annotationReason("my reason")
                        .customProperties(Map.of(
                                "My Custom Property", "1.5",
                                "Next Property", "Property Value"
                        ))
                        .build()
        );
        assertNotNull(result);
        assertTrue(result.contains("MyInteractionId1"));
    }

    @Test
    void logInteraction() {
        client.setVersionName("2");
        String result = client.logInteraction(
                LogInteractionType.builder()
                        .userInteractionId("MyInteractionId5")
                        .input("my user input 5")
                        .output("my model output 5")
                        .annotation(AnnotationType.GOOD)
                        .annotationReason("test reason 5")
                        .startedAt(LocalDateTime.now().minusHours(1))
                        .finishedAt(LocalDateTime.now())
                        .steps(asList(
                                Step.builder()
                                        .name("Information Retrieval")
                                        .type(StepType.INFORMATION_RETRIEVAL)
                                        .startedAt(LocalDateTime.of(2024, 10, 31, 15, 1, 0))
                                        .attributes(Map.of(
                                                "embeddings", "ada-02"
                                        ))
                                        .input("my user input")
                                        .output("This is a relevant document for this input")
                                        .build(),
                                Step.builder()
                                        .name("Information Retrieval")
                                        .type(StepType.LLM)
                                        .startedAt(LocalDateTime.of(2024, 10, 31, 15, 2, 0))
                                        .attributes(Map.of("model", "gpt-3.5-turbo"))
                                        .input("Full prompt with my user input + the retrieved document")
                                        .output("my model output")
                                        .build()
                        ))
                        .build()
        );

        assertNotNull(result);
        assertTrue(result.contains("MyInteractionId5"));
    }

    @Test
    void logBatchInteractionsWithAdditionalData() {
        String result = client.logBatchInteractions(asList(
                LogInteractionType.builder()
                        .input("my user input 2")
                        .output("my model output 2")
                        .informationRetrieval("my information retrieval2")
                        .fullPrompt("system part: my user input2")
                        .annotation(AnnotationType.BAD)
                        .annotationReason("Output is not correctly grounded in information retrieval result")
                        .userInteractionId("user_interactions_id_2")
                        .startedAt(LocalDateTime.of(2024, 10, 31, 15, 1, 0))
                        .finishedAt(LocalDateTime.now())
                        .customProps(Map.of("My Custom Property", 2))
                        .build(),
                LogInteractionType.builder()
                        .input("my user input1")
                        .output("my model output1")
                        .informationRetrieval("my information retrieval1")
                        .fullPrompt("system part: my user input1")
                        .annotation(AnnotationType.GOOD)
                        .userInteractionId("user_interactions_id_1")
                        .startedAt(LocalDateTime.of(2024, 10, 31, 15, 1, 0))
                        .finishedAt(LocalDateTime.now())
                        .customProps(Map.of("My Custom Property", 1))
                        .build()
        ));
        assertNotNull(result);
        assertTrue(result.contains("user_interactions_id_2"));
        assertTrue(result.contains("user_interactions_id_1"));
    }

    @Test
    void getData() {
        client.setEnvType(EnvType.PROD);
        client.setVersionName("1");
        String result = client.logBatchInteractions(asList(
                LogInteractionType.builder()
                        .input("my user input6")
                        .output("my model output6")
                        .userInteractionId("transaction6")
                        .startedAt(LocalDateTime.now())
                        .annotation(AnnotationType.GOOD)
                        .build(),
                LogInteractionType.builder()
                        .input("my user input7")
                        .output("my model output7")
                        .userInteractionId("transaction7")
                        .startedAt(LocalDateTime.now().minusDays(3))
                        .annotation(AnnotationType.BAD)
                        .build()
        ));
        assertNotNull(result);

        result = client.getData(DataRequest.builder()
                .environment(EnvType.PROD)
                .applicationVersionId("1")
                .startTimeEpoch(LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC))
                .endTimeEpoch(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .build()
        );
        assertTrue(result.contains("transaction6"));
    }
}