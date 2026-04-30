package com.example.demo.service.jpa;

import com.example.demo.dto.BonusStatsDTO;
import com.example.demo.dto.PlayerStatsDTO;
import com.example.demo.exception.InvalidPlayerClassDataException;

public interface IPlayerClassStatsService {
    PlayerStatsDTO getScaledPlayerClassStats(Long classId, BonusStatsDTO bonus, int level) throws InvalidPlayerClassDataException;
}
