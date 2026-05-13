package com.telecom.deviation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deviations")
public class Deviation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviation_id")
    private Long deviationId;

    @Column(name = "initiator_id", nullable = false)
    private Long initiatorId;

    @Column(name = "deviation_type", nullable = false)
    private String deviationType;

    @Column(nullable = false)
    private String status = "DRAFT";

    @Column(name = "customer_context", nullable = false, columnDefinition = "TEXT")
    private String customerContext;

    @Column(name = "business_justification", columnDefinition = "TEXT")
    private String businessJustification;

    @Column(name = "ai_justification", columnDefinition = "TEXT")
    private String aiJustification;

    @Column(name = "ai_risk_score")
    private String aiRiskScore;

    @Column(name = "ai_risk_explanation", columnDefinition = "TEXT")
    private String aiRiskExplanation;

    @Column(name = "ai_policy_match", columnDefinition = "TEXT")
    private String aiPolicyMatch;

    @Column(name = "is_ai_overridden")
    private Boolean isAiOverridden = false;

    @Column(name = "ai_override_reason", columnDefinition = "TEXT")
    private String aiOverrideReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", insertable = false, updatable = false)
    private User initiator;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getDeviationId() { return deviationId; }
    public void setDeviationId(Long deviationId) { this.deviationId = deviationId; }
    public Long getInitiatorId() { return initiatorId; }
    public void setInitiatorId(Long initiatorId) { this.initiatorId = initiatorId; }
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
    public User getInitiator() { return initiator; }
    public void setInitiator(User initiator) { this.initiator = initiator; }
}