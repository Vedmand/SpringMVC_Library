package ru.vergolyas.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {

    private int personId;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 50, message = "Количество символов должно быть не меньше 2 и не больше 50")
    private String fullName;
    @Min(value = 1901, message = "Год рождения должен быть больше 1900")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int personId, String fullName, int yearOfBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }


}
