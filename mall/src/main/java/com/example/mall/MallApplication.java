package com.example.mall;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@OpenAPIDefinition(
		info = @Info(
				title = "Mall 项目 API 文档",
				version = "1.0",
				description = "电商商城后端接口文档"
		)
)


@SpringBootApplication
public class MallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}

}
