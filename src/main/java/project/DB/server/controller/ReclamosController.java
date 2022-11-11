package project.DB.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Reclamos;
import project.DB.server.repository.ReclamosRepository;

@RestController
@RequestMapping
public class ReclamosController {
    
    @Autowired
    private ReclamosRepository reclamosRepository;

    @GetMapping("/reclamos")
    private ResponseEntity<Iterable<Reclamos>> findAll() {
        return ResponseEntity.ok(reclamosRepository.findAll());
    }
    @GetMapping("/reclamos/{id}")
    private @ResponseBody ResponseEntity<Optional<Reclamos>> findById(@PathVariable int id){
        return ResponseEntity.ok(reclamosRepository.findById(id));
    }
}
