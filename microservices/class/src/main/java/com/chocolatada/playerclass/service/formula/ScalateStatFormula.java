package com.chocolatada.playerclass.service.formula;

public class ScalateStatFormula {
    public static int calculateStatAtLevel(float baseStat, float modifier, int bonus, int level) {
        return (int) Math.round((baseStat + bonus) * Math.pow(modifier, level));
    }
}
