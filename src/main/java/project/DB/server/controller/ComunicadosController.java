package project.DB.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Comunicados;
import project.DB.server.repository.ComunicadosRepository;

@RestController
@RequestMapping
public class ComunicadosController {
    @Autowired
    private ComunicadosRepository comunicadosRepository;

    @GetMapping("/comunicados")
    private ResponseEntity<Iterable<Comunicados>> findAll() {
        return ResponseEntity.ok(comunicadosRepository.findAll());
    }

    @GetMapping("/comunicados/{id}")
    private @ResponseBody ResponseEntity<Optional<Comunicados>> findById(@PathVariable int id){
        return ResponseEntity.ok(comunicadosRepository.findById(id));
    }
}
