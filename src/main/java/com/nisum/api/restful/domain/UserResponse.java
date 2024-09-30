package com.nisum.api.restful.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private int id;
    private Date created;
    private Date modified;
    private Date last_login;
    private String token;
    private Boolean isActive;
}
