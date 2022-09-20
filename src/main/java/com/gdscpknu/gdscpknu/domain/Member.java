package com.gdscpknu.gdscpknu.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
    private String name;
    private String email;
    private String role;

    @Builder
    public Member(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
