package com.dpp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan({"com.dpp.entity"})
@EnableJpaRepositories(basePackages = {"com.dpp.repositories"})
@EnableJpaAuditing
@EnableTransactionManagement
@Slf4j
public class DppAppConfig implements ServletContextInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
  }


}
