import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function Dashboard({ currentUser }) {
  const [deviations, setDeviations] = useState([]);
  const [activeTab, setActiveTab] = useState('all');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDeviations();
  }, [activeTab]);

  const loadDeviations = () => {
    setLoading(true);
    setTimeout(() => {
      const mockData = [
        { deviationId: 1, deviationType: 'BILLING_CREDIT', status: 'DRAFT', customerContext: 'Customer requires urgent billing adjustment', aiRiskScore: null, createdAt: '2026-05-14' },
        { deviationId: 2, deviationType: 'BANDWIDTH_INCREASE', status: 'PENDING_OPS', customerContext: 'Enterprise client needs temporary bandwidth boost', aiRiskScore: 'LOW', createdAt: '2026-05-13' },
        { deviationId: 3, deviationType: 'SLA_WAIVER', status: 'PENDING_FINANCE', customerContext: 'SLA breach due to external factors', aiRiskScore: 'MEDIUM', createdAt: '2026-05-12' },
        { deviationId: 4, deviationType: 'CONTENT_EXCEPTION', status: 'PENDING_COMPLIANCE', customerContext: 'Content access for educational purposes', aiRiskScore: 'LOW', createdAt: '2026-05-11' },
        { deviationId: 5, deviationType: 'BILLING_CREDIT', status: 'APPROVED', customerContext: 'Customer loyalty retention', aiRiskScore: 'LOW', createdAt: '2026-05-10' },
        { deviationId: 6, deviationType: 'KYC_DEFERRAL', status: 'REJECTED', customerContext: 'New customer verification delay', aiRiskScore: 'HIGH', createdAt: '2026-05-09' },
      ];

      let filtered = mockData;
      if (activeTab === 'draft') filtered = mockData.filter(d => d.status === 'DRAFT');
      else if (activeTab === 'pending') filtered = mockData.filter(d => d.status.startsWith('PENDING'));
      else if (activeTab === 'completed') filtered = mockData.filter(d => ['APPROVED', 'REJECTED', 'CLOSED'].includes(d.status));

      setDeviations(filtered);
      setLoading(false);
    }, 500);
  };

  const getStatusBadgeClass = (status) => {
    return status.toLowerCase().replace('_', '_');
  };

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2>📊 Dashboard</h2>
        <Link to="/create" className="btn btn-primary">+ New Deviation</Link>
      </div>

      <div className="tabs">
        <button className={`tab ${activeTab === 'all' ? 'active' : ''}`} onClick={() => setActiveTab('all')}>
          All Deviations
        </button>
        <button className={`tab ${activeTab === 'draft' ? 'active' : ''}`} onClick={() => setActiveTab('draft')}>
          Drafts
        </button>
        <button className={`tab ${activeTab === 'pending' ? 'active' : ''}`} onClick={() => setActiveTab('pending')}>
          Pending Approval
        </button>
        <button className={`tab ${activeTab === 'completed' ? 'active' : ''}`} onClick={() => setActiveTab('completed')}>
          Completed
        </button>
      </div>

      {loading ? (
        <div className="loading">Loading...</div>
      ) : deviations.length === 0 ? (
        <div className="empty-state">
          <h3>No deviations found</h3>
          <p>Create a new deviation to get started</p>
        </div>
      ) : (
        <div className="dashboard-grid">
          {deviations.map(deviation => (
            <div key={deviation.deviationId} className="card">
              <div className="card-header">
                <span className="card-title">{deviation.deviationType.replace('_', ' ')}</span>
                <span className={`badge ${getStatusBadgeClass(deviation.status)}`}>
                  {deviation.status.replace('_', ' ')}
                </span>
              </div>
              <p style={{ color: '#636e72', marginBottom: '1rem' }}>
                {deviation.customerContext.length > 80
                  ? deviation.customerContext.substring(0, 80) + '...'
                  : deviance.customerContext}
              </p>
              {deviation.aiRiskScore && (
                <div>
                  <span className={`risk-score ${deviation.aiRiskScore.toLowerCase()}`}>
                    Risk: {deviation.aiRiskScore}
                  </span>
                </div>
              )}
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '1rem' }}>
                <small style={{ color: '#636e72' }}>Created: {deviation.createdAt}</small>
                {deviation.status === 'PENDING_OPS' && currentUser.role.includes('APPROVER') && (
                  <Link to={`/approval/${deviation.deviationId}`} className="btn btn-primary" style={{ padding: '0.5rem 1rem', fontSize: '0.9rem' }}>
                    Review
                  </Link>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Dashboard;