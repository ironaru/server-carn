package project.DB.server.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Multas {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Date fechaVigencia;
    @Column(nullable = false, precision = 3,scale = 2)
    private Double monto;

    @OneToMany(mappedBy = "idMulta")
    @JsonIgnore
    private Set<Cobros> idcobro;

}
