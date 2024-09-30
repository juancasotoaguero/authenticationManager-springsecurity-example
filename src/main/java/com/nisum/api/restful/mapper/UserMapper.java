package com.nisum.api.restful.mapper;

import com.nisum.api.restful.domain.UserResponse;
import com.nisum.api.restful.entity.User;

public class UserMapper {

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .token(user.getToken())
                .id(user.getId())
                .created(user.getCreatedDate())
                .modified(user.getModifiedDate())
                .last_login(user.getLastLoginDate())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .build();
    }
}
