package br.com.helpgr.repository;

import br.com.helpgr.model.Chamado;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChamadoRepository implements PanacheMongoRepository<Chamado> {
}
