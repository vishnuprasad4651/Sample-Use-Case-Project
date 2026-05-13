# End-End Task Prompts: AI-Assisted Service Deviation & Approval Workflow

Here is your master list of prompts to execute the entire hackathon project from start to finish. These prompts are structured to directly satisfy all 8 deliverables and strict evaluation criteria.

**Instructions for use:** Open your Continue.dev chat, type `@` to attach your `SPEC.md` and `IMPLEMENTATION.md` files as context, and copy-paste these prompts one by one. Wait for the AI to completely finish generating the files for each step before moving on to the next one!

---

## 🛠️ Phase 1: Planning, Specs & Architecture (Deliverables 1, 2 & 3)

### Prompt 1: Functional Requirements & User Stories (Deliverable 1 & 2)
> "Act as a Lead Business Analyst. Based on the attached specifications, generate a comprehensive Functional Requirements Document (FRD) for the AI-Assisted Service Deviation & Approval Workflow. It must cover: business objectives, actors/roles, deviation triggers, workflow states, approval hierarchies, AI capabilities (risk scoring, policy interpretation, justification), human-in-the-loop controls, auditability, exceptions, SLAs, and compliance. Additionally, generate the User Stories as a Domain Specific Language (DSL) using YAML format, making them ready to be converted into code."

### Prompt 2: Architecture Diagrams (Deliverable 3)
> "Act as a Solutions Architect. Generate the Application Architecture and Technical Architecture diagrams for this system. Since you are text-based, generate the diagrams using **Mermaid.js** code syntax so I can render them. Include the React frontend, the Java Spring Boot backend, the PostgreSQL database, and the external API integration with the GenAI Lab endpoint. Ensure the architecture demonstrates high scalability and seamless integration."

---

## 🗄️ Phase 2: Database & Data Modeling (Deliverable 4)

### Prompt 3: RDBMS Database Design
> "Act as a Database Architect. Design the database for the operational deviation workflow. 
> **Strict Constraints:** You must use an RDBMS (PostgreSQL). Do NOT use JSON input or JSONB storage types for any data. 
> **Deliverables needed in your response:** > 1. A detailed Logical Model explaining the tables and relationships. 
> 2. An Entity-Relationship (ER) diagram using Mermaid.js syntax. 
> 3. The Physical Model: Complete SQL DDL scripts (`schema.sql`) to create the Users, Deviations, Approvals, and Audit_Logs tables with primary/foreign keys and appropriate data types."

---

## 💻 Phase 3: Software Development (Deliverable 5)

### Prompt 4: Backend Code Generation (Java Spring Boot)
> "Act as a Senior Backend Developer. Let's build the Java Spring Boot backend based on our PostgreSQL schema. Please generate the following production-quality code applying consistent design patterns:
> 1. The `application.properties` configuration.
> 2. The JPA Entities (`User`, `Deviation`, `Approval`, `AuditLog`).
> 3. The Spring Data Repositories.
> 4. An `AIAssistantService.java` class that handles the HTTP calls to `https://genailab.tcs.in` (model: `azure/genailab-maas-gpt-4.1`). Include the prompts for justification generation, policy matching, and risk scoring. 
> 5. The REST Controllers (`DeviationController`) implementing the end-to-end workflow states."

### Prompt 5: Frontend Code Generation (React)
> "Act as a Senior Frontend Developer. Now, let's build the user interface using React.js. 
> **Strict Constraint:** Do NOT use Streamlit or Gradio. Prioritize a highly user-centric, professional UX.
> Please generate the code for:
> 1. The main Dashboard displaying a Kanban or data table of deviations.
> 2. The Initiation Form (for Requestors) with a dedicated 'AI Action Panel' to trigger the AI justification.
> 3. The Approval View (for Reviewers) displaying the details, AI risk score, and Approve/Reject buttons with mandatory comments.
> *Provide the React component code using modern functional components and hooks.*"

---

## 🧪 Phase 4: Testing & Traceability (Deliverables 6 & 7)

### Prompt 6: Functional Tests & Traceability Matrix
> "Act as a Lead QA Engineer. I need to fulfill the testing deliverables.
> 1. Generate comprehensive Functional Test scripts (covering both positive workflows and negative/non-functional exceptions like bypassed compliance). Output this strictly in **CSV format** inside a code block so I can save it as an Excel file. Use columns: Test_ID, Scenario, Steps, Expected_Result.
> 2. Generate a Traceability Matrix mapping the business requirements from our FRD to these Test IDs. Output this strictly in **CSV format** inside a second code block."

---

## 📘 Phase 5: Documentation & End-User Guides (Deliverable 8)

### Prompt 7: End User Manual
> "Act as a Technical Writer. Create a comprehensive End User Manual for the Telecom & Media Operations teams using this system. Include a step-by-step help guide on how to:
> 1. Log in and navigate the dashboard.
> 2. Initiate a new service deviation.
> 3. Effectively use the AI Agent to generate justifications and policy matches.
> 4. Override AI recommendations (Human-in-the-loop).
> 5. Review and approve/reject workflows.
> Format the manual professionally using Markdown, indicating exactly where UI screen captures should be placed by the development team."

---

## 💡 Pro-Tips for Execution:
* **The "Continue" Command:** If the LLM stops generating code halfway through a long file, simply type **"Continue exactly where you left off"** in the chat, and it will keep writing.
* **Saving CSVs:** When it generates the CSV code blocks for Phase 4, simply copy the text, paste it into Notepad, save it as `tests.csv`, and then open that file with Excel to submit your deliverables.
