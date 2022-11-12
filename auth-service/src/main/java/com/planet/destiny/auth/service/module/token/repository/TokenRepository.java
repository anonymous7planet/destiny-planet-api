package com.planet.destiny.auth.service.module.token.repository;


import com.planet.destiny.auth.service.module.token.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<List<TokenEntity>> findByMemberIdx(Long memberIdx);
    Optional<TokenEntity> findByRefreshToken(String refreshToken);
}
