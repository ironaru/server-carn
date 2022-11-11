package project.DB.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Reclamos;



@CrossOrigin("*")
@RepositoryRestResource(path = "reclamos", collectionResourceRel = "Reclamos")
public interface ReclamosRepository extends CrudRepository<Reclamos, Integer> {

}