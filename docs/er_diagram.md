# Entity-Relationship (ER) Diagram
## AI-Assisted Service Deviation & Approval Workflow

```mermaid
erDiagram
    USERS ||--o{ DEVIATIONS : "creates"
    USERS ||--o{ APPROVALS : "approves"
    USERS ||--o{ AUDIT_LOGS : "performs"
    DEVIATIONS ||--o{ APPROVALS : "has"
    DEVIATIONS ||--o{ AUDIT_LOGS : "tracked_by"

    USERS {
        bigint user_id PK
        varchar name
        varchar role
        varchar email UK
        varchar password_hash
        boolean is_active
        timestamp created_at
        timestamp updated_at
    }

    DEVIATIONS {
        bigint deviation_id PK
        bigint initiator_id FK
        varchar deviation_type
        varchar status
        text customer_context
        text business_justification
        text ai_justification
        varchar ai_risk_score
        text ai_risk_explanation
        text ai_policy_match
        boolean is_ai_overridden
        text ai_override_reason
        timestamp created_at
        timestamp updated_at
    }

    APPROVALS {
        bigint approval_id PK
        bigint deviation_id FK
        bigint approver_id FK
        varchar stage
        varchar decision
        text comments
        boolean is_ai_overridden
        text ai_override_reason
        timestamp created_at
        timestamp updated_at
    }

    AUDIT_LOGS {
        bigint log_id PK
        bigint deviation_id FK
        varchar action_type
        bigint actor_id FK
        text previous_value
        text new_value
        text details
        varchar ip_address
        timestamp created_at
    }
```

## Relationship Cardinalities

| Relationship | Type | Description |
|-------------|------|-------------|
| USERS → DEVIATIONS | One-to-Many | A user can create multiple deviations |
| USERS → APPROVALS | One-to-Many | An approver can approve multiple deviations |
| DEVIATIONS → APPROVALS | One-to-Many | A deviation can have multiple approval stages |
| USERS → AUDIT_LOGS | One-to-Many | A user can perform multiple actions logged |
| DEVIATIONS → AUDIT_LOGS | One-to-Many | A deviation has multiple audit entries |