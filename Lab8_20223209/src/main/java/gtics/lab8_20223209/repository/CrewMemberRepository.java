package gtics.lab8_20223209.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gtics.lab8_20223209.Entity.CrewMember;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
}
