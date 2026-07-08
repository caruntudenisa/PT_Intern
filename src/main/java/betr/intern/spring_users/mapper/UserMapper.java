package betr.intern.spring_users.mapper;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);

  User toEntity(UserDto dto);

  void updateEntityFromDto(UserDto dto, @MappingTarget User user);
}
