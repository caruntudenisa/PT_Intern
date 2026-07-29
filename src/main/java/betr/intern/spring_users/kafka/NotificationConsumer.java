package betr.intern.spring_users.kafka;

import betr.intern.spring_users.event.NotificationEvent;
import betr.intern.spring_users.event.factory.NotificationProcessorFactory;
import betr.intern.spring_users.event.processor.NotificationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

  private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);
  private final NotificationProcessorFactory processorFactory;

  public NotificationConsumer(final NotificationProcessorFactory processorFactory) {
    this.processorFactory = processorFactory;
  }

  @KafkaListener(topics = "notifications", groupId = "notification-group")
  public void consume(final NotificationEvent event) {
    logger.info("Consumed NotificationEvent: {}", event);
    try {
      final NotificationProcessor processor =
          this.processorFactory.getProcessor(event.getChannelType());
      processor.process(event);
    } catch (final Exception e) {
      logger.error("Error processing consumed notification event: {}", e.getMessage(), e);
    }
  }
}
