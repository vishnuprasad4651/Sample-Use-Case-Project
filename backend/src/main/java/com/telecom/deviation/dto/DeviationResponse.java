package com.telecom.deviation.dto;

import java.time.LocalDateTime;

public class DeviationResponse {
    private Long deviationId;
    private Long initiatorId;
    private String initiatorName;
    private String deviationType;
    private String status;
    private String customerContext;
    private String businessJustification;
    private String aiJustification;
    private String aiRiskScore;
    private String aiRiskExplanation;
    private String aiPolicyMatch;
    private Boolean isAiOverridden;
    private String aiOverrideReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getDeviationId() { return deviationId; }
    public void setDeviationId(Long deviationId) { this.deviationId = deviationId; }
    public Long getInitiatorId() { return initiatorId; }
    public void setInitiatorId(Long initiatorId) { this.initiatorId = initiatorId; }
    public String getInitiatorName() { return initiatorName; }
    public void setInitiatorName(String initiatorName) { this.initiatorName = initiatorName; }
    public String getDeviationType() { return deviationType; }
    public void setDeviationType(String deviationType) { this.deviationType = deviationType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCustomerContext() { return customerContext; }
    public void setCustomerContext(String customerContext) { this.customerContext = customerContext; }
    public String getBusinessJustification() { return businessJustification; }
    public void setBusinessJustification(String businessJustification) { this.businessJustification = businessJustification; }
    public String getAiJustification() { return aiJustification; }
    public void setAiJustification(String aiJustification) { this.aiJustification = aiJustification; }
    public String getAiRiskScore() { return aiRiskScore; }
    public void setAiRiskScore(String aiRiskScore) { this.aiRiskScore = aiRiskScore; }
    public String getAiRiskExplanation() { return aiRiskExplanation; }
    public void setAiRiskExplanation(String aiRiskExplanation) { this.aiRiskExplanation = aiRiskExplanation; }
    public String getAiPolicyMatch() { return aiPolicyMatch; }
    public void setAiPolicyMatch(String aiPolicyMatch) { this.aiPolicyMatch = aiPolicyMatch; }
    public Boolean getIsAiOverridden() { return isAiOverridden; }
    public void setIsAiOverridden(Boolean isAiOverridden) { this.isAiOverridden = isAiOverridden; }
    public String getAiOverrideReason() { return aiOverrideReason; }
    public void setAiOverrideReason(String aiOverrideReason) { this.aiOverrideReason = aiOverrideReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}