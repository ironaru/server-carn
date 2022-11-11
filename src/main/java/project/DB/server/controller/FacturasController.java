package project.DB.server.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Cobros;
import project.DB.server.model.Consumos;
import project.DB.server.model.Facturas;
import project.DB.server.model.Tarifas;
import project.DB.server.repository.CobrosRepository;
import project.DB.server.repository.ConsumosRepository;
import project.DB.server.repository.FacturasRepository;
import project.DB.server.repository.TarifasRepository;

@RestController
@ControllerAdvice
@RequestMapping
public class FacturasController {

    @Autowired
    private FacturasRepository facturasRepository;

    @Autowired
    private ConsumosRepository consumosRepository;

    @Autowired
    private TarifasRepository tarifasRepository;

    @Autowired
    private CobrosRepository cobrosRepository;

    @GetMapping
    private ResponseEntity<Iterable<Facturas>> findAll() {
        return ResponseEntity.ok(facturasRepository.findAll());
    }

    @PostMapping("/factura/consumo/{id}")
    private @ResponseBody ResponseEntity<Facturas> findById(@PathVariable Integer id, @RequestBody Facturas facturaR) {
        Consumos consumoRepo = consumosRepository.findById(id).get();
        List<Tarifas> tarifas = tarifasRepository.findByFechaInicAndConsumoMax(consumoRepo.getFecha(),
                consumoRepo.getLectura());
        Facturas factura = new Facturas();
        if (consumoRepo.getIdFactura() != null) {
            factura.setId(consumoRepo.getIdFactura().getId());
        }
        factura.setNIT(consumoRepo.getIdMedidor().getIdSocio().getCi());
        factura.setRazonSocial(consumoRepo.getIdMedidor().getIdSocio().getApellidos() + " "
                + consumoRepo.getIdMedidor().getIdSocio().getNombres());
        factura.setIdConsumo(consumoRepo);
        factura.setPeriodo(facturaR.getPeriodo());
        factura.setEstado(false);
        if (tarifas.get(0).getCostoUnit() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        factura.setMonto(consumoRepo.getLectura() * tarifas.get(0).getCostoUnit());
        return ResponseEntity.ok(facturasRepository.save(factura));
    }

    @PutMapping("/factura")
    private @ResponseBody ResponseEntity<Facturas> confimarFactura(@RequestBody Facturas facturaR) {
        Facturas factura = facturasRepository.findById(facturaR.getId()).get();
        if (factura != null) {
            factura.setEstado(true);
            facturasRepository.save(factura);
            Cobros cobro = new Cobros();
            Long miliseconds = System.currentTimeMillis();
            cobro.setRef("----------------------------------------------------------------");
            cobro.setFechaHora(new Timestamp(miliseconds));
            cobro.setIdFactura(factura);
            cobro.setMonto(factura.getMonto());
            cobro.setSocios(factura.getIdConsumo().getIdMedidor().getIdSocio());
            cobro.setTipo(1);
            cobrosRepository.save(cobro);
            return ResponseEntity.ok(factura);
        }else
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
  
    }

    @GetMapping("/genera/{fecha}&{consumo}")
    private Iterable<Tarifas> resp(@PathVariable Date fecha, @PathVariable Double consumo) {

        return tarifasRepository.findByFechaInicAndConsumoMax(fecha, consumo);
    }
}
