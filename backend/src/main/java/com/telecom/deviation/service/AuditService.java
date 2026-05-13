package com.telecom.deviation.service;

import com.telecom.deviation.entity.AuditLog;
import com.telecom.deviation.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Transactional
    public void logAction(Long deviationId, Long actorId, String actionType,
                         String previousValue, String newValue, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setDeviationId(deviationId);
        auditLog.setActorId(actorId);
        auditLog.setActionType(actionType);
        auditLog.setPreviousValue(previousValue);
        auditLog.setNewValue(newValue);
        auditLog.setDetails(details);
        
        auditLogRepository.save(auditLog);
    }
}