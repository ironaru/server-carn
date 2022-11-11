package project.DB.server.controller;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Roles;
import project.DB.server.model.Usuario;
import project.DB.server.repository.SocioRepository;
import project.DB.server.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    private ResponseEntity<Iterable<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<Usuario> findByUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRepository.findById(id).get());
    }

    @GetMapping("/{id}/roles")
    private ResponseEntity<Set<Roles>> roles(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRepository.findById(id).get().getRoles());
    }

    @PostMapping
    private ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) throws Exception {
        if (usuarioRepository.findByUsuario(usuario.getUsuario()) != null
                || socioRepository.findByCorreo(usuario.getSocio().getCorreo()) != null || socioRepository.findByCi(usuario.getSocio().getCi()) != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            Roles role = new Roles();
            role.setId(4);
            role.setAutoridad("SOCIO");
            role.setDescripcion("Rol de socio");
            Set<Roles> roles = new HashSet<>();
            roles.add(role);
            usuario.setClave(bCryptPasswordEncoder.encode(usuario.getClave()));
            usuario.setRoles(roles);
            Long miliseconds = System.currentTimeMillis();
            usuario.getSocio().setFechaReg(new Date(miliseconds));
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }
    }
    @DeleteMapping("/{id}")
    private void DeleteById(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    private Usuario updateUser(@PathVariable Long id, @RequestBody Usuario user) {
        Usuario userRepo = usuarioRepository.findById(id).get();
        userRepo.getSocio().setApellidos(user.getSocio().getApellidos());
        userRepo.getSocio().setNombres(user.getSocio().getNombres());
        userRepo.getSocio().setCorreo(user.getSocio().getCorreo());
        userRepo.getSocio().setFechaNac(user.getSocio().getFechaNac());
        userRepo.getSocio().setDireccion(user.getSocio().getDireccion());
        userRepo.getSocio().setActivo(user.getSocio().getActivo());
        userRepo.getSocio().setCi(user.getSocio().getCi());
        userRepo.getSocio().setFoto(user.getSocio().getFoto());
        userRepo.setRoles(user.getRoles());
        return usuarioRepository.save(userRepo);
    }

    @PutMapping("/{usuario}/password")
    private Usuario changePassword(@RequestBody Usuario user, @PathVariable String usuario) {
        Usuario userRepo = usuarioRepository.findByUsuario(usuario);
        userRepo.setClave(bCryptPasswordEncoder.encode(user.getClave()));
        return usuarioRepository.save(user);

    }

    @PutMapping("/profile/{id}")
    private Usuario updateUserProfile(@PathVariable Long id, @RequestBody Usuario user) {
        Usuario userRepo = usuarioRepository.findById(id).get();
        return usuarioRepository.save(userRepo);
    }
    


}