package io.github.zwieback.relef.configs;

import org.springframework.context.annotation.Import;

@Import({
        AnalyzerConfig.class,
        DatabaseConfig.class,
        DownloaderConfig.class,
        ExporterConfig.class,
        ImporterConfig.class,
        JacksonConfig.class,
        ParserConfig.class,
        ParserStrategyConfig.class,
        PropertyConfig.class,
        ServiceConfig.class,
        WebConfig.class
})
public class ApplicationConfig {
}
