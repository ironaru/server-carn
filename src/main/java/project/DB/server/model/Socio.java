package project.DB.server.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "socios")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private Long id_socio;
    @Column(length = 70,nullable = false)
    private String nombres;
    @Column(length = 70, nullable = false)
    private String apellidos;
    @Column(length = 100, nullable = false)
    private String correo;
    @Column(nullable = false)
    private Boolean activo;
    @Column(length = 12)
    private Long ci;
    @Column(nullable = true, length = 1000000)
    private String foto;
    @Column
    private Date fechaNac;
    @Column(updatable = false)
    private Date fechaReg;
    @Column
    private String direccion;

    @OneToMany(mappedBy = "idsocio")
    @JsonIgnore
    private Set<Solicitudes> solicitudes= new HashSet<>();

    @OneToMany(mappedBy = "idSocio")
    @JsonIgnore
    private Set<Medidor> medidores = new HashSet<>();
    
    @OneToOne(mappedBy = "socio")
    @JsonIgnore
    private Usuario user;

    @OneToMany(mappedBy = "socios")
    @JsonIgnore
    private Set<Socio> socios = new HashSet<>();

    
    
}
