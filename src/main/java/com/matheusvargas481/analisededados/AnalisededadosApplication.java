package com.matheusvargas481.analisededados;

import com.matheusvargas481.analisededados.service.diretorio.EscutaDiretorio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalisededadosApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnalisededadosApplication.class, args);
		EscutaDiretorio escutaDiretorio = new EscutaDiretorio();
		escutaDiretorio.escutarCaminho();
	}

}
