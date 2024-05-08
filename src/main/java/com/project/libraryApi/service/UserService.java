package com.project.libraryApi.service;

import com.project.libraryApi.dto.UserDTO;
import com.project.libraryApi.exception.UserNotFoundException;
import com.project.libraryApi.mapper.UserMapper;
import com.project.libraryApi.model.User;
import com.project.libraryApi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"users"})
public class UserService {
    Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Cacheable("usersList")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public User getUserById(Long id) {
        log.info("Finding user. id: ".concat(id.toString()));
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Caching(evict = @CacheEvict(cacheNames = "usersList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public User saveUser(UserDTO user) {
        log.info("Saving user. name: ".concat(user.getName()));
        User saveUser = userRepository.save(UserMapper.toUser(user));
        log.info("User saved success: ".concat(user.toString()));
        return saveUser;
    }

    @CacheEvict(key = "#id")
    @Caching(evict = {@CacheEvict(cacheNames = "usersList", allEntries = true), @CacheEvict(key = "#id")})
    public void deleteUser(Long id) {
        log.info("Deleting user. id: ".concat(id.toString()));
        userRepository.deleteById(id);
        log.info("User deleted success: id ".concat(id.toString()));
    }

    @Caching(evict = @CacheEvict(cacheNames = "usersList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public User updateUser(Long id, UserDTO user) {
        log.info("Updating user. id: ".concat(id.toString()).concat(" new data: ".concat(user.toString())));
        User originalUser = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        User updateUser = userRepository.save(UserMapper.toUser(originalUser, user));
        log.info("User updated success: id ".concat(id.toString()));
        return updateUser;
    }
}
