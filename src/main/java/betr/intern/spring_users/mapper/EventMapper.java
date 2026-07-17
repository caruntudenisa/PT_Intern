package betr.intern.spring_users.mapper;

import betr.intern.spring_users.event.NotificationEvent;
import betr.intern.spring_users.event.PaymentEvent;
import betr.intern.spring_users.model.dto.NotificationEventDto;
import betr.intern.spring_users.model.dto.PaymentEventDto;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

  public PaymentEvent toEntity(final PaymentEventDto dto) {
    if (dto == null) {
      return null;
    }
    return PaymentEvent.builder()
        .transactionId(dto.getTransactionId())
        .userId(dto.getUserId())
        .amount(dto.getAmount() != null ? dto.getAmount().doubleValue() : null)
        .paymentMethod(dto.getPaymentMethod())
        .currency(dto.getCurrency())
        .timestamp(dto.getTimestamp() != null ? dto.getTimestamp() : OffsetDateTime.now())
        .build();
  }

  public NotificationEvent toEntity(final NotificationEventDto dto) {
    if (dto == null) {
      return null;
    }
    return NotificationEvent.builder()
        .recipient(dto.getRecipient())
        .messageBody(dto.getMessageBody())
        .channelType(dto.getChannelType())
        .sender(dto.getSender())
        .subject(dto.getSubject())
        .timestamp(dto.getTimestamp() != null ? dto.getTimestamp() : OffsetDateTime.now())
        .build();
  }
}
