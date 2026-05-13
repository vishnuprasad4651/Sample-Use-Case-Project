package com.telecom.deviation.dto;

public class DeviationRequest {
    private Long initiatorId;
    private String deviationType;
    private String customerContext;
    private String businessJustification;

    public Long getInitiatorId() { return initiatorId; }
    public void setInitiatorId(Long initiatorId) { this.initiatorId = initiatorId; }
    public String getDeviationType() { return deviationType; }
    public void setDeviationType(String deviationType) { this.deviationType = deviationType; }
    public String getCustomerContext() { return customerContext; }
    public void setCustomerContext(String customerContext) { this.customerContext = customerContext; }
    public String getBusinessJustification() { return businessJustification; }
    public void setBusinessJustification(String businessJustification) { this.businessJustification = businessJustification; }
}