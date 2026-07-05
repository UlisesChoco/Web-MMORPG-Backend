package com.chocolatada.combat.service.grpc;

import com.chocolatada.combat.grpc.Enemy;
import com.chocolatada.combat.grpc.EnemyServiceGrpc;
import com.chocolatada.combat.grpc.GetEnemyByIdRequest;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnemyStubClientServiceGrpcImpl {
    private final EnemyServiceGrpc.EnemyServiceBlockingStub enemyStub;

    public Enemy getEnemy(Long enemyId) throws StatusRuntimeException {
        GetEnemyByIdRequest request = GetEnemyByIdRequest.newBuilder()
                .setEnemyId(enemyId)
                .build();

        return enemyStub.getEnemyById(request).getEnemy();
    }
}
