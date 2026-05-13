package com.telecom.deviation.dto;

public class ApprovalRequest {
    private Long approverId;
    private String decision;
    private String comments;
    private Boolean isAiOverridden;
    private String aiOverrideReason;

    public Long getApproverId() { return approverId; }
    public void setApproverId(Long approverId) { this.approverId = approverId; }
    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public Boolean getIsAiOverridden() { return isAiOverridden; }
    public void setIsAiOverridden(Boolean isAiOverridden) { this.isAiOverridden = isAiOverridden; }
    public String getAiOverrideReason() { return aiOverrideReason; }
    public void setAiOverrideReason(String aiOverrideReason) { this.aiOverrideReason = aiOverrideReason; }
}