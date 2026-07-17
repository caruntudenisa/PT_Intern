package betr.intern.spring_users.mapper;

import betr.intern.spring_users.event.NotificationEvent;
import betr.intern.spring_users.event.PaymentEvent;
import betr.intern.spring_users.model.dto.NotificationEventDto;
import betr.intern.spring_users.model.dto.PaymentEventDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

  PaymentEvent toEntity(PaymentEventDto dto);

  NotificationEvent toEntity(NotificationEventDto dto);
}
