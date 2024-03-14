## Java SDK

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


Get all data for different application configuration:
```
client = client.toBuilder()
        .appName("python-to-java")
        .versionName("bad_version")
        .envType(EnvType.PROD)
        .build();
String interactions = client.getData();
```