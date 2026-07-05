package com.chocolatada.player.validator;

import com.chocolatada.player.dto.PlayerUpdateDTO;
import com.chocolatada.player.exception.InvalidPlayerDataException;

import java.util.regex.Pattern;

public class PlayerValidator {
    private static final String NAME_REGEX = "^[a-zA-Z0-9_]{1,12}$";

    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public static void validatePlayerUpdateDTO(PlayerUpdateDTO playerUpdateDTO) throws InvalidPlayerDataException {
        playerUpdateDTO.getName().ifPresent(name -> {
            if (!NAME_PATTERN.matcher(name).matches())
                throw new IllegalArgumentException("El nombre del personaje solo puede contener letras, números, guiones bajos y no debe exceder los 12 caracteres.");

            if (name.isBlank() || name.length() > 12)
                throw new IllegalArgumentException("El nombre del personaje no puede estar vacío ni exceder los 12 caracteres.");
        });

        playerUpdateDTO.getGold().ifPresent(gold -> {
            if (gold < 0)
                throw new IllegalArgumentException("El oro no puede ser negativo.");
        });

        playerUpdateDTO.getLevel().ifPresent(level -> {
            if (level < 1)
                throw new IllegalArgumentException("El nivel debe ser al menos 1.");
        });

        playerUpdateDTO.getExperience().ifPresent(experience -> {
            if (experience < 0)
                throw new IllegalArgumentException("La experiencia no puede ser negativa.");
        });

        playerUpdateDTO.getExperienceLimit().ifPresent(experienceLimit -> {
            if (experienceLimit <= 0)
                throw new IllegalArgumentException("El límite de experiencia no puede ser negativo ni cero.");
        });

        playerUpdateDTO.getFreeStatPoints().ifPresent(freeStatPoints -> {
            if (freeStatPoints < 0)
                throw new IllegalArgumentException("Los puntos de estadística libres no pueden ser negativos.");
        });
    }
}
