import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import InitiationForm from './pages/InitiationForm';
import ApprovalView from './pages/ApprovalView';

function App() {
  const [currentUser, setCurrentUser] = useState({ id: 1, name: 'John Initiator', role: 'INITIATOR' });

  return (
    <Router>
      <div className="app">
        <nav className="navbar">
          <h1>📋 Deviation Workflow</h1>
          <div className="user-info">
            {currentUser.name} ({currentUser.role})
          </div>
        </nav>

        <div className="container">
          <Routes>
            <Route path="/" element={<Dashboard currentUser={currentUser} />} />
            <Route path="/create" element={<InitiationForm currentUser={currentUser} />} />
            <Route path="/approval/:id" element={<ApprovalView currentUser={currentUser} />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;