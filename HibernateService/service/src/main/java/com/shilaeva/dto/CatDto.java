package com.shilaeva.dto;

import java.util.Date;

public record CatDto(Long id, String name, Date birthdate, String breed, String color, OwnerDto owner) {
}
