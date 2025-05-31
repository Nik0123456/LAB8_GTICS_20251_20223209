package gtics.lab8_20223209.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gtics.lab8_20223209.Entity.ExpeditionCrew;
import gtics.lab8_20223209.Entity.ExpeditionCrewId;

public interface ExpeditionCrewRepository extends JpaRepository<ExpeditionCrew, ExpeditionCrewId> {
    @Query("SELECT ec FROM ExpeditionCrew ec WHERE ec.crewMember.id = :crewId AND (ec.expedition.estado = 'Planificada' OR ec.expedition.estado = 'En Curso')")
    List<ExpeditionCrew> findActiveAssignmentsByCrewId(@Param("crewId") Long crewId);

    List<ExpeditionCrew> findByExpeditionId(Long expeditionId);
}
