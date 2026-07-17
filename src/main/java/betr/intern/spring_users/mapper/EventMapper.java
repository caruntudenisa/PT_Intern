package betr.intern.spring_users.mapper;

import betr.intern.spring_users.event.NotificationEvent;
import betr.intern.spring_users.event.PaymentEvent;
import betr.intern.spring_users.model.dto.NotificationEventDto;
import betr.intern.spring_users.model.dto.PaymentEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface
EventMapper {

  @Mapping(
      target = "timestamp",
      expression =
          "java(dto.getTimestamp() != null ? dto.getTimestamp().toLocalDateTime() : java.time.LocalDateTime.now())")
  PaymentEvent toEntity(PaymentEventDto dto);

  @Mapping(
      target = "timestamp",
      expression =
          "java(dto.getTimestamp() != null ? dto.getTimestamp().toLocalDateTime() : java.time.LocalDateTime.now())")
  NotificationEvent toEntity(NotificationEventDto dto);
}
