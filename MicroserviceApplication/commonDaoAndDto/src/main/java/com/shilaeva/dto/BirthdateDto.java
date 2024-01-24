package com.shilaeva.dto;

import java.util.Calendar;
import java.util.Date;

public record BirthdateDto(int year, int month, int day) {
    static public BirthdateDto asBirthdateDto(Date birthdate) {
        Calendar birthdateTime = Calendar.getInstance();
        birthdateTime.setTime(birthdate);
        return new BirthdateDto(birthdateTime.get(Calendar.YEAR), birthdateTime.get(Calendar.MONTH) + 1,
                birthdateTime.get(Calendar.DATE));
    }

    public Date asDate() {
        Calendar catBirthdate = new Calendar.Builder()
                .set(Calendar.YEAR, this.year())
                .set(Calendar.MONTH, this.month() - 1)
                .set(Calendar.DATE, this.day())
                .build();

        return catBirthdate.getTime();
    }
}
