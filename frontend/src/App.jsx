import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { CssBaseline } from '@mui/material';
import Login from './pages/Login';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';

// Dummy components para Fase 1
import Dashboard from './pages/Dashboard';
import Technicians from './pages/Technicians';
const Materials = () => <h2>Materiales Placeholder</h2>;
const Orders = () => <h2>Ãƒâ€œrdenes Placeholder</h2>;

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