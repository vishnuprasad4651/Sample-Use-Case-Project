package com.telecom.deviation.dto;

public class AIResponse {
    private String justification;
    private String policyMatch;
    private String riskScore;
    private String riskExplanation;

    public AIResponse() {}

    public AIResponse(String justification, String policyMatch, String riskScore, String riskExplanation) {
        this.justification = justification;
        this.policyMatch = policyMatch;
        this.riskScore = riskScore;
        this.riskExplanation = riskExplanation;
    }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public String getPolicyMatch() { return policyMatch; }
    public void setPolicyMatch(String policyMatch) { this.policyMatch = policyMatch; }
    public String getRiskScore() { return riskScore; }
    public void setRiskScore(String riskScore) { this.riskScore = riskScore; }
    public String getRiskExplanation() { return riskExplanation; }
    public void setRiskExplanation(String riskExplanation) { this.riskExplanation = riskExplanation; }
}