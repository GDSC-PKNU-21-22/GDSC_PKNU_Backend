package com.gdscpknu.gdscpknu.controller;

import com.gdscpknu.gdscpknu.domain.Member;
import com.gdscpknu.gdscpknu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping
    public ApiResponse<List<Member>> getProfiles() {
        return ApiResponse.SUCCESS(memberService.getAllMember());
    }
}
