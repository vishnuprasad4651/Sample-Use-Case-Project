package com.telecom.deviation.dto;

import java.time.LocalDateTime;

public class ApprovalResponse {
    private Long approvalId;
    private Long deviationId;
    private Long approverId;
    private String approverName;
    private String stage;
    private String decision;
    private String comments;
    private Boolean isAiOverridden;
    private String aiOverrideReason;
    private LocalDateTime createdAt;

    public Long getApprovalId() { return approvalId; }
    public void setApprovalId(Long approvalId) { this.approvalId = approvalId; }
    public Long getDeviationId() { return deviationId; }
    public void setDeviationId(Long deviationId) { this.deviationId = deviationId; }
    public Long getApproverId() { return approverId; }
    public void setApproverId(Long approverId) { this.approverId = approverId; }
    public String getApproverName() { return approverName; }
    public void setApproverName(String approverName) { this.approverName = approverName; }
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
}