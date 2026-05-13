# IMPLEMENTATION GUIDE: AI-Assisted Service Deviation & Approval Workflow

## 1. Technology Stack
* **Frontend:** React.js (HTML5/CSS3/JavaScript) - *Note: Streamlit and Gradio are strictly prohibited.*
* **Backend:** Java Spring Boot (REST API architecture)
* **Database:** PostgreSQL (Strict RDBMS requirement; no JSON storage)
* **AI Integration:** Integration via REST calls to the provided GenAI Lab endpoint (`https://genailab.tcs.in`).
* **Testing:** REST Assured for API testing, Selenium for UI automation.

## 2. Database Schema Design (PostgreSQL)
The AI must generate SQL DDL scripts for the following logical entities:

* **Users Table:** `user_id`, `name`, `role` (Initiator, Ops, Finance, Network, Compliance), `email`.
* **Deviations Table:** `deviation_id`, `initiator_id`, `type` (Billing, SLA, Bandwidth, etc.), `status` (Draft, Pending_Ops, Pending_Finance, Approved, Rejected), `customer_context`, `business_justification` (AI generated/user edited), `created_at`, `updated_at`.
* **Approvals Table:** `approval_id`, `deviation_id`, `approver_id`, `stage`, `decision` (Approve/Reject), `comments`, `timestamp`.
* **Audit_Logs Table:** `log_id`, `deviation_id`, `action_type` (Initiation, AI_Generation, Human_Override, Approval), `actor_id`, `details`, `timestamp`.

## 3. Backend Implementation (Java Spring Boot)
The AI must generate controllers, services, and repository layers for the following core APIs:

* `POST /api/deviations`: Create a new deviation request.
* `GET /api/deviations/{id}`: Fetch deviation details and current workflow state.
* `PUT /api/deviations/{id}/approve`: Submit an approval or rejection decision.
* `GET /api/audit-logs/{deviation_id}`: Retrieve full traceability for compliance.

## 4. AI Agent Integration Logic
The backend must include an `AIAssistantService` that handles HTTP requests to the LLM. 
* **Model Config:** `azure/genailab-maas-gpt-4.1`
* **Base URL:** `https://genailab.tcs.in`
* **Prompts to Implement:**
    1. **Justification Generator:** `Given the deviation type {type} and customer context {context}, generate a professional, compliance-friendly business justification.`
    2. **Policy Matcher:** `Identify standard telecom policies and contractual clauses relevant to a {type} deviation.`
    3. **Risk Scorer:** `Analyze the following deviation request and provide a risk score (Low/Medium/High) with a 2-sentence explanation.`

## 5. Frontend Implementation (React)
The AI must generate the following UI components:

* **Dashboard View:** A kanban or data table view showing deviations categorized by status (Pending My Approval, Drafts, Completed).
* **Initiation Form:** A dynamic form where users select the deviation type and input basic customer context.
* **AI Action Panel:** A dedicated UI section within the form that features a "Generate Justification with AI" button. This must populate a text area that the user can manually override (Human-in-the-loop requirement).
* **Approval View:** A screen for approvers showing the deviation details, AI risk score, AI policy match, and buttons to "Approve" or "Reject" with mandatory comment fields.

## 6. Execution Steps for the AI Tool
*(Instruct the AI to execute these steps sequentially)*

1. **Step 1:** Generate the `schema.sql` file containing all database table creation scripts.
2. **Step 2:** Scaffold the Java Spring Boot backend, including `application.properties` configured for PostgreSQL.
3. **Step 3:** Implement JPA Entities and Spring Data Repositories for all tables.
4. **Step 4:** Build the `AIAssistantService` to handle LLM calls (ensure SSL verification is bypassed if required by local environment constraints).
5. **Step 5:** Implement REST Controllers for the workflows.
6. **Step 6:** Scaffold the React frontend and integrate Axios for API calls.
7. **Step 7:** Build the UI components and connect them to the backend endpoints.
8. **Step 8:** Generate automated functional test scripts (REST Assured for APIs, Selenium for UI) and output a traceability matrix mapping features to tests.
