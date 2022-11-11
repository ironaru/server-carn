package project.DB.server.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import project.DB.server.config.JwtUtils;
import project.DB.server.model.JwtRequest;
import project.DB.server.model.JwtResponse;
import project.DB.server.model.Medidor;
import project.DB.server.model.Usuario;
import project.DB.server.repository.MedidorRepository;
import project.DB.server.repository.UsuarioRepository;
import project.DB.server.service.UserDetailService;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }
        UserDetails userDetails = userDetailService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException exception) {
            throw new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales invalidas " + e.getMessage());
        }
    }

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MedidorRepository medidorRepository;
    @GetMapping("/usuario")
    public Usuario obtenerUsuarioActual(Principal principal){
        return (Usuario) this.userDetailService.loadUserByUsername(principal.getName());
    }
    @GetMapping("/medidoresUsuario")
    public ResponseEntity<Iterable<Medidor>> obtenerMedidoresUsuario(Principal principal){
        String usuario =  this.userDetailService.loadUserByUsername(principal.getName()).getUsername();
        Usuario usuarioRepo = usuarioRepository.findByUsuario(usuario);
        return ResponseEntity.ok(usuarioRepo.getSocio().getMedidores());
    }
    
    @GetMapping("/medidorUser/{id}")
    public ResponseEntity<Medidor> obtenerMedidorUsuario(Principal principal,@PathVariable Long id){
        return ResponseEntity.ok(medidorRepository.findById(id).get());
    }
}