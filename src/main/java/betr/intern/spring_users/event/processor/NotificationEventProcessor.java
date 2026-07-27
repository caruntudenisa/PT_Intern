package betr.intern.spring_users.event.processor;

import betr.intern.spring_users.event.DomainEvent;
import betr.intern.spring_users.event.EventType;
import betr.intern.spring_users.event.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventProcessor implements EventProcessor {

  private static final Logger logger = LoggerFactory.getLogger(NotificationEventProcessor.class);

  @Override
  public void process(final DomainEvent event) {
    if (event instanceof NotificationEvent) {
      final NotificationEvent notificationEvent = (NotificationEvent) event;
      logger.info(
          "Processing NotificationEvent: recipient={}, sender={}, subject={}, body={}, channel={}, timestamp={}",
          notificationEvent.getRecipient(),
          notificationEvent.getSender(),
          notificationEvent.getSubject(),
          notificationEvent.getMessageBody(),
          notificationEvent.getChannelType(),
          notificationEvent.getTimestamp());
    }
  }

  @Override
  public boolean supports(final EventType eventType) {
    return EventType.NOTIFICATION == eventType;
  }
}
