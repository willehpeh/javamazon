package tech.reactiv.ecommerce.common;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.junit.jupiter.api.Test;

public class OtelSetupTest {
    @Test
    void shouldSendSpanToConsole() {
        try (SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                .build()) {
            OpenTelemetry otel = OpenTelemetrySdk.builder()
                    .setTracerProvider(tracerProvider)
                    .build();
            Tracer tracer = otel.getTracer("test");
            Span span = tracer.spanBuilder("test").startSpan();
            span.setAttribute("key", "value");
            span.setAttribute("key2", "value2");
            span.end();
        }
    }
}
