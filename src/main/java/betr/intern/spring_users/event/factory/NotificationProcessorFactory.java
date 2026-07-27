package betr.intern.spring_users.event.factory;

import betr.intern.spring_users.event.processor.NotificationProcessor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NotificationProcessorFactory {

  private final List<NotificationProcessor> processors;

  public NotificationProcessorFactory(final List<NotificationProcessor> processors) {
    this.processors = processors;
  }

  public NotificationProcessor getProcessor(final String channelType) {
    if (channelType == null) {
      throw new IllegalArgumentException("Channel type cannot be null");
    }
    return this.processors.stream()
        .filter(p -> p.supports(channelType))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "No notification processor found for channel type: " + channelType));
  }
}
