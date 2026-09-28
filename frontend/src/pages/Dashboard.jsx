import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Typography, Grid, CircularProgress, Alert, List, ListItem, ListItemText, Divider } from '@mui/material';
import api from '../api/axiosConfig';
import { toast } from 'react-toastify';

export default function Dashboard() {
    const [metrics, setMetrics] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        const fetchMetrics = async () => {
            try {
                const response = await api.get('/api/metrics/dashboard');
                setMetrics(response.data);
            } catch (err) {
                setError(true);
                toast.error('Error al cargar métricas del dashboard');
            } finally {
                setLoading(false);
            }
        };
        fetchMetrics();
    }, []);

    if (loading) {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" height="80vh">
                <CircularProgress />
            </Box>
        );
    }

    if (error) {
        return (
            <Box m={2}>
                <Alert severity="error">No se pudieron cargar las métricas. Intente más tarde.</Alert>
            </Box>
        );
    }

    return (
        <Box sx={{ flexGrow: 1 }}>
            <Typography variant="h4" gutterBottom>
                Dashboard
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                    <Card sx={{ bgcolor: '#e3f2fd' }}>
                        <CardContent>
                            <Typography color="textSecondary" gutterBottom>
                                Órdenes Activas (Pendiente / En Progreso)
                            </Typography>
                            <Typography variant="h3" component="h2">
                                {metrics.activeWorkOrders}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <Card sx={{ bgcolor: '#e8f5e9' }}>
                        <CardContent>
                            <Typography color="textSecondary" gutterBottom>
                                Técnicos Activos
                            </Typography>
                            <Typography variant="h3" component="h2">
                                {metrics.activeTechnicians}
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12}>
                    <Card>
                        <CardContent>
                            <Typography variant="h6" gutterBottom color="error">
                                Alertas de Inventario (Stock Bajo &lt; 20)
                            </Typography>
                            {metrics.lowStockMaterials.length === 0 ? (
                                <Typography>No hay materiales con stock bajo.</Typography>
                            ) : (
                                <List>
                                    {metrics.lowStockMaterials.map((mat, index) => (
                                        <React.Fragment key={mat.id}>
                                            <ListItem>
                                                <ListItemText 
                                                    primary={mat.name} 
                                                    secondary={\SKU: \ | Stock Actual: \\} 
                                                />
                                            </ListItem>
                                            {index < metrics.lowStockMaterials.length - 1 && <Divider />}
                                        </React.Fragment>
                                    ))}
                                </List>
                            )}
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
        </Box>
    );
}