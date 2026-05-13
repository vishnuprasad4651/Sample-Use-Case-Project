package com.telecom.deviation.service;

import com.telecom.deviation.dto.*;
import com.telecom.deviation.entity.*;
import com.telecom.deviation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeviationService {

    @Autowired
    private DeviationRepository deviationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AIAssistantService aiAssistantService;

    @Autowired
    private AuditService auditService;

    @Transactional
    public DeviationResponse createDeviation(DeviationRequest request) {
        Deviation deviation = new Deviation();
        deviation.setInitiatorId(request.getInitiatorId());
        deviation.setDeviationType(request.getDeviationType());
        deviation.setCustomerContext(request.getCustomerContext());
        deviation.setBusinessJustification(request.getBusinessJustification());
        deviation.setStatus("DRAFT");

        deviation = deviationRepository.save(deviation);

        auditService.logAction(deviation.getDeviationId(), request.getInitiatorId(),
            "DEVIATION_CREATED", null, "DRAFT", "Deviation created in DRAFT status");

        return mapToResponse(deviation);
    }

    @Transactional
    public DeviationResponse updateDeviation(Long id, DeviationRequest request, Long userId) {
        Deviation deviation = deviationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Deviation not found"));

        deviation.setDeviationType(request.getDeviationType());
        deviation.setCustomerContext(request.getCustomerContext());
        deviation.setBusinessJustification(request.getBusinessJustification());

        deviation = deviationRepository.save(deviation);

        auditService.logAction(id, userId, "DEVIATION_UPDATED", null, null,
            "Deviation details updated");

        return mapToResponse(deviation);
    }

    public DeviationResponse getDeviation(Long id) {
        Deviation deviation = deviationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Deviation not found"));
        return mapToResponse(deviation);
    }

    public List<DeviationResponse> getAllDeviations() {
        return deviationRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public List<DeviationResponse> getDeviationsByStatus(String status) {
        return deviationRepository.findByStatus(status).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public List<DeviationResponse> getDeviationsByInitiator(Long initiatorId) {
        return deviationRepository.findByInitiatorId(initiatorId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public DeviationResponse submitDeviation(Long id, Long userId) {
        Deviation deviation = deviationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Deviation not found"));

        if (!"DRAFT".equals(deviation.getStatus())) {
            throw new RuntimeException("Can only submit DRAFT deviations");
        }

        deviation.setStatus("PENDING_OPS");
        deviation = deviationRepository.save(deviation);

        auditService.logAction(id, userId, "DEVIATION_SUBMITTED", "DRAFT", "PENDING_OPS",
            "Deviation submitted for Operations approval");

        return mapToResponse(deviation);
    }

    @Transactional
    public DeviationResponse triggerAIAnalysis(Long id, Long userId) {
        Deviation deviation = deviationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Deviation not found"));

        String justification = aiAssistantService.generateJustification(
            deviation.getDeviationType(), deviation.getCustomerContext());
        String policyMatch = aiAssistantService.matchPolicy(
            deviation.getDeviationType(), deviation.getCustomerContext());
        Map<String, String> riskResult = aiAssistantService.calculateRiskScore(
            deviation.getDeviationType(),
            deviation.getBusinessJustification() != null ? deviation.getBusinessJustification() : "",
            deviation.getCustomerContext());

        deviation.setAiJustification(justification);
        deviation.setAiPolicyMatch(policyMatch);
        deviation.setAiRiskScore(riskResult.get("score"));
        deviation.setAiRiskExplanation(riskResult.get("explanation"));

        deviation = deviationRepository.save(deviation);

        auditService.logAction(id, userId, "AI_ANALYSIS_TRIGGERED", null, null,
            "AI analysis generated for deviation");

        return mapToResponse(deviation);
    }

    @Transactional
    public DeviationResponse updateStatus(Long id, String newStatus, Long userId) {
        Deviation deviation = deviationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Deviation not found"));

        String oldStatus = deviation.getStatus();
        deviation.setStatus(newStatus);
        deviation = deviationRepository.save(deviation);

        auditService.logAction(id, userId, "STATUS_CHANGED", oldStatus, newStatus,
            "Deviation status updated");

        return mapToResponse(deviation);
    }

    private DeviationResponse mapToResponse(Deviation deviation) {
        DeviationResponse response = new DeviationResponse();
        response.setDeviationId(deviation.getDeviationId());
        response.setInitiatorId(deviation.getInitiatorId());
        response.setDeviationType(deviation.getDeviationType());
        response.setStatus(deviation.getStatus());
        response.setCustomerContext(deviation.getCustomerContext());
        response.setBusinessJustification(deviation.getBusinessJustification());
        response.setAiJustification(deviation.getAiJustification());
        response.setAiRiskScore(deviation.getAiRiskScore());
        response.setAiRiskExplanation(deviation.getAiRiskExplanation());
        response.setAiPolicyMatch(deviation.getAiPolicyMatch());
        response.setIsAiOverridden(deviation.getIsAiOverridden());
        response.setAiOverrideReason(deviation.getAiOverrideReason());
        response.setCreatedAt(deviation.getCreatedAt());
        response.setUpdatedAt(deviation.getUpdatedAt());

        if (deviation.getInitiator() != null) {
            response.setInitiatorName(deviation.getInitiator().getName());
        }

        return response;
    }
}