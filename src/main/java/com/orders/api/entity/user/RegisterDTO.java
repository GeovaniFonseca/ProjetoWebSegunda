package com.orders.api.entity.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
