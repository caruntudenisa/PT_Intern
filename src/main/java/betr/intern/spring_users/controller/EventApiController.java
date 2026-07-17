package betr.intern.spring_users.controller;

import betr.intern.spring_users.api.EventsApi;
import betr.intern.spring_users.event.EventType;
import betr.intern.spring_users.event.NotificationEvent;
import betr.intern.spring_users.event.PaymentEvent;
import betr.intern.spring_users.event.factory.EventProcessorFactory;
import betr.intern.spring_users.event.processor.EventProcessor;
import betr.intern.spring_users.mapper.EventMapper;
import betr.intern.spring_users.model.dto.NotificationEventDto;
import betr.intern.spring_users.model.dto.PaymentEventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventApiController implements EventsApi {

  private final EventProcessorFactory processorFactory;
  private final EventMapper eventMapper;

  public EventApiController(
      final EventProcessorFactory processorFactory, final EventMapper eventMapper) {
    this.processorFactory = processorFactory;
    this.eventMapper = eventMapper;
  }

  @Override
  public ResponseEntity<Void> processPaymentEvent(final PaymentEventDto paymentEventDto) {
    final PaymentEvent paymentEvent = this.eventMapper.toEntity(paymentEventDto);

    final EventProcessor processor = this.processorFactory.getProcessor(EventType.PAYMENT);
    processor.process(paymentEvent);

    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> processNotificationEvent(
      final NotificationEventDto notificationEventDto) {
    final NotificationEvent notificationEvent = this.eventMapper.toEntity(notificationEventDto);

    final EventProcessor processor = this.processorFactory.getProcessor(EventType.NOTIFICATION);
    processor.process(notificationEvent);

    return ResponseEntity.ok().build();
  }
}
