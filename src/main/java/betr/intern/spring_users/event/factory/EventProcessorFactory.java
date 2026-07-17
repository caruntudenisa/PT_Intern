package betr.intern.spring_users.event.factory;

import betr.intern.spring_users.event.EventType;
import betr.intern.spring_users.event.processor.EventProcessor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EventProcessorFactory {

  private final List<EventProcessor> processors;

  public EventProcessorFactory(final List<EventProcessor> processors) {
    this.processors = processors;
  }

  public EventProcessor getProcessor(final EventType eventType) {
    return this.processors.stream()
        .filter(p -> p.supports(eventType))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException("No processor found for event type: " + eventType));
  }
}
