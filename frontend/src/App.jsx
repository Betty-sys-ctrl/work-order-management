import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { CssBaseline } from '@mui/material';
import Login from './pages/Login';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';

import Dashboard from './pages/Dashboard';
import Technicians from './pages/Technicians';
import Materials from './pages/Materials';
const Orders = () => <h2>Órdenes</h2>;

export default function App() {
  return (
    <>
      <CssBaseline />
      <ToastContainer position="top-right" autoClose={3000} />
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route element={<ProtectedRoute />}>
            <Route element={<Layout />}>
              <Route path="/" element={<Dashboard />} />
              <Route path="/technicians" element={<Technicians />} />
              <Route path="/materials" element={<Materials />} />
              <Route path="/orders" element={<Orders />} />
            </Route>
          </Route>
        </Routes>
      </Router>
    </>
  );
}