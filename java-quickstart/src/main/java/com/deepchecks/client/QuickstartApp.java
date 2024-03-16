package com.deepchecks.client;

import com.deepchecks.config.PropsLoader;
import com.deepchecks.csv.CsvReader;
import com.deepchecks.http.FIleDownloader;
import com.deepchecks.sdk.DeepchecksClient;
import com.deepchecks.sdk.types.AnnotationType;
import com.deepchecks.sdk.types.EnvType;
import com.deepchecks.sdk.types.LogInteractionType;
import com.deepchecks.sdk.types.Step;
import com.deepchecks.sdk.types.StepType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class QuickstartApp {

    PropsLoader propsLoader;
    DeepchecksClient deepchecksClient;
    FIleDownloader fIleDownloader;
    CsvReader csvReader;

    public QuickstartApp() {
        log.info("Loading properties");
        this.propsLoader = new PropsLoader();
        this.fIleDownloader = new FIleDownloader();
        this.csvReader = new CsvReader();
        log.info("Starting DeepchecksClient");
        this.deepchecksClient = DeepchecksClient.builder()
                .appName(propsLoader.read("appName"))
                .versionName(propsLoader.read("versionName"))
                .envType(EnvType.valueOf(propsLoader.read("envType")))
                .host(propsLoader.read("host"))
                .token(propsLoader.read("token"))
                .build();
    }

    public void start() {
        String filePath = propsLoader.read("folder") + File.separator + propsLoader.read("fileName");

        fIleDownloader.download(propsLoader.read("url"), filePath);
        List<Map<String, String>> interactions = csvReader.readValues(Path.of(filePath));
        log.info("File has been parsed, size={}", interactions.size());

        String version = "0.1.1";
        log.info("Creating version {}", version);
        deepchecksClient.setVersionName(version);
        List<LogInteractionType> batch = new ArrayList<>();
        for (int i = 0; i < interactions.size(); i++) {
            Map<String, String> interaction = interactions.get(i);
            batch.add(LogInteractionType.builder()
                    .input(interaction.get("input"))
                    .output(interaction.get("output"))
                    .informationRetrieval(interaction.get("information_retrieval"))
                    .userInteractionId(String.valueOf(i))
                    .annotation(AnnotationType.of(interaction.get("annotation")))
                    .build());
        }
        String result = deepchecksClient.logBatchInteractions(batch);
        log.info("Created version {} in Deepchecks server, result={}", version, result);

        log.info("Add another version equivalent to the first, this time logging the individual steps.");
        version="0.1.2";
        log.info("Creating version {}", version);
        deepchecksClient.setVersionName(version);
        logEvalInteractions(interactions);
        log.info("Created version {} in Deepchecks server, result={}", version, result);
    }

    private void logEvalInteractions(List<Map<String, String>> interactions) {
        List<LogInteractionType> interactionsToLog = new ArrayList<>();
        for (int i = 0; i < interactions.size(); i++) {
            List<Step> steps = new ArrayList<>();
            Map<String, String> interaction = interactions.get(i);
            log.info("Append the data retriever (information retrieval) step for interaction {}", i);
            steps.add(Step.builder()
                    .name("Data Retriever")
                    .type(StepType.INFORMATION_RETRIEVAL)
                    .startedAt(LocalDateTime.now())
                    .input(interaction.get("input"))
                    .output(interaction.get("information_retrieval"))
                    .finishedAt(LocalDateTime.now())
                    .attributes(Map.of("model", "gpt-3.5-turbo"))
                    .build()
            );

            log.info("Append the full prompt and response step for interaction {}", i);
            steps.add(Step.builder()
                    .name("LLM")
                    .type(StepType.LLM)
                    .startedAt(LocalDateTime.now())
                    .input(interaction.get("full_prompt"))
                    .output("output")
                    .finishedAt(LocalDateTime.now())
                    .attributes(Map.of("model", "gpt-3.5-turbo"))
                    .build()
            );

            interactionsToLog.add(LogInteractionType.builder()
                    .input(steps.get(0).getInput())
                    .output(interaction.get("output"))
                    .annotation(AnnotationType.of(interaction.get("annotation")))
                    .userInteractionId(String.valueOf(i))
                    .steps(steps)
                    .build()
            );
        }
        deepchecksClient.logBatchInteractions(interactionsToLog);
    }
}
