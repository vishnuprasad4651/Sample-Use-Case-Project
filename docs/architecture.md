# Architecture Diagrams
## AI-Assisted Service Deviation & Approval Workflow

---

## 1. Application Architecture Diagram

```mermaid
flowchart TB
    subgraph Client_Layer ["Frontend - React.js"]
        UI[User Interface]
        Dashboard[Dashboard Component]
        InitiationForm[Deviation Initiation Form]
        ApprovalView[Approval View]
        AIActionPanel[AI Action Panel]
    end

    subgraph API_Gateway ["API Layer - Spring Boot"]
        REST_API[REST Controllers]
        Auth_Service[Authentication Service]
    end

    subgraph Business_Logic ["Business Logic Layer"]
        Deviation_Service[Deviation Service]
        AI_Service[AI Assistant Service]
        Approval_Service[Approval Service]
        Audit_Service[Audit Service]
    end

    subgraph Data_Access ["Data Access Layer"]
        Deviation_Repo[Deviation Repository]
        User_Repo[User Repository]
        Approval_Repo[Approval Repository]
        Audit_Repo[Audit Repository]
    end

    subgraph External ["External Systems"]
        GenAI_Lab[GenAI Lab<br/>https://genailab.tcs.in]
    end

    UI --> Dashboard
    UI --> InitiationForm
    UI --> ApprovalView
    InitiationForm --> AIActionPanel

    Dashboard --> REST_API
    InitiationForm --> REST_API
    ApprovalView --> REST_API

    REST_API --> Deviation_Service
    REST_API --> Auth_Service

    Deviation_Service --> Approval_Service
    Deviation_Service --> AI_Service
    Deviation_Service --> Audit_Service

    AI_Service -->|HTTP| GenAI_Lab

    Deviation_Service --> Deviation_Repo
    Approval_Service --> Approval_Repo
    Audit_Service --> Audit_Repo
    Auth_Service --> User_Repo
```

---

## 2. Technical Architecture Diagram

```mermaid
flowchart TB
    subgraph Frontend_Tier ["Frontend Tier - React.js"]
        React[React 18.x]
        Axios[Axios HTTP Client]
        JWT[JWT Auth Token]
        State[React Context/State]
    end

    subgraph Backend_Tier ["Backend Tier - Java Spring Boot"]
        Spring_Boot[Spring Boot 3.x]
        Spring_Security[Spring Security]
        Spring_Data[Spring Data JPA]
        Controllers[REST Controllers]
        Services[Business Services]
        Entities[JPA Entities]
    end

    subgraph Database_Tier ["Database Tier - PostgreSQL"]
        PG[(PostgreSQL 15)]
        Connection_Pool[HikariCP]
        DB_Schema[(schema.sql)]
    end

    subgraph AI_Integration ["AI Integration Tier"]
        GenAI_Endpoint[GenAI Lab Endpoint]
        GPT_Model[azure/genailab-maas-gpt-4.1]
        AI_Prompts[Prompt Templates]
    end

    subgraph Security ["Security Layer"]
        SSL[SSL/TLS]
        RBAC[Role-Based Access Control]
        JWT_Verify[JWT Verification]
    end

    React -->|HTTP+JSON| Axios
    Axios -->|HTTPS| Spring_Boot
    Axios -->|Bearer Token| JWT

    Spring_Boot --> Spring_Security
    Spring_Boot --> Spring_Data
    Spring_Boot --> Controllers
    Controllers --> Services
    Services --> Entities

    Spring_Data --> Connection_Pool
    Connection_Pool -->|JDBC| PG

    Services -->|HTTP| GenAI_Endpoint
    GenAI_Endpoint --> GPT_Model

    SSL --> Frontend_Tier
    SSL --> Backend_Tier
    RBAC --> Spring_Security
    JWT_Verify --> Spring_Security
```

---

## 3. Component Architecture Diagram

```mermaid
classDiagram
    class User {
        +Long userId
        +String name
        +String role
        +String email
    }

    class Deviation {
        +Long deviationId
        +Long initiatorId
        +String type
        +String status
        +String customerContext
        +String businessJustification
        +String aiRiskScore
        +String aiPolicyMatch
        +LocalDateTime createdAt
        +LocalDateTime updatedAt
    }

    class Approval {
        +Long approvalId
        +Long deviationId
        +Long approverId
        +String stage
        +String decision
        +String comments
        +LocalDateTime timestamp
    }

    class AuditLog {
        +Long logId
        +Long deviationId
        +String actionType
        +Long actorId
        +String details
        +LocalDateTime timestamp
    }

    User "1" --> "*" Deviation
    Deviation "1" --> "*" Approval
    Deviation "1" --> "*" AuditLog
```

---

## 4. Workflow Sequence Diagram

```mermaid
sequenceDiagram
    participant Initiator
    participant Frontend
    participant Backend
    participant Database
    participant GenAI

    Initiator->>Frontend: Fills Deviation Form
    Frontend->>Backend: POST /api/deviations
    Backend->>Database: Save Deviation (DRAFT)
    Backend->>Database: Create Audit Log

    Frontend->>Backend: POST /api/deviations/{id}/ai-justification
    Backend->>GenAI: Call GPT-4.1 for justification
    GenAI-->>Backend: Return AI justification
    Backend->>Database: Update Deviation with AI output
    Backend->>Frontend: Return AI justification

    Initiator->>Frontend: Submit Deviation
    Frontend->>Backend: PUT /api/deviations/{id}/submit
    Backend->>Database: Update Status to PENDING_OPS

    loop Approval Loop
        Backend->>Frontend: Get Deviations for Approver
        Frontend->>Backend: GET /api/deviations?status=PENDING_OPS
        Backend->>Database: Query Deviations
        Database-->>Backend: Return Deviations

        Approver->>Frontend: Review and Approve/Reject
        Frontend->>Backend: PUT /api/deviations/{id}/approve
        Backend->>Database: Save Approval and Update Status
        Backend->>Database: Create Audit Log
    end

    Backend->>Initiator: Notify Status Change
```

---

## 5. Deployment Architecture

```mermaid
flowchart LR
    subgraph Production
        LB[Load Balancer]
        FE[React App - Nginx]
        BE[Spring Boot - Tomcat Cluster]
        DB[(PostgreSQL Primary)]
        AI[GenAI Lab External]
    end

    Users --> LB
    LB --> FE
    FE --> BE
    BE --> DB
    BE --> AI

    style LB fill:#f9f,stroke:#333
    style FE fill:#bbf,stroke:#333
    style BE fill:#bfb,stroke:#333
    style DB fill:#ff9,stroke:#333
    style AI fill:#fbb,stroke:#333
```