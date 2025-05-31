package gtics.lab8_20223209.Entity;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "expedition_crew")
public class ExpeditionCrew implements Serializable {
    @EmbeddedId
    private ExpeditionCrewId id;

    @ManyToOne
    @MapsId("expeditionId")
    @JoinColumn(name = "expedition_id")
    private Expedition expedition;

    @ManyToOne
    @MapsId("crewMemberId")
    @JoinColumn(name = "crew_member_id")
    private CrewMember crewMember;

}
