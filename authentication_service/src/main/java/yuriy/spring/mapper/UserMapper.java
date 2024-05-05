package yuriy.spring.mapper;

import org.mapstruct.Mapper;
import yuriy.spring.dto.user.UserRegistrationDto;
import yuriy.spring.dto.user.UserViewDto;
import yuriy.spring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserViewDto toDto(User user);

    User toEntity(UserRegistrationDto userDto);
}
