package betr.intern.spring_users.kafka;

import betr.intern.spring_users.event.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

  private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);
  private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

  public NotificationProducer(final KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendNotification(final NotificationEvent event) {
    logger.info("Publishing NotificationEvent: {}", event);
    this.kafkaTemplate.send("notifications", event.getRecipient(), event);
  }
}
