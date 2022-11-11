package project.DB.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Reclamos extends Solicitudes {

    @Column(nullable = false)
    private Date fechaAtencion;
    @Column(nullable = false)
    private String resultado;

    
}
