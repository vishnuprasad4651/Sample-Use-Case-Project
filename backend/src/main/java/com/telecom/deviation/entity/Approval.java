package com.telecom.deviation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approvals")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long approvalId;

    @Column(name = "deviation_id", nullable = false)
    private Long deviationId;

    @Column(name = "approver_id", nullable = false)
    private Long approverId;

    @Column(nullable = false)
    private String stage;

    private String decision;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(name = "is_ai_overridden")
    private Boolean isAiOverridden = false;

    @Column(name = "ai_override_reason", columnDefinition = "TEXT")
    private String aiOverrideReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deviation_id", insertable = false, updatable = false)
    private Deviation deviation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", insertable = false, updatable = false)
    private User approver;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getApprovalId() { return approvalId; }
    public void setApprovalId(Long approvalId) { this.approvalId = approvalId; }
    public Long getDeviationId() { return deviationId; }
    public void setDeviationId(Long deviationId) { this.deviationId = deviationId; }
    public Long getApproverId() { return approverId; }
    public void setApproverId(Long approverId) { this.approverId = approverId; }
    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }
    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public Boolean getIsAiOverridden() { return isAiOverridden; }
    public void setIsAiOverridden(Boolean isAiOverridden) { this.isAiOverridden = isAiOverridden; }
    public String getAiOverrideReason() { return aiOverrideReason; }
    public void setAiOverrideReason(String aiOverrideReason) { this.aiOverrideReason = aiOverrideReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Deviation getDeviation() { return deviation; }
    public void setDeviation(Deviation deviation) { this.deviation = deviation; }
    public User getApprover() { return approver; }
    public void setApprover(User approver) { this.approver = approver; }
}