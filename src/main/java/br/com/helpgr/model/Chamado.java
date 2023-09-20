package br.com.helpgr.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.LocalDate;

@MongoEntity(collection = "CHAMADO")
public class Chamado extends PanacheMongoEntity {
    public String title;
    public String description;

    public String clienteEmail;
    private String atendenteEmail;

    private String status; // Adicione este campo para representar o status do chamado

    private String solucao; // Adicione este campo para representar o status do chamado

    private LocalDate dataAbertura; // Adicione este campo para a data de abertura
    private LocalDate dataEncerrado; // Adicione este campo para a data encerrado

    public String getStatus() {
        return status;
    }

    public String getAtendenteEmail() {
        return atendenteEmail;
    }

    public void setAtendenteEmail(String atendenteEmail) {
        this.atendenteEmail = atendenteEmail;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataEncerrado() {
        return dataEncerrado;
    }

    public void setDataEncerrado(LocalDate dataEncerrado) {
        this.dataEncerrado = dataEncerrado;
    }

    // Construtor para definir o status como "aberto" por padrão
    public Chamado() {
        this.status = "aberto";
    }
}

   // public String atendenteEmail;

