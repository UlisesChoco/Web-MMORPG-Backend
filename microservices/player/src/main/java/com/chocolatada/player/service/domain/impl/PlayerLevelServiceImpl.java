package com.chocolatada.player.service.domain.impl;

import com.chocolatada.player.service.domain.IPlayerLevelService;
import org.springframework.stereotype.Service;

@Service
public class PlayerLevelServiceImpl implements IPlayerLevelService {
    private final int BASE_SCALE = 100;
    private final float POLYNOMIAL_EXPONENT = 2.5f;
    private final int EXPONENTIAL_SCALE = 50;
    private final float EXPONENTIAL_GROWTH = 1.08f;

    @Override
    public int calculateExperienceLimit(int level) {
        return (int)(BASE_SCALE * Math.pow(level, POLYNOMIAL_EXPONENT) + EXPONENTIAL_SCALE * Math.pow(EXPONENTIAL_GROWTH, level));
    }
}
