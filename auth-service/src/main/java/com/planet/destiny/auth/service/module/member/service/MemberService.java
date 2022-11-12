package com.planet.destiny.auth.service.module.member.service;


import com.planet.destiny.auth.service.module.member.item.MemberDto;
import com.planet.destiny.auth.service.module.member.model.MemberEntity;
import com.planet.destiny.auth.service.module.member.repository.MemberRepository;
import com.planet.destiny.auth.service.module.token.item.TokenDto;
import com.planet.destiny.auth.service.module.token.service.TokenService;
import com.planet.destiny.core.api.constant.RoleType;
import com.planet.destiny.core.api.exception.BusinessException;
import com.planet.destiny.core.api.exception.NotFoundUsernameException;
import com.planet.destiny.core.api.item.wrapper.response.RestSingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service(value = "memberService")
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    public RestSingleResponse login(MemberDto.LoginReq reqDto) {

        MemberEntity member = memberRepository.findByMemberId(reqDto.getMemberId()).orElseThrow(() -> new NotFoundUsernameException(reqDto.getMemberId()));

        if(!passwordEncoder.matches(reqDto.getPassword(), member.getPassword())) {
            throw new BusinessException("일치하는 회원 정보가 없습니다.");
        }

        return RestSingleResponse.success("정상적으로 로그인 되었습니다.", tokenService.adminTokenIssue(TokenDto.TokenIssueReq.builder().memberIdx(admin.getIdx()).roles(List.of(RoleType.ADMIN)).build()));



    }
}
