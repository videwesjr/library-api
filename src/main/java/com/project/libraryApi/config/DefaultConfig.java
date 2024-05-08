package com.project.libraryApi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
  "com.project.libraryApi",
  "com.project.libraryApi.config"
})
public class DefaultConfig {}
