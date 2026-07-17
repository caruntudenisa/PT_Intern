package betr.intern.spring_users.event;

import java.time.OffsetDateTime;

public class NotificationEvent implements DomainEvent {

  private final String recipient;
  private final String messageBody;
  private final String channelType;
  private final String sender;
  private final String subject;
  private final OffsetDateTime timestamp;

  private NotificationEvent(final Builder builder) {
    this.recipient = builder.recipient;
    this.messageBody = builder.messageBody;
    this.channelType = builder.channelType;
    this.sender = builder.sender;
    this.subject = builder.subject;
    this.timestamp = builder.timestamp;
  }

  @Override
  public EventType getEventType() {
    return EventType.NOTIFICATION;
  }

  @Override
  public OffsetDateTime getTimestamp() {
    return this.timestamp;
  }

  public String getRecipient() {
    return this.recipient;
  }

  public String getMessageBody() {
    return this.messageBody;
  }

  public String getChannelType() {
    return this.channelType;
  }

  public String getSender() {
    return this.sender;
  }

  public String getSubject() {
    return this.subject;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String recipient;
    private String messageBody;
    private String channelType;
    private String sender;
    private String subject;
    private OffsetDateTime timestamp;

    public Builder recipient(final String recipient) {
      this.recipient = recipient;
      return this;
    }

    public Builder messageBody(final String messageBody) {
      this.messageBody = messageBody;
      return this;
    }

    public Builder channelType(final String channelType) {
      this.channelType = channelType;
      return this;
    }

    public Builder sender(final String sender) {
      this.sender = sender;
      return this;
    }

    public Builder subject(final String subject) {
      this.subject = subject;
      return this;
    }

    public Builder timestamp(final OffsetDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public NotificationEvent build() {
      if (this.timestamp == null) {
        this.timestamp = OffsetDateTime.now();
      }
      return new NotificationEvent(this);
    }
  }
}
