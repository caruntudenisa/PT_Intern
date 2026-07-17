package betr.intern.spring_users.event;

import java.time.OffsetDateTime;

public class PaymentEvent implements DomainEvent {

  private final String transactionId;
  private final String userId;
  private final Double amount;
  private final String paymentMethod;
  private final String currency;
  private final OffsetDateTime timestamp;

  private PaymentEvent(final Builder builder) {
    this.transactionId = builder.transactionId;
    this.userId = builder.userId;
    this.amount = builder.amount;
    this.paymentMethod = builder.paymentMethod;
    this.currency = builder.currency;
    this.timestamp = builder.timestamp;
  }

  @Override
  public EventType getEventType() {
    return EventType.PAYMENT;
  }

  @Override
  public OffsetDateTime getTimestamp() {
    return this.timestamp;
  }

  public String getTransactionId() {
    return this.transactionId;
  }

  public String getUserId() {
    return this.userId;
  }

  public Double getAmount() {
    return this.amount;
  }

  public String getPaymentMethod() {
    return this.paymentMethod;
  }

  public String getCurrency() {
    return this.currency;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String transactionId;
    private String userId;
    private Double amount;
    private String paymentMethod;
    private String currency;
    private OffsetDateTime timestamp;

    public Builder transactionId(final String transactionId) {
      this.transactionId = transactionId;
      return this;
    }

    public Builder userId(final String userId) {
      this.userId = userId;
      return this;
    }

    public Builder amount(final Double amount) {
      this.amount = amount;
      return this;
    }

    public Builder paymentMethod(final String paymentMethod) {
      this.paymentMethod = paymentMethod;
      return this;
    }

    public Builder currency(final String currency) {
      this.currency = currency;
      return this;
    }

    public Builder timestamp(final OffsetDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public PaymentEvent build() {
      if (this.timestamp == null) {
        this.timestamp = OffsetDateTime.now();
      }
      return new PaymentEvent(this);
    }
  }
}
