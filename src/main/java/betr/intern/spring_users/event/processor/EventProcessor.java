package betr.intern.spring_users.event.processor;

import betr.intern.spring_users.event.DomainEvent;
import betr.intern.spring_users.event.EventType;

public interface EventProcessor {
  void process(DomainEvent event);

  boolean supports(EventType eventType);
}
