package gtics.lab8_20223209.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "crew_members")
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    @NotBlank
    private String nombreCompleto;

    @Column(nullable = false)
    @NotBlank
    private String especialidad;

    @Column
    private String rango;

    @Column(name = "fecha_contratacion", nullable = false)
    @NotNull
    private LocalDate fechaContratacion;

    // Getters y setters
}
