## Java SDK

### Quickstart

See example in [Quickstart.md](java-quickstart/Quickstart.md)

### Example of SDK usage

Using constructor to init client:
```
DeepchecksClient client = new DeepchecksClient(TOKEN, APP_NAME, VERSION_NAME);
```

Using builder to init client:
```
client = DeepchecksClient.builder()
                .host("https://app.llm.deepchecks.com")
                .token(TEST_TOKEN)
                .appName("python-to-java")
                .versionName("1")
                .envType(EnvType.EVAL)
                .build();
```

Log batch interactions:
```
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
```

Get all data:
```
String interactions = client.getData();
```

Get all data for different application version:
```
client = client.toBuilder()
                .versionName("2")
                .build();
                
String interactions = client.getData();
```
or
```
client.setVersionName("2");

String interactions = client.getData();
```

Get all data with different application configuration:
```
client = client.toBuilder()
        .appName("python-to-java")
        .versionName("2")
        .envType(EnvType.PROD)
        .build();
        
String interactions = client.getData();
```
or
```
client.setVersionName("2");
client.setEnvType(EnvType.PROD);
client.setAppName("python-to-java");

String interactions = client.getData();
```

Request data with filters:

```
result = client.getData(DataRequest.builder()
        .environment(EnvType.EVAL)
        .applicationVersionId("1")
        .startTimeEpoch(LocalDateTime.now().minusDays(2).toEpochSecond(ZoneOffset.UTC))
        .endTimeEpoch(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        .build()
);
```

Upload new version of interactions:
```
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
```

Update interaction:

```
String result = client.updateInteraction("MyInteractionId1", "2",
        UpdateInteractionRequest.builder()
                .annotation(AnnotationType.BAD)
                .annotationReason("my reason")
                .customProperties(Map.of(
                        "My Custom Property", "1.5",
                        "Next Property", "Property Value"
                ))
                .build()
);
```

Log interaction with Steps:

```
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
```

Usage of Additional Data:

```
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
```