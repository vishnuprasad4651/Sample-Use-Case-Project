import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function ApprovalView({ currentUser }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const [deviation, setDeviation] = useState(null);
  const [loading, setLoading] = useState(true);
  const [comments, setComments] = useState('');
  const [isOverridingAI, setIsOverridingAI] = useState(false);
  const [overrideReason, setOverrideReason] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    loadDeviation();
  }, [id]);

  const loadDeviation = () => {
    setLoading(true);
    setTimeout(() => {
      setDeviation({
        deviationId: id,
        deviationType: 'BANDWIDTH_INCREASE',
        status: 'PENDING_OPS',
        customerContext: 'Enterprise client needs temporary bandwidth boost for Q2 business review meeting. Client is a strategic partner requiring premium service level.',
        businessJustification: 'Customer requires additional bandwidth for critical business operations during peak season.',
        aiJustification: 'This deviation aligns with customer retention objectives and maintains service quality standards.',
        aiRiskScore: 'LOW',
        aiRiskExplanation: 'Standard review required. No significant compliance or financial risks identified.',
        aiPolicyMatch: '• Standard Telecom Service Agreement (Section 4.2)\n• Enterprise Service Tier Policy (Section 2.3)',
        initiatorName: 'John Initiator',
        createdAt: '2026-05-13'
      });
      setLoading(false);
    }, 500);
  };

  const handleApproval = async (decision) => {
    if (!comments.trim()) {
      alert('Please provide comments for your decision');
      return;
    }

    if (isOverridingAI && !overrideReason.trim()) {
      alert('Please provide a reason for overriding the AI recommendation');
      return;
    }

    setIsSubmitting(true);
    setTimeout(() => {
      alert(`Deviation ${decision} successfully!`);
      setIsSubmitting(false);
      navigate('/');
    }, 1000);
  };

  if (loading) {
    return <div className="loading">Loading deviation details...</div>;
  }

  if (!deviation) {
    return <div className="error">Deviation not found</div>;
  }

  return (
    <div>
      <div style={{ marginBottom: '2rem' }}>
        <button onClick={() => navigate('/')} style={{ background: 'none', border: 'none', color: '#667eea', cursor: 'pointer', marginBottom: '1rem' }}>
          ← Back to Dashboard
        </button>
        <h2>✅ Review Deviation #{id}</h2>
      </div>

      <div className="card">
        <div className="card-header">
          <span className="card-title">{deviation.deviationType.replace('_', ' ')}</span>
          <span className={`badge ${deviation.status.toLowerCase().replace('_', '_')}`}>
            {deviation.status.replace('_', ' ')}
          </span>
        </div>
        <p style={{ color: '#636e72', marginBottom: '1rem' }}>
          <strong>Initiator:</strong> {deviation.initiatorName} | <strong>Created:</strong> {deviation.createdAt}
        </p>
      </div>

      <div className="card">
        <h3 style={{ marginBottom: '1rem' }}>📋 Customer Context</h3>
        <p>{deviation.customerContext}</p>
      </div>

      <div className="card">
        <h3 style={{ marginBottom: '1rem' }}>📝 Business Justification</h3>
        <p>{deviation.businessJustification}</p>
      </div>

      <div className="ai-panel">
        <h3>🤖 AI Analysis</h3>
        
        <div style={{ background: 'white', padding: '1rem', borderRadius: '8px', marginBottom: '1rem' }}>
          <strong>📋 AI Generated Justification:</strong>
          <p style={{ marginTop: '0.5rem', color: '#636e72' }}>{deviation.aiJustification}</p>
        </div>

        <div style={{ background: 'white', padding: '1rem', borderRadius: '8px', marginBottom: '1rem' }}>
          <strong>📜 Policy Match:</strong>
          <pre style={{ marginTop: '0.5rem', color: '#636e72', whiteSpace: 'pre-wrap' }}>{deviation.aiPolicyMatch}</pre>
        </div>

        <div>
          <strong>⚠️ AI Risk Score: </strong>
          <span className={`risk-score ${deviation.aiRiskScore.toLowerCase()}`}>
            {deviation.aiRiskScore}
          </span>
          <p style={{ marginTop: '0.5rem', color: '#636e72' }}>{deviation.aiRiskExplanation}</p>
        </div>

        <div style={{ marginTop: '1rem', padding: '1rem', background: '#fff3cd', borderRadius: '8px' }}>
          <label style={{ display: 'flex', alignItems: 'center', cursor: 'pointer' }}>
            <input
              type="checkbox"
              checked={isOverridingAI}
              onChange={(e) => setIsOverridingAI(e.target.checked)}
              style={{ marginRight: '0.5rem' }}
            />
            <strong>Override AI Recommendation</strong>
          </label>
          {isOverridingAI && (
            <div style={{ marginTop: '1rem' }}>
              <label>Reason for Override:</label>
              <textarea
                value={overrideReason}
                onChange={(e) => setOverrideReason(e.target.value)}
                placeholder="Explain why you are overriding the AI recommendation..."
                style={{ minHeight: '80px' }}
              />
            </div>
          )}
        </div>
      </div>

      <div className="card">
        <h3 style={{ marginBottom: '1rem' }}>💬 Approval Decision</h3>
        <div className="form-group">
          <label>Comments (Required) *</label>
          <textarea
            value={comments}
            onChange={(e) => setComments(e.target.value)}
            placeholder="Provide your approval or rejection comments..."
            required
          />
        </div>
      </div>

      <div className="actions">
        <button
          className="btn btn-success"
          onClick={() => handleApproval('Approved')}
          disabled={isSubmitting}
        >
          {isSubmitting ? 'Submitting...' : '✓ Approve'}
        </button>
        <button
          className="btn btn-danger"
          onClick={() => handleApproval('Rejected')}
          disabled={isSubmitting}
        >
          ✗ Reject
        </button>
        <button
          className="btn btn-secondary"
          onClick={() => navigate('/')}
        >
          Cancel
        </button>
      </div>
    </div>
  );
}

export default ApprovalView;