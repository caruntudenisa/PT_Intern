package betr.intern.spring_users.event;

import java.time.LocalDateTime;

public interface DomainEvent {
  EventType getEventType();

  LocalDateTime getTimestamp();
}
