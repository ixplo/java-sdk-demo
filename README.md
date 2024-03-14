### Java SDK

Example of SDK usage:
```
DeepchecksClient client = new DeepchecksClient(TOKEN, APP_NAME, VERSION_NAME)

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
