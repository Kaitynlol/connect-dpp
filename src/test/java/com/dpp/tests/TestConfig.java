package com.dpp.tests;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.dpp.config","com.dpp.storage"})
public class TestConfig {

}
