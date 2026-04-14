package tech.reactiv.ecommerce.catalog;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@TestConfiguration
class TracingConfiguration {

    @Bean
    OpenTelemetry openTelemetry() {
        GlobalOpenTelemetry.resetForTest();
        var tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(new FileSpanExporter("build/traces.log")))
                .build();
        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .buildAndRegisterGlobal();
    }

    static class FileSpanExporter implements SpanExporter {
        private final String filePath;

        FileSpanExporter(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public CompletableResultCode export(Collection<SpanData> spans) {
            try (var writer = new PrintWriter(new FileWriter(filePath, true))) {
                for (var span : spans) {
                    writer.printf("span: %s | trace: %s | parent: %s | name: %s | status: %s%n",
                            span.getSpanId(),
                            span.getTraceId(),
                            span.getParentSpanId(),
                            span.getName(),
                            span.getStatus());
                    span.getAttributes().forEach((key, value) ->
                            writer.printf("  %s = %s%n", key.getKey(), value));
                    if (!span.getEvents().isEmpty()) {
                        span.getEvents().forEach(event ->
                                writer.printf("  event: %s%n", event.getName()));
                    }
                    writer.println();
                }
            } catch (IOException e) {
                return CompletableResultCode.ofFailure();
            }
            return CompletableResultCode.ofSuccess();
        }

        @Override
        public CompletableResultCode flush() {
            return CompletableResultCode.ofSuccess();
        }

        @Override
        public CompletableResultCode shutdown() {
            return CompletableResultCode.ofSuccess();
        }
    }
}
