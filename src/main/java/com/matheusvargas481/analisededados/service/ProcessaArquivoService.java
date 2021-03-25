package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.domain.Vendedor;
import com.matheusvargas481.analisededados.strategy.ProcessaDadoStrategy;
import org.springframework.stereotype.Service;

@Service
public class ProcessaArquivoService {
    ProcessaDadoStrategy processaDadoStrategy = new ProcessaDadoStrategy();

    public void processarArquivos(String linhaArquivo, DadoProcessado dadoProcessado) {
        if (linhaArquivo.startsWith(Vendedor.COMECA_COM_001)) {
            processaDadoStrategy.setMontaObjetoStrategy(new VendedorService());
            processaDadoStrategy.executarStrategy(linhaArquivo, dadoProcessado);
        } else if (linhaArquivo.startsWith(Cliente.COMECA_COM_002)) {
            processaDadoStrategy.setMontaObjetoStrategy(new ClienteService());
            processaDadoStrategy.executarStrategy(linhaArquivo, dadoProcessado);
        } else if (linhaArquivo.startsWith(Venda.COMECA_COM_003)) {
            processaDadoStrategy.setMontaObjetoStrategy(new VendaService());
            processaDadoStrategy.executarStrategy(linhaArquivo, dadoProcessado);
        }
    }

}
