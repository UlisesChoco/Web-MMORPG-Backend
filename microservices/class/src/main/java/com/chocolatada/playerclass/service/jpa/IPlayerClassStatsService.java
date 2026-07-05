package com.chocolatada.playerclass.service.jpa;

import com.chocolatada.playerclass.dto.BonusStatsDTO;
import com.chocolatada.playerclass.dto.PlayerStatsDTO;
import com.chocolatada.playerclass.exception.InvalidPlayerClassDataException;

public interface IPlayerClassStatsService {
    PlayerStatsDTO getScaledPlayerClassStats(Long classId, BonusStatsDTO bonus, int level) throws InvalidPlayerClassDataException;
}
