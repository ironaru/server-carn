package project.DB.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Facturas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String razonSocial;
    @NonNull
    @Column
    private Long NIT;
    @Column(nullable = false)
    private String periodo;
    @Column(nullable = false)
    private Double monto;
    @Column(nullable = false)
    private Boolean estado;

    @OneToOne
    @JoinColumn(name = "id_consumo")
    private Consumos idConsumo;

    @OneToOne(mappedBy = "idFactura")
    @JsonIgnore
    private Cobros cobros;

    @PrePersist
    public void costoPersist(){
        redondeo2d();
    }
    @PreUpdate
    public void costoUpdate(){
        redondeo2d();
    }
    public void redondeo2d(){
        monto = Math.round(monto*100.0)/100.0;
    }

}
