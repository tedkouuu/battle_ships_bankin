package com.example.ships.models.dto;

import com.example.ships.models.CategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class CreateShipDTO {

    @NotBlank
    @Size(min = 2, max = 10)
    private String name;

    @Positive
    private long power;

    @Positive
    private long health;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    private int category;

    public CreateShipDTO() {
    }

    public String getName() {
        return name;
    }

    public CreateShipDTO setName(String name) {
        this.name = name;
        return this;
    }

    public long getPower() {
        return power;
    }

    public CreateShipDTO setPower(long power) {
        this.power = power;
        return this;
    }

    public long getHealth() {
        return health;
    }

    public CreateShipDTO setHealth(long health) {
        this.health = health;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public CreateShipDTO setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public int getCategory() {
        return category;
    }

    public CreateShipDTO setCategory(int category) {
        this.category = category;
        return this;
    }

    @Override
    public String toString() {
        return "CreateShipDTO{" +
                "name='" + name + '\'' +
                ", power=" + power +
                ", health=" + health +
                ", created=" + created +
                ", category=" + category +
                '}';
    }

}
