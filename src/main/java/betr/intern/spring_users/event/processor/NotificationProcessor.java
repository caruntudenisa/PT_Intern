package betr.intern.spring_users.event.processor;

import betr.intern.spring_users.event.NotificationEvent;

public interface NotificationProcessor {
  void process(NotificationEvent event);

  boolean supports(String channelType);
}
