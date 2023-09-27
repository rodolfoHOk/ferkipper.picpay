package br.com.hiokdev.picpaysimplificado.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Pic Pay Simplificado")
        .description("Desafio backend Pic Pay simplificado")
        .version("v1")
        .contact(new Contact().name("Rudolf HiOk").email("hioktec@gmail.com"))
      )
      .tags(Arrays.asList(
        new Tag().name("Users").description("Manager users"),
        new Tag().name("Transactions").description("Realize transaction")
      ));
  }
}
