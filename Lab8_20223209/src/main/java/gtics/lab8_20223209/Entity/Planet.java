package gtics.lab8_20223209.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "planets")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String nombre;

    @Column(name = "tipo_planeta", nullable = false)
    @NotBlank
    private String tipoPlaneta;

    @Column(nullable = false)
    private boolean habitable;

    @Column(name = "gravedad_relativa", nullable = false)
    private double gravedadRelativa;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // Getters y setters
}
