package com.telecom.deviation.repository;

import com.telecom.deviation.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByDeviationIdOrderByCreatedAtDesc(Long deviationId);
    List<AuditLog> findByActorId(Long actorId);
    List<AuditLog> findByActionType(String actionType);
}