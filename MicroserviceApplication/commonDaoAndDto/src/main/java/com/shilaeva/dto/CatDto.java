package com.shilaeva.dto;

import com.shilaeva.models.Cat;

import java.util.Date;

public record CatDto(Long id, String name, BirthdateDto birthdate, String breed, String color) {
    public static CatDto asDto(Cat cat) {
        return new CatDto(cat.getId(), cat.getName(), BirthdateDto.asBirthdateDto(cat.getBirthdate()), cat.getBreed(),
                cat.getColor().toString());
    }
}
