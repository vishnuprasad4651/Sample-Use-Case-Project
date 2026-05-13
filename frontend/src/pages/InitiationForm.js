import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function InitiationForm({ currentUser }) {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    deviationType: '',
    customerContext: '',
    businessJustification: ''
  });
  const [aiAnalysis, setAiAnalysis] = useState(null);
  const [isGeneratingAI, setIsGeneratingAI] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const deviationTypes = [
    'BILLING_CREDIT',
    'BANDWIDTH_INCREASE',
    'SLA_WAIVER',
    'CONTENT_EXCEPTION',
    'KYC_DEFERRAL'
  ];

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const generateAIAnalysis = async () => {
    if (!formData.deviationType || !formData.customerContext) {
      alert('Please fill in deviation type and customer context first');
      return;
    }

    setIsGeneratingAI(true);
    setTimeout(() => {
      setAiAnalysis({
        justification: 'This deviation request is submitted to support the customer\'s operational requirements while maintaining compliance with our service agreements. The request aligns with our customer retention policy and ensures business continuity.',
        policyMatch: 'Relevant Policies:\n• Standard Telecom Service Agreement (Section 4.2)\n• Customer Bill of Rights (Section 2.1)\n• SLA Exception Policy (Section 3.4)',
        riskScore: 'MEDIUM',
        riskExplanation: 'This deviation requires standard review and approval process. No significant compliance risks identified based on initial assessment.'
      });
      setIsGeneratingAI(false);
    }, 2000);
  };

  const useAIJustification = () => {
    if (aiAnalysis) {
      setFormData({ ...formData, businessJustification: aiAnalysis.justification });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    setTimeout(() => {
      alert('Deviation created successfully!');
      setIsSubmitting(false);
      navigate('/');
    }, 1000);
  };

  return (
    <div>
      <div style={{ marginBottom: '2rem' }}>
        <h2>📝 Create New Deviation</h2>
        <p style={{ color: '#636e72' }}>Fill in the details below to initiate a service deviation request</p>
      </div>

      <form onSubmit={handleSubmit}>
        <div className="card">
          <div className="form-group">
            <label>Deviation Type *</label>
            <select
              name="deviationType"
              value={formData.deviationType}
              onChange={handleChange}
              required
            >
              <option value="">Select deviation type</option>
              {deviationTypes.map(type => (
                <option key={type} value={type}>{type.replace('_', ' ')}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Customer Context *</label>
            <textarea
              name="customerContext"
              value={formData.customerContext}
              onChange={handleChange}
              placeholder="Describe the customer situation, context, and reason for deviation request..."
              required
            />
          </div>

          <div className="form-group">
            <label>Business Justification</label>
            <textarea
              name="businessJustification"
              value={formData.businessJustification}
              onChange={handleChange}
              placeholder="Provide the business justification for this deviation request..."
            />
          </div>
        </div>

        <div className="ai-panel">
          <h3>🤖 AI Action Panel</h3>
          <p style={{ marginBottom: '1rem', color: '#636e72' }}>
            Let AI help you generate a professional business justification and analyze risk.
          </p>

          {!aiAnalysis ? (
            <button
              type="button"
              className="btn btn-primary"
              onClick={generateAIAnalysis}
              disabled={isGeneratingAI}
            >
              {isGeneratingAI ? '⏳ Analyzing...' : '✨ Generate Justification with AI'}
            </button>
          ) : (
            <div style={{ marginTop: '1rem' }}>
              <div style={{ background: 'white', padding: '1rem', borderRadius: '8px', marginBottom: '1rem' }}>
                <strong>📋 AI Generated Justification:</strong>
                <p style={{ marginTop: '0.5rem', color: '#636e72' }}>{aiAnalysis.justification}</p>
                <button
                  type="button"
                  className="btn btn-secondary"
                  style={{ marginTop: '0.5rem', fontSize: '0.8rem' }}
                  onClick={useAIJustification}
                >
                  Use This Justification
                </button>
              </div>

              <div style={{ background: 'white', padding: '1rem', borderRadius: '8px', marginBottom: '1rem' }}>
                <strong>📜 Policy Match:</strong>
                <pre style={{ marginTop: '0.5rem', color: '#636e72', whiteSpace: 'pre-wrap' }}>{aiAnalysis.policyMatch}</pre>
              </div>

              <div>
                <strong>⚠️ Risk Score: </strong>
                <span className={`risk-score ${aiAnalysis.riskScore.toLowerCase()}`}>
                  {aiAnalysis.riskScore}
                </span>
                <p style={{ marginTop: '0.5rem', color: '#636e72' }}>{aiAnalysis.riskExplanation}</p>
              </div>
            </div>
          )}
        </div>

        <div className="actions" style={{ marginTop: '2rem' }}>
          <button type="submit" className="btn btn-success" disabled={isSubmitting}>
            {isSubmitting ? 'Submitting...' : '✓ Submit for Approval'}
          </button>
          <button type="button" className="btn btn-secondary" onClick={() => navigate('/')}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default InitiationForm;