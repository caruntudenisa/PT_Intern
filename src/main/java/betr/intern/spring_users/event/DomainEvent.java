package betr.intern.spring_users.event;

import java.time.OffsetDateTime;

public interface DomainEvent {
  EventType getEventType();

  OffsetDateTime getTimestamp();
}
