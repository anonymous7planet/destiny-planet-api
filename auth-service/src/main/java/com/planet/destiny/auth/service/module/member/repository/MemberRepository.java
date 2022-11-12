package com.planet.destiny.auth.service.module.member.repository;

import com.planet.destiny.auth.service.module.member.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByIdx(Long idx);

    Optional<MemberEntity> findByMemberId(String memberId);
}
