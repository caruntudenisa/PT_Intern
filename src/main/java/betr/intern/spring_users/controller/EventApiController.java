package betr.intern.spring_users.controller;

import betr.intern.spring_users.api.EventsApi;
import betr.intern.spring_users.event.NotificationEvent;
import betr.intern.spring_users.event.PaymentEvent;
import betr.intern.spring_users.kafka.NotificationProducer;
import betr.intern.spring_users.mapper.EventMapper;
import betr.intern.spring_users.model.dto.NotificationEventDto;
import betr.intern.spring_users.model.dto.PaymentEventDto;
import betr.intern.spring_users.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventApiController implements EventsApi {

  private final NotificationProducer notificationProducer;
  private final PaymentService paymentService;
  private final EventMapper eventMapper;

  public EventApiController(
      final NotificationProducer notificationProducer,
      final PaymentService paymentService,
      final EventMapper eventMapper) {
    this.notificationProducer = notificationProducer;
    this.paymentService = paymentService;
    this.eventMapper = eventMapper;
  }

  @Override
  public ResponseEntity<Void> processPaymentEvent(final PaymentEventDto paymentEventDto) {
    final PaymentEvent paymentEvent = this.eventMapper.toEntity(paymentEventDto);
    this.paymentService.processPayment(paymentEvent);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> processNotificationEvent(
      final NotificationEventDto notificationEventDto) {
    final NotificationEvent notificationEvent = this.eventMapper.toEntity(notificationEventDto);
    this.notificationProducer.sendNotification(notificationEvent);
    return ResponseEntity.ok().build();
  }
}
