package project.DB.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Solicitudes;
import project.DB.server.repository.SolicitudesRepository;

@RestController
@RequestMapping
public class SolicitudesController {
    @Autowired
    private SolicitudesRepository solicitudesRepository;

    @GetMapping("/solicitudes")
    private ResponseEntity<Iterable<Solicitudes>> findAll(){
        return ResponseEntity.ok(solicitudesRepository.findAll());
    }
    @GetMapping("/solicitudes/{id}")
    private @ResponseBody ResponseEntity<Optional<Solicitudes>> findById(@PathVariable int id){
        return ResponseEntity.ok(solicitudesRepository.findById(id));
    }
}
