package com.example.res.server.controllers.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TokenDto {
    private UUID access_token;
    private String token_type;
    private Long expires_in;
    private String scope;
}
