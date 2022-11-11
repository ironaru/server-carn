package project.DB.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Solicitudes {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String detalle;
    @Column(nullable = false)
    private Date fecha;
    @Column(nullable = false)
    private boolean atendido;

    @ManyToOne
    @JoinColumn(name = "id_socio")
    private Socio idsocio;

    
}
