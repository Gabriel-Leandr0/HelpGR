package br.com.helpgr.repository;

import br.com.helpgr.model.Atendente;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtendenteRepository implements PanacheMongoRepository<Atendente> {

    public Atendente findByEmail(String email) {
        return find("email", email).firstResult();
    }
}

