-- ============================================================================
-- AI-Assisted Service Deviation & Approval Workflow
-- PostgreSQL Database Schema
-- ============================================================================

-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS audit_logs CASCADE;
DROP TABLE IF EXISTS approvals CASCADE;
DROP TABLE IF EXISTS deviations CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================================================
-- USERS TABLE
-- ============================================================================
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index for role-based queries
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_email ON users(email);

-- Insert default users (password: Password123!)
INSERT INTO users (name, role, email, password_hash) VALUES
    ('John Initiator', 'INITIATOR', 'john.initiator@telecom.com', '$2a$10$ABC123xyz'),
    ('Alice Ops', 'OPS_APPROVER', 'alice.ops@telecom.com', '$2a$10$DEF456uvw'),
    ('Bob Finance', 'FINANCE_APPROVER', 'bob.finance@telecom.com', '$2a$10$GHI789rst'),
    ('Charlie Network', 'NETWORK_APPROVER', 'charlie.network@telecom.com', '$2a$10$JKL012qrs'),
    ('Diana Compliance', 'COMPLIANCE_APPROVER', 'diana.compliance@telecom.com', '$2a$10$MNO345tuv');

-- ============================================================================
-- DEVIATIONS TABLE
-- ============================================================================
CREATE TABLE deviations (
    deviation_id BIGSERIAL PRIMARY KEY,
    initiator_id BIGINT NOT NULL REFERENCES users(user_id),
    deviation_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    customer_context TEXT NOT NULL,
    business_justification TEXT,
    ai_justification TEXT,
    ai_risk_score VARCHAR(20),
    ai_risk_explanation TEXT,
    ai_policy_match TEXT,
    is_ai_overridden BOOLEAN DEFAULT FALSE,
    ai_override_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for common queries
CREATE INDEX idx_deviations_initiator ON deviations(initiator_id);
CREATE INDEX idx_deviations_status ON deviations(status);
CREATE INDEX idx_deviations_type ON deviations(deviation_type);
CREATE INDEX idx_deviations_created ON deviations(created_at);

-- ============================================================================
-- APPROVALS TABLE
-- ============================================================================
CREATE TABLE approvals (
    approval_id BIGSERIAL PRIMARY KEY,
    deviation_id BIGINT NOT NULL REFERENCES deviations(deviation_id) ON DELETE CASCADE,
    approver_id BIGINT NOT NULL REFERENCES users(user_id),
    stage VARCHAR(50) NOT NULL,
    decision VARCHAR(20),
    comments TEXT,
    is_ai_overridden BOOLEAN DEFAULT FALSE,
    ai_override_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_approvals_deviation ON approvals(deviation_id);
CREATE INDEX idx_approvals_approver ON approvals(approver_id);
CREATE INDEX idx_approvals_stage ON approvals(stage);

-- ============================================================================
-- AUDIT_LOGS TABLE
-- ============================================================================
CREATE TABLE audit_logs (
    log_id BIGSERIAL PRIMARY KEY,
    deviation_id BIGINT REFERENCES deviations(deviation_id) ON DELETE SET NULL,
    action_type VARCHAR(50) NOT NULL,
    actor_id BIGINT NOT NULL REFERENCES users(user_id),
    previous_value TEXT,
    new_value TEXT,
    details TEXT,
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_audit_deviation ON audit_logs(deviation_id);
CREATE INDEX idx_audit_actor ON audit_logs(actor_id);
CREATE INDEX idx_audit_action ON audit_logs(action_type);
CREATE INDEX idx_audit_created ON audit_logs(created_at);

-- ============================================================================
-- CONSTRAINTS
-- ============================================================================

-- Add CHECK constraints for valid status values
ALTER TABLE deviations
    ADD CONSTRAINT chk_deviation_status
    CHECK (status IN ('DRAFT', 'PENDING_OPS', 'PENDING_FINANCE', 'PENDING_COMPLIANCE', 'APPROVED', 'REJECTED', 'CLOSED', 'ARCHIVED'));

ALTER TABLE deviations
    ADD CONSTRAINT chk_deviation_type
    CHECK (deviation_type IN ('BILLING_CREDIT', 'BANDWIDTH_INCREASE', 'SLA_WAIVER', 'CONTENT_EXCEPTION', 'KYC_DEFERRAL'));

ALTER TABLE approvals
    ADD CONSTRAINT chk_approval_decision
    CHECK (decision IN ('APPROVED', 'REJECTED'));

ALTER TABLE approvals
    ADD CONSTRAINT chk_approval_stage
    CHECK (stage IN ('OPS', 'FINANCE', 'COMPLIANCE'));

-- ============================================================================
-- FUNCTIONS AND TRIGGERS
-- ============================================================================

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Trigger for users table
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Trigger for deviations table
CREATE TRIGGER update_deviations_updated_at
    BEFORE UPDATE ON deviations
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Trigger for approvals table
CREATE TRIGGER update_approvals_updated_at
    BEFORE UPDATE ON approvals
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- ============================================================================
-- VIEWS
-- ============================================================================

-- View for dashboard: deviations with initiator and current status
CREATE OR REPLACE VIEW v_deviation_dashboard AS
SELECT
    d.deviation_id,
    d.deviation_type,
    d.status,
    d.customer_context,
    d.ai_risk_score,
    d.created_at,
    d.updated_at,
    u.name AS initiator_name,
    u.email AS initiator_email,
    (SELECT COUNT(*) FROM approvals a WHERE a.deviation_id = d.deviation_id) AS approval_count
FROM deviations d
JOIN users u ON d.initiator_id = u.user_id;

-- View for audit trail: complete history of a deviation
CREATE OR REPLACE VIEW v_audit_trail AS
SELECT
    al.log_id,
    al.deviation_id,
    al.action_type,
    al.actor_id,
    u.name AS actor_name,
    u.role AS actor_role,
    al.previous_value,
    al.new_value,
    al.details,
    al.created_at
FROM audit_logs al
JOIN users u ON al.actor_id = u.user_id
ORDER BY al.created_at DESC;

-- ============================================================================
-- COMMENTS
-- ============================================================================

COMMENT ON TABLE users IS 'Stores user information and roles';
COMMENT ON TABLE deviations IS 'Main deviation request records';
COMMENT ON TABLE approvals IS 'Approval records for deviation workflow';
COMMENT ON TABLE audit_logs IS 'Audit trail for all system actions';

-- ============================================================================
-- END OF SCHEMA
-- ============================================================================