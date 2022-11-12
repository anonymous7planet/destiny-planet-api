package com.planet.destiny.auth.service.module.member.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Slf4j
@Getter
@Entity
@Table(schema = "destiny_planet_user", name = "tb_member_role_relation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleMapEntity implements Serializable {

    @Id
    @Column(name = "idx", columnDefinition = "INT")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_idx")
    private MemberEntity member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_idx")
    private RoleEntity role;

    @Column(name = "creator_idx", columnDefinition="INT")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberEntity creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "ASIA/SEOUL")
    @Generated(GenerationTime.INSERT)
    @Column(name = "created_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createDateTime;

    @Column(name = "modifier_idx", columnDefinition="INT")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberEntity modifier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "ASIA/SEOUL")
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "modified_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDateTime;

}
