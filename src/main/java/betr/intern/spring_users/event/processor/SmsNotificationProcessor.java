package betr.intern.spring_users.event.processor;

import betr.intern.spring_users.event.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationProcessor implements NotificationProcessor {

  private static final Logger logger = LoggerFactory.getLogger(SmsNotificationProcessor.class);

  @Override
  public void process(final NotificationEvent event) {
    logger.info(
        "Processing SMS Notification: recipient={}, sender={}, subject={}, body={}, timestamp={}",
        event.getRecipient(),
        event.getSender(),
        event.getSubject(),
        event.getMessageBody(),
        event.getTimestamp());
  }

  @Override
  public boolean supports(final String channelType) {
    return "SMS".equalsIgnoreCase(channelType);
  }
}
