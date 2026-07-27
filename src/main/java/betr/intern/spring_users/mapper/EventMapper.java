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
    final OffsetDateTime timestamp =
        dto.getTimestamp() != null ? dto.getTimestamp() : OffsetDateTime.now();
    return NotificationEvent.newBuilder()
        .setRecipient(dto.getRecipient())
        .setMessageBody(dto.getMessageBody())
        .setChannelType(dto.getChannelType())
        .setSender(dto.getSender())
        .setSubject(dto.getSubject())
        .setTimestamp(timestamp.toString())
        .build();
  }
}
