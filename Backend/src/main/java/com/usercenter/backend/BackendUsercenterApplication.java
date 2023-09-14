package com.usercenter.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.usercenter.backend.Mapper")
public class BackendUsercenterApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendUsercenterApplication.class, args);
	}

}
