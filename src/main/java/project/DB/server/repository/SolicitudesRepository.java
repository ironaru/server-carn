package project.DB.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Solicitudes;



@CrossOrigin("*")
@RepositoryRestResource(path = "solicitudes", collectionResourceRel = "Solicitudes" )
public interface SolicitudesRepository extends CrudRepository<Solicitudes, Integer> {

}