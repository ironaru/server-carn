package project.DB.server.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Medidor;
import project.DB.server.model.Socio;
import project.DB.server.repository.MedidorRepository;
import project.DB.server.repository.SocioRepository;

@RestController
@RequestMapping("/medidores")
public class MedidorController {
    
    @Autowired
    private MedidorRepository medidorRepository;


    @Autowired
    private SocioRepository socioRepository;
    
    @GetMapping
    private ResponseEntity<Iterable<Medidor>> findAll(){
        return ResponseEntity.ok(medidorRepository.findAll());
    }
    @GetMapping("/{id}")
    private @ResponseBody ResponseEntity<Optional<Medidor>> findById(@PathVariable Long id){
        return ResponseEntity.ok(medidorRepository.findById(id));
    }

    @PostMapping
    private ResponseEntity<Medidor> saveMedidor(@RequestBody Medidor medidor){
        Optional<Socio> socio = socioRepository.findById(medidor.getIdSocio().getId_socio());
        if(medidorRepository.findBySerial(medidor.getSerial()) == null && socio!=null){
            medidor.setIdSocio(socio.get());
            return ResponseEntity.ok(medidorRepository.save(medidor));
        }else
            return null;
    }
    @PutMapping("/{id}")
    private ResponseEntity<Medidor> updateMedidor(@PathVariable Long id, @RequestBody Medidor medidor){
        System.out.println("----------------------------------------------------");
        System.out.println(medidor.getIdSocio().getId_socio());
        Medidor medidorRepo = medidorRepository.findById(id).get();
        medidorRepo.setFechaInst(medidor.getFechaInst());
        medidorRepo.setMarca(medidor.getMarca());
        medidorRepo.setRegInic(medidor.getRegInic());
        medidorRepo.setSerial(medidor.getSerial());
        Socio socioRepo = socioRepository.findById(medidor.getIdSocio().getId_socio()).get();
        System.out.println(socioRepo.getNombres());
        
        medidorRepo.setIdSocio(socioRepo);
        return ResponseEntity.ok(medidorRepository.save(medidorRepo));
    }

    @DeleteMapping("/{id}")
    private void DeleteById(@PathVariable Long id) {
        medidorRepository.deleteById(id);
    }

}
