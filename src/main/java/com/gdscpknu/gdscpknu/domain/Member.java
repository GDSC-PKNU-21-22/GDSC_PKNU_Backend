package com.gdscpknu.gdscpknu.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
    private String name;
    private String role;
    private String email;
    @Builder
    public Member(String name, String role, String email) {
        this.name = name;
        this.role = role;
        this.email = email;
    }
}
