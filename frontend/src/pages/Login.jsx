import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';
import { toast } from 'react-toastify';
import { Box, Button, TextField, Typography, Paper, CircularProgress } from '@mui/material';

export default function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const response = await api.post('/api/auth/login', { username, password });
            localStorage.setItem('jwt_token', response.data.token);
            toast.success('Login exitoso');
            navigate('/');
        } catch (error) {
            toast.error('Credenciales inválidas');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh" bgcolor="#f5f5f5">
            <Paper elevation={3} sx={{ p: 4, width: '100%', maxWidth: 400 }}>
                <Typography variant="h5" component="h1" gutterBottom align="center">
                    Work Order System
                </Typography>
                <form onSubmit={handleSubmit}>
                    <TextField
                        fullWidth
                        label="Username"
                        margin="normal"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    <TextField
                        fullWidth
                        label="Password"
                        type="password"
                        margin="normal"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <Box mt={2}>
                        <Button
                            fullWidth
                            variant="contained"
                            color="primary"
                            type="submit"
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} /> : 'Login'}
                        </Button>
                    </Box>
                </form>
            </Paper>
        </Box>
    );
}