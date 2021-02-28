package com.matheusvargas481.analisededados;

import com.matheusvargas481.analisededados.service.diretorio.EscutaDiretorio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AnalisededadosApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AnalisededadosApplication.class, args);
        context.getBean(EscutaDiretorio.class).escutarCaminho();
    }

}
