package yuriy.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import yuriy.spring.dto.user.UserRegistrationDto;
import yuriy.spring.dto.user.UserViewDto;
import yuriy.spring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserViewDto toDto(User user);

    @Mapping(target = "password", source = "password")
    User toEntity(UserRegistrationDto userDto);
}
