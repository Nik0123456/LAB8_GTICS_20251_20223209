package gtics.lab8_20223209.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "expeditions")
public class Expedition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_mision", nullable = false)
    @NotBlank
    private String nombreMision;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planeta_destino_id")
    private Planet planetaDestino;

    @Column(name = "fecha_lanzamiento", nullable = false)
    @NotNull
    private LocalDateTime fechaLanzamiento;

    @Column(nullable = false)
    @NotBlank
    private String estado;

    @Column(columnDefinition = "TEXT")
    private String objetivos;

    @Column(columnDefinition = "TEXT")
    private String resultados;

    // Getters y setters
}
