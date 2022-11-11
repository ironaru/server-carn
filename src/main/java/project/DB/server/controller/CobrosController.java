package project.DB.server.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Cobros;
import project.DB.server.repository.CobrosRepository;
import project.DB.server.repository.ConsumosRepository;

@RestController
@RequestMapping("/cobros")
public class CobrosController {
    @Autowired
    private CobrosRepository cobrosRepository;

    @Autowired
    private ConsumosRepository consumosRepository;
    @GetMapping
    private @ResponseBody ResponseEntity<Iterable<Cobros>> getAllCobros(){
        return ResponseEntity.ok(cobrosRepository.findAll());
    }

    @GetMapping("/medidor/{id}")
    private @ResponseBody ResponseEntity<Iterable<Cobros>> findById(@PathVariable Long id){
        ArrayList<Cobros>  cobros =  new ArrayList<>();
        consumosRepository.findByIdMedidor(id).forEach(consumo->{
            if(consumo.getIdFactura().getEstado()){
                cobros.add(consumo.getIdFactura().getCobros());
            }
        });
        return ResponseEntity.ok(cobros);
    }

    @PostMapping
    private ResponseEntity<Cobros> updateCobro(@RequestBody Cobros cobro){
        return ResponseEntity.ok(cobro);
    }
    @PutMapping("/{id}")
    private ResponseEntity<Cobros> updateCobro(@PathVariable Integer id, @RequestBody Cobros cobro){
        Cobros cobrosRepo = cobrosRepository.findById(id).get();
        return ResponseEntity.ok(cobrosRepo);
    }

}
