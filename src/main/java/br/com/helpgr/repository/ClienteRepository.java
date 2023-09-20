package br.com.helpgr.repository;

import br.com.helpgr.model.Cliente;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheMongoRepository<Cliente> {

    public Cliente findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
