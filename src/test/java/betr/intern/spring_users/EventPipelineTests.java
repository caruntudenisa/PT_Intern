package betr.intern.spring_users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import betr.intern.spring_users.event.NotificationEvent;
import betr.intern.spring_users.event.PaymentEvent;
import betr.intern.spring_users.event.factory.NotificationProcessorFactory;
import betr.intern.spring_users.event.processor.EmailNotificationProcessor;
import betr.intern.spring_users.event.processor.NotificationProcessor;
import betr.intern.spring_users.event.processor.SmsNotificationProcessor;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(properties = {
    "spring.kafka.producer.properties.schema.registry.url=mock://test-registry",
    "spring.kafka.consumer.properties.schema.registry.url=mock://test-registry"
})
@EmbeddedKafka(partitions = 1, bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class EventPipelineTests {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Autowired
  private NotificationProcessorFactory processorFactory;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void testPaymentEventBuilder() {
    final OffsetDateTime now = OffsetDateTime.now();
    final PaymentEvent event =
        PaymentEvent.builder()
            .transactionId("tx-123")
            .userId("user-456")
            .amount(100.50)
            .paymentMethod("CREDIT_CARD")
            .currency("USD")
            .timestamp(now)
            .build();

    assertEquals("tx-123", event.getTransactionId());
    assertEquals("user-456", event.getUserId());
    assertEquals(100.50, event.getAmount());
    assertEquals("CREDIT_CARD", event.getPaymentMethod());
    assertEquals("USD", event.getCurrency());
    assertEquals(now, event.getTimestamp());
  }

  @Test
  void testNotificationEventBuilder() {
    final NotificationEvent event =
        NotificationEvent.newBuilder()
            .setRecipient("john@example.com")
            .setMessageBody("Hello world")
            .setChannelType("EMAIL")
            .setSender("system@example.com")
            .setSubject("Welcome")
            .setTimestamp("2026-07-27T10:00:00Z")
            .build();

    assertEquals("john@example.com", event.getRecipient());
    assertEquals("Hello world", event.getMessageBody());
    assertEquals("EMAIL", event.getChannelType());
    assertEquals("system@example.com", event.getSender());
    assertEquals("Welcome", event.getSubject());
    assertEquals("2026-07-27T10:00:00Z", event.getTimestamp());
  }

  @Test
  void testFactoryAndProcessors() {
    final NotificationProcessor emailProcessor = this.processorFactory.getProcessor("EMAIL");
    assertTrue(emailProcessor instanceof EmailNotificationProcessor);

    final NotificationProcessor smsProcessor = this.processorFactory.getProcessor("SMS");
    assertTrue(smsProcessor instanceof SmsNotificationProcessor);

    assertThrows(IllegalArgumentException.class, () -> this.processorFactory.getProcessor(null));
    assertThrows(IllegalArgumentException.class, () -> this.processorFactory.getProcessor("UNKNOWN"));
  }

  @Test
  void testPostPaymentEndpoint() throws Exception {
    final String requestJson =
        "{"
            + "\"transactionId\":\"tx-999\","
            + "\"userId\":\"user-888\","
            + "\"amount\":250.00,"
            + "\"paymentMethod\":\"PAYPAL\","
            + "\"currency\":\"EUR\","
            + "\"timestamp\":\"2026-07-17T12:00:00Z\""
            + "}";

    this.mockMvc
        .perform(
            post("/events/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  void testPostNotificationEndpoint() throws Exception {
    final String requestJson =
        "{"
            + "\"recipient\":\"jane@example.com\","
            + "\"messageBody\":\"Testing 123\","
            + "\"channelType\":\"SMS\","
            + "\"sender\":\"+123456789\","
            + "\"subject\":\"Info\","
            + "\"timestamp\":\"2026-07-17T12:00:00Z\""
            + "}";

    this.mockMvc
        .perform(
            post("/events/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }
}
