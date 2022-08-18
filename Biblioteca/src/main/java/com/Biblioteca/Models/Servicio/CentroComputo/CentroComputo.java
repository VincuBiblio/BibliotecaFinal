package com.Biblioteca.Models.Servicio.CentroComputo;

import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "centro_computo")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CentroComputo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_inventario",referencedColumnName = "id")
    private InventarioComputo inventario;

    @OneToMany (targetEntity = ComputoCliente.class, mappedBy = "centro")
    private List<ComputoCliente> computoCliente;

}
