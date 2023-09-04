package com.project.streampark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@SpringBootApplication
@PropertySource(value = { "classpath:global.properties" }) // 직접만든 환경설정파일위치
@MapperScan(basePackages = { "com.project.mapper" })
@ComponentScan(basePackages = { 
	"com.project.controller", 
	"com.project.service", 
	"com.project.config",
	"com.project.restcontroller", 
	"com.project.controller.jpa",
	"com.project.controller.mybatis",
	"com.project.filter" ,
	"com.project.utils" 
}) // 컨트롤러, 서비스 위치, 시큐리티 환경설정
@EntityScan(basePackages = { "com.project.entity" }) // 엔티티 위치
@EnableJpaRepositories(basePackages = { "com.project.repository" }) // 저장소 위치
public class StreamparkApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(StreamparkApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StreamparkApplication.class);
	}

	@Bean
	public PageableHandlerMethodArgumentResolverCustomizer customize() {
	  return p -> {
		p.setOneIndexedParameters(true);	// 1부터 시작
	  };
	}
	
}
