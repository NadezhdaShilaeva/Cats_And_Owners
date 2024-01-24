package com.shilaeva.mapping;

import com.shilaeva.dto.CatDto;
import com.shilaeva.models.Cat;

public class CatMapping {
    public static CatDto asDto(Cat cat) {
        return new CatDto(cat.getId(), cat.getName(), cat.getBirthdate(), cat.getBreed(), cat.getColor().toString(),
                OwnerMapping.asDto(cat.getOwner()));
    }
}
