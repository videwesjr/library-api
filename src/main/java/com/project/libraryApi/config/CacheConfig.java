package com.project.libraryApi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
    public static final String USERCACHE = "users";
    public static final String USERLISTCACHE = "usersList";
    public static final String BOOKCACHE = "books";
    public static final String BOOKLISTCACHE = "booksList";
    public static final String REVIEWCACHE = "reviews";
    public static final String REVIEWLISTCACHE = "reviewsList";
    public static final String RESERVATIONCACHE = "reservations";
    public static final String RESERVATIONLISTCACHE = "reservationsList";
    @CacheEvict(allEntries = true, value = {BOOKCACHE, BOOKLISTCACHE, USERCACHE, USERLISTCACHE, REVIEWCACHE,
            REVIEWLISTCACHE, RESERVATIONCACHE, RESERVATIONLISTCACHE})
    @Scheduled(cron = "${cacheEvictAll.cron}")
    public void reportCacheEvict() {
        System.out.println("Evict all caches ".concat(LocalDateTime.now().toString()));
    }
}
