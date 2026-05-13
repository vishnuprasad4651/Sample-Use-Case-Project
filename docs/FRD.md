# Functional Requirements Document (FRD)
## AI-Assisted Service Deviation & Approval Workflow

**Version:** 1.0
**Date:** 2026-05-13
**Author:** Lead Business Analyst

---

## 1. Business Objectives

### 1.1 Primary Goals
- Enable Telecom & Media Operations teams to initiate and track service deviations efficiently
- Provide AI-powered decision support for deviation justifications and risk assessment
- Ensure regulatory compliance through multi-level approval workflows
- Maintain complete audit trail for all deviation actions

### 1.2 Key Performance Indicators
- Reduce deviation processing time by 40% through AI assistance
- Achieve 100% compliance with approval hierarchy requirements
- Maintain zero unauthorized deviations through RBAC enforcement

---

## 2. Actors & Roles

### 2.1 User Roles

| Role | Description | Permissions |
|------|-------------|-------------|
| **Initiator** | Operations Managers, Network Managers, Account Managers | Create deviations, edit drafts, view own submissions |
| **Ops Approver** | Operations Team Lead | Approve/Reject deviation requests, override AI recommendations |
| **Finance Approver** | Finance Team | Approve/Reject billing-related deviations |
| **Network Approver** | Network Team | Approve/Reject bandwidth/network-related deviations |
| **Compliance Approver** | Compliance Officer | Final approval, audit review, policy enforcement |
| **System Admin** | IT Administrator | User management, system configuration |

### 2.2 Role Hierarchy
```
Compliance Approver (Final)
    ↑
Finance Approver
    ↑
Network Approver
    ↑
Ops Approver
    ↑
Initiator
```

---

## 3. Deviation Triggers

### 3.1 Deviation Types

| Type | Description | Required Approvers |
|------|-------------|-------------------|
| **BILLING_CREDIT** | Billing credits exceeding standard limits | Ops → Finance → Compliance |
| **BANDWIDTH_INCREASE** | Temporary data/bandwidth increases | Ops → Network → Compliance |
| **SLA_WAIVER** | Limited SLA waivers | Ops → Network → Compliance |
| **CONTENT_EXCEPTION** | Content access exceptions | Ops → Compliance |
| **KYC_DEFERRAL** | Short-term KYC deferrals | Ops → Compliance |

---

## 4. Workflow States

### 4.1 State Diagram

```
DRAFT → PENDING_OPS → PENDING_FINANCE → PENDING_COMPLIANCE → APPROVED
                                    ↓
                                REJECTED
```

### 4.2 State Definitions

| State | Description | Next State |
|-------|-------------|------------|
| **DRAFT** | Initial creation, not submitted | PENDING_OPS, ARCHIVED |
| **PENDING_OPS** | Awaiting Operations approval | PENDING_FINANCE, REJECTED, APPROVED (if no finance needed) |
| **PENDING_FINANCE** | Awaiting Finance approval | PENDING_COMPLIANCE, REJECTED |
| **PENDING_COMPLIANCE** | Awaiting Compliance approval | APPROVED, REJECTED |
| **APPROVED** | Final approval granted | CLOSED |
| **REJECTED** | Request rejected | DRAFT (resubmit), ARCHIVED |
| **CLOSED** | Deviation completed | - |
| **ARCHIVED** | Withdrawn or cancelled | - |

### 4.3 State Transitions by Role

| Role | Can Transition |
|------|----------------|
| Initiator | DRAFT → PENDING_OPS, DRAFT → ARCHIVED |
| Ops Approver | PENDING_OPS → PENDING_FINANCE, PENDING_OPS → REJECTED, PENDING_OPS → APPROVED |
| Finance Approver | PENDING_FINANCE → PENDING_COMPLIANCE, PENDING_FINANCE → REJECTED |
| Compliance Approver | PENDING_COMPLIANCE → APPROVED, PENDING_COMPLIANCE → REJECTED |

---

## 5. AI Capabilities

### 5.1 AI Integration Points

| Feature | Trigger | Output |
|---------|---------|--------|
| **Justification Generator** | User clicks "Generate with AI" | Compliance-friendly business justification text |
| **Policy Matcher** | Auto-trigger on deviation creation | List of relevant policies and clauses |
| **Context Summarizer** | Auto-trigger on deviation creation | Customer/service context summary |
| **Risk Scorer** | Auto-trigger on deviation creation | Risk score (Low/Medium/High) with explanation |

### 5.2 AI Prompt Templates

**Justification Generator:**
```
Given the deviation type {type} and customer context {context}, generate a professional, compliance-friendly business justification.
```

**Policy Matcher:**
```
Identify standard telecom policies and contractual clauses relevant to a {type} deviation.
```

**Risk Scorer:**
```
Analyze the following deviation request and provide a risk score (Low/Medium/High) with a 2-sentence explanation.
```

### 5.3 AI Model Configuration
- **Endpoint:** https://genailab.tcs.in
- **Model:** azure/genailab-maas-gpt-4.1

---

## 6. Human-in-the-Loop Controls

### 6.1 Override Capabilities
- Users can edit AI-generated justifications before submission
- Approvers can override AI risk scores with mandatory justification
- All overrides must be logged in audit trail

### 6.2 Mandatory Fields for Override
- Reason for override
- Approver confirmation checkbox
- Timestamp

---

## 7. Audit & Compliance

### 7.1 Audit Log Requirements

| Action | Logged | Details |
|--------|--------|---------|
| Deviation Created | Yes | Initiator, timestamp, initial state |
| AI Justification Generated | Yes | Prompt, response, user acceptance |
| Human Override | Yes | Original value, new value, reason |
| State Transition | Yes | From state, to state, actor, timestamp |
| Approval/Rejection | Yes | Decision, comments, approver |
| Viewed by Approver | Yes | Actor, timestamp |

### 7.2 Compliance Requirements
- All deviation requests must complete full approval chain
- No bypass of compliance step allowed
- Export capability for regulatory reviews
- Data retention: 7 years minimum

### 7.3 SLA Requirements

| Stage | Maximum Time |
|-------|--------------|
| DRAFT → PENDING_OPS | 24 hours |
| PENDING_OPS → PENDING_FINANCE | 48 hours |
| PENDING_FINANCE → PENDING_COMPLIANCE | 48 hours |
| PENDING_COMPLIANCE → APPROVED/REJECTED | 72 hours |

---

## 8. Exceptions

### 8.1 Allowed Exceptions
- Emergency deviations (flagged, requires post-approval)
- Bulk deviations (processed in batch)
- Delegated approvals (when primary approver unavailable)

### 8.2 Exception Handling
- All exceptions require documented justification
- Compliance team notified automatically
- Monthly exception report generated

---

## 9. Non-Functional Requirements

### 9.1 Performance
- Page load time: < 3 seconds
- API response time: < 2 seconds
- Concurrent users: 500+

### 9.2 Security
- JWT-based authentication
- Role-based access control (RBAC)
- SSL/TLS encryption
- Session timeout: 30 minutes

### 9.3 Reliability
- 99.9% uptime
- Automatic backup every 6 hours
- Recovery time objective: 4 hours