package com.example.EducationDepartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
public class EducationDepartmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationDepartmentApplication.class, args);
		
	}

}
