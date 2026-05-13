# SPECIFICATION: AI-Assisted Service Deviation & Approval Workflow

## 1. System Overview
Build a web-based operational deviation initiation and approval system for the Telecom & Media sector. The system must enforce role-based approvals, integrate AI for decision support, and maintain strict audit logs. 

## 2. Core Features & Functionality

### A. User Roles & Access Control
* **Initiators:** Telecom/Media Operations Managers, Network Managers, Account Managers.
* **Approvers/Reviewers:** Operations, Finance, Network, and Compliance teams.
* **Action:** Implement strict Role-Based Access Control (RBAC) ensuring compliance steps cannot be bypassed.

### B. Deviation Initiation Module
* Provide a form for Initiators to trigger specific deviation types:
    * Billing credits exceeding standard limits.
    * Temporary data/bandwidth increases.
    * Limited SLA waivers.
    * Content access exceptions.
    * Short-term KYC deferrals.

### C. AI Agent Integrations (Crucial for Evaluation)
* **AI Justification Generator:** Auto-generate compliance-friendly business justifications based on minimal user input.
* **Policy Matcher:** AI must reference relevant internal policies or contractual clauses based on the requested deviation type.
* **Context Summarizer:** AI compiles a summary of the customer and service context for the decision-makers.
* **Risk Scoring:** AI provides a risk score or recommendation for the requested deviation.

### D. Multi-Level Approval Workflow
* Implement state transitions (e.g., Draft -> Pending Ops Approval -> Pending Finance -> Approved/Rejected).
* Enforce escalation rules and approval hierarchies.
* Include "Human-in-the-loop" controls allowing overrides of AI recommendations.

### E. Compliance & Audit Logging
* Systematically log all actions, AI recommendations, and human decisions.
* Produce audit-ready records exportable for internal/external regulatory reviews.

## 3. Technical Constraints
* **Database:** Must use a Relational Database Management System (RDBMS) (e.g., PostgreSQL, MySQL). Do not use JSON files for data storage.
* **Frontend UI:** Must be a custom UI (e.g., React, HTML/JS). Streamlit and Gradio are strictly prohibited.
