package gtics.lab8_20223209.Entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ExpeditionCrewId implements Serializable {
    private Long expeditionId;
    private Long crewMemberId;

    public ExpeditionCrewId() {}
    public ExpeditionCrewId(Long expeditionId, Long crewMemberId) {
        this.expeditionId = expeditionId;
        this.crewMemberId = crewMemberId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpeditionCrewId that = (ExpeditionCrewId) o;
        return Objects.equals(expeditionId, that.expeditionId) && Objects.equals(crewMemberId, that.crewMemberId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(expeditionId, crewMemberId);
    }
}
