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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Entity
@Table(schema = "destiny_planet_user", name = "tb_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity implements Serializable {

    @Id
    @Column(name = "idx", columnDefinition = "INT")
    private Long idx;

    @Column(name = "member_id", columnDefinition = "VARCHAR(30)")
    private String memberId;

    @Column(name = "password", columnDefinition = "VARCHAR(150)")
    private String password;

    @Column(name = "name", columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(name = "phone", columnDefinition = "VARCHAR(12)")
    private String phone;

    @Column(name = "status", columnDefinition = "CHAR(1)")
    private String status;

    @Column(name = "manager_yn", columnDefinition = "CHAR(1)")
    private String isManager;

    @Column(name = "last_pwd_init_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastPwdInitDate;

    @Column(name = "last_login_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastLoginDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "member")
    private List<RoleMapEntity> roles = new ArrayList<>();

    @Column(name = "creator_idx", columnDefinition="INT")
    private Long creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "ASIA/SEOUL")
    @Generated(GenerationTime.INSERT)
    @Column(name = "created_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createDateTime;

    @Column(name = "modifier_idx", columnDefinition="INT")
    private Long modifier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "ASIA/SEOUL")
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "modified_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDateTime;

}
