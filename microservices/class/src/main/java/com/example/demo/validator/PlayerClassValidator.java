package com.example.demo.validator;

import java.util.List;
import java.util.regex.Pattern;

import com.example.demo.configuration.resources.definition.player_class.ClassDefinition;
import com.example.demo.entity.PlayerClassEntity;
import com.example.demo.exception.InvalidPlayerClassDataException;
import com.example.demo.mapper.PlayerClassMapper;

public class PlayerClassValidator {
    private final static String NAME_REGEX = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$";

    private final static Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public static boolean validate(PlayerClassEntity playerClass, int level) throws InvalidPlayerClassDataException {
        isValidClassName(playerClass.getName());
        isValidDescription(playerClass.getDescription());
        isValidLevel(level);
        isValidStats(playerClass);
        return true;
    }

    public static boolean validate(PlayerClassEntity playerClass) throws InvalidPlayerClassDataException {
        isValidClassName(playerClass.getName());
        isValidDescription(playerClass.getDescription());
        isValidStats(playerClass);
        return true;
    }

    public static boolean validate(List<ClassDefinition> classDefinitions) throws InvalidPlayerClassDataException {
        for(ClassDefinition classDef : classDefinitions) {
            PlayerClassEntity playerClass = PlayerClassMapper.toPlayerClass(classDef);
            isValidClassName(playerClass.getName());
            isValidDescription(playerClass.getDescription());
            isValidStats(playerClass);
        }
        return true;
    }

    public static boolean isValidClassName(String className) throws InvalidPlayerClassDataException {
        if(className == null || className.trim().isEmpty())
            throw new InvalidPlayerClassDataException("El nombre de la clase no puede estar vacío");

        if(!NAME_PATTERN.matcher(className).matches())
            throw new InvalidPlayerClassDataException("El nombre de la clase contiene caracteres inválidos");

        return true;
    }

    public static boolean isValidDescription(String description) throws InvalidPlayerClassDataException {
        if(description == null || description.trim().isEmpty())
            throw new InvalidPlayerClassDataException("La descripción de la clase no puede estar vacía");

        return true;
    }
    
    public static boolean isValidLevel(int level) throws InvalidPlayerClassDataException {
        if(level <= 0)
            throw new InvalidPlayerClassDataException("El nivel debe ser un número positivo");

        return true;
    }
    
    public static boolean isValidStats(PlayerClassEntity playerClass) throws InvalidPlayerClassDataException {
        float critRate = playerClass.getCritRate();
        float critDamage = playerClass.getCritDamage();
        int hp = playerClass.getHp();
        int atk = playerClass.getAtk();
        int def = playerClass.getDef();
        int stamina = playerClass.getStamina();
        int accuracy = playerClass.getAccuracy();
        int evasion = playerClass.getEvasion();

        if(critRate < 0f || critDamage < 0f || hp < 0 || atk < 0 || def < 0 || stamina < 0 || accuracy < 0 || evasion < 0)
            throw new InvalidPlayerClassDataException("Los atributos de la clase de jugador no pueden ser negativos");

        return true;
    }
}
