package com.project.libraryApi.mapper;

import com.project.libraryApi.dto.UserDTO;
import com.project.libraryApi.model.User;

public class UserMapper {

    public static User toUser(User originalUser, UserDTO newUser) {
        originalUser.setName(newUser.getName());
        return originalUser;
    }

    public static User toUser(UserDTO newUser) {
        return new User(newUser.getName());
    }
}
