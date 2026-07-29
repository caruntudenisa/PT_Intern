package betr.intern.spring_users.service;

import betr.intern.spring_users.event.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

  public void processPayment(final PaymentEvent event) {
    logger.info(
        "Processing PaymentEvent: transactionId={}, userId={}, amount={}, method={}, currency={}, timestamp={}",
        event.getTransactionId(),
        event.getUserId(),
        event.getAmount(),
        event.getPaymentMethod(),
        event.getCurrency(),
        event.getTimestamp());
  }
}
