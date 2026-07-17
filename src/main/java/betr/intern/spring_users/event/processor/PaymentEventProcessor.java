package betr.intern.spring_users.event.processor;

import betr.intern.spring_users.event.DomainEvent;
import betr.intern.spring_users.event.EventType;
import betr.intern.spring_users.event.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventProcessor implements EventProcessor {

  private static final Logger logger = LoggerFactory.getLogger(PaymentEventProcessor.class);

  @Override
  public void process(final DomainEvent event) {
    if (event instanceof PaymentEvent) {
      final PaymentEvent paymentEvent = (PaymentEvent) event;
      logger.info(
          "Processing PaymentEvent: transactionId={}, userId={}, amount={}, method={}, currency={}, timestamp={}",
          paymentEvent.getTransactionId(),
          paymentEvent.getUserId(),
          paymentEvent.getAmount(),
          paymentEvent.getPaymentMethod(),
          paymentEvent.getCurrency(),
          paymentEvent.getTimestamp());
    }
  }

  @Override
  public boolean supports(final EventType eventType) {
    return EventType.PAYMENT == eventType;
  }
}
