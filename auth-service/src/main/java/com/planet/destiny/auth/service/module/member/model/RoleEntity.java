package com.planet.destiny.auth.service.module.member.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
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
@Table(schema = "destiny_planet_user", name = "tb_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity implements Serializable {

    @Id
    @Column(name = "idx", columnDefinition = "INT")
    private Long idx;

    @Column(name = "role", columnDefinition = "VARCHAR(30)")
    private String role;

    @Column(name = "role_name", columnDefinition = "VARCHAR(20)")
    private String roleName;

    @Column(name = "role_desc", columnDefinition = "VARCHAR(100)")
    private String roleDesc;

//    private Integer limitCnt;
//
//    private String displayYn;
//
//    private String useYn;
//
//    private String deleteYn;
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
