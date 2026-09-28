import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Box, Button, Typography, Paper, Grid, Select, MenuItem, InputLabel, FormControl, TextField, CircularProgress, Divider, List, ListItem, ListItemText, ListItemAvatar, Avatar } from '@mui/material';
import TimelineIcon from '@mui/icons-material/Timeline';
import api from '../api/axiosConfig';
import { toast } from 'react-toastify';

export default function OrderDetail() {
    const { id } = useParams();
    const navigate = useNavigate();
    
    const [order, setOrder] = useState(null);
    const [history, setHistory] = useState([]);
    const [technicians, setTechnicians] = useState([]);
    const [materials, setMaterials] = useState([]);
    const [loading, setLoading] = useState(true);
    
    // Assignment state
    const [selectedTech, setSelectedTech] = useState('');
    const [assigning, setAssigning] = useState(false);
    
    // Material state
    const [selectedMaterial, setSelectedMaterial] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [consuming, setConsuming] = useState(false);

    const fetchData = async () => {
        setLoading(true);
        try {
            const [orderRes, historyRes, techRes, matRes] = await Promise.all([
                api.get(`/api/orders/${id}`),
                api.get(`/api/orders/${id}/history`),
                api.get('/api/technicians?size=1000'),
                api.get('/api/materials?size=1000')
            ]);
            setOrder(orderRes.data);
            setHistory(historyRes.data);
            setTechnicians(techRes.data.content || []);
            setMaterials(matRes.data.content || []);
            setSelectedTech(orderRes.data.technicianId || '');
        } catch (error) {
            console.error(error);
            toast.error('Error al cargar detalle de la orden');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, [id]);

    const handleStatusChange = async (newStatus) => {
        try {
            await api.put(`/api/orders/${id}/status`, { newStatus });
            toast.success('Estado actualizado correctamente');
            fetchData();
        } catch (error) {
            console.error(error);
            toast.error('Error al cambiar el estado');
        }
    };

    const handleAssign = async () => {
        setAssigning(true);
        try {
            await api.put(`/api/orders/${id}/assign/${selectedTech}`);
            toast.success('Técnico asignado exitosamente');
            fetchData();
        } catch (error) {
            console.error(error);
            toast.error('Error al asignar técnico');
        } finally {
            setAssigning(false);
        }
    };

    const handleConsumeMaterial = async () => {
        setConsuming(true);
        try {
            await api.post(`/api/orders/${id}/materials`, { materialId: selectedMaterial, quantityUsed: parseInt(quantity) });
            toast.success('Material registrado exitosamente');
            setSelectedMaterial('');
            setQuantity(1);
            fetchData();
        } catch (error) {
            console.error(error);
            toast.error(error.response?.data?.message || 'Error al registrar consumo');
        } finally {
            setConsuming(false);
        }
    };

    if (loading) {
        return <Box display="flex" justifyContent="center" m={5}><CircularProgress /></Box>;
    }

    if (!order) {
        return <Typography>No se encontró la orden.</Typography>;
    }

    return (
        <Box sx={{ flexGrow: 1, pb: 5 }}>
            <Button variant="outlined" onClick={() => navigate('/orders')} sx={{ mb: 3 }}>
                Volver a Órdenes
            </Button>
            
            <Grid container spacing={4}>
                {/* Panel Izquierdo: Info, Transiciones y Asignación */}
                <Grid item xs={12} md={8}>
                    <Paper sx={{ p: 3, mb: 4 }}>
                        <Typography variant="h5" gutterBottom>
                            {order.title} <Typography component="span" variant="subtitle1" color="textSecondary">(ID: {order.id})</Typography>
                        </Typography>
                        <Typography variant="body1" paragraph>
                            {order.description}
                        </Typography>
                        <Typography variant="h6" color="primary" gutterBottom>
                            Estado Actual: {order.status}
                        </Typography>
                        
                        <Box display="flex" gap={2} mt={3} flexWrap="wrap">
                            <Button variant="contained" color="info" onClick={() => handleStatusChange('IN_PROGRESS')} disabled={order.status === 'IN_PROGRESS' || order.status === 'COMPLETED' || order.status === 'CANCELLED'}>
                                Iniciar Trabajo
                            </Button>
                            <Button variant="contained" color="success" onClick={() => handleStatusChange('COMPLETED')} disabled={order.status === 'COMPLETED' || order.status === 'CANCELLED'}>
                                Completar Orden
                            </Button>
                            <Button variant="contained" color="error" onClick={() => handleStatusChange('CANCELLED')} disabled={order.status === 'COMPLETED' || order.status === 'CANCELLED'}>
                                Cancelar
                            </Button>
                        </Box>
                    </Paper>

                    <Paper sx={{ p: 3, mb: 4 }}>
                        <Typography variant="h6" gutterBottom>Asignación de Técnico</Typography>
                        <Box display="flex" gap={2} alignItems="center" mt={2}>
                            <FormControl fullWidth sx={{ minWidth: 200 }}>
                                <InputLabel id="tech-label">Técnico</InputLabel>
                                <Select
                                    labelId="tech-label"
                                    value={selectedTech}
                                    label="Técnico"
                                    onChange={(e) => setSelectedTech(e.target.value)}
                                >
                                    <MenuItem value=""><em>Sin Asignar</em></MenuItem>
                                    {technicians.map((tech) => (
                                        <MenuItem key={tech.id} value={tech.id}>
                                            {tech.name} ({tech.specialty})
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                            <Button variant="contained" color="primary" onClick={handleAssign} disabled={assigning || !selectedTech || selectedTech === order.technicianId}>
                                {assigning ? <CircularProgress size={24} /> : 'Asignar Técnico'}
                            </Button>
                        </Box>
                    </Paper>

                    <Paper sx={{ p: 3 }}>
                        <Typography variant="h6" gutterBottom>Gestión de Materiales</Typography>
                        <Box display="flex" gap={2} alignItems="center" mt={2}>
                            <FormControl fullWidth sx={{ minWidth: 200 }}>
                                <InputLabel id="mat-label">Material</InputLabel>
                                <Select
                                    labelId="mat-label"
                                    value={selectedMaterial}
                                    label="Material"
                                    onChange={(e) => setSelectedMaterial(e.target.value)}
                                >
                                    <MenuItem value=""><em>Seleccionar...</em></MenuItem>
                                    {materials.map((mat) => (
                                        <MenuItem key={mat.id} value={mat.id} disabled={mat.stockQuantity <= 0}>
                                            {mat.name} (Stock: {mat.stockQuantity})
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                            <TextField
                                label="Cantidad"
                                type="number"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                                inputProps={{ min: 1 }}
                                sx={{ width: 100 }}
                            />
                            <Button variant="contained" color="secondary" onClick={handleConsumeMaterial} disabled={consuming || !selectedMaterial || quantity < 1}>
                                {consuming ? <CircularProgress size={24} /> : 'Registrar Consumo'}
                            </Button>
                        </Box>
                        
                        <Divider sx={{ my: 3 }} />
                        
                        <Typography variant="subtitle1" gutterBottom>Materiales Consumidos en esta Orden</Typography>
                        {(!order.materials || order.materials.length === 0) ? (
                            <Typography variant="body2" color="textSecondary">No se han registrado materiales consumidos.</Typography>
                        ) : (
                            <List>
                                {order.materials.map((om, index) => {
                                    const materialName = materials.find(m => m.id === om.materialId)?.name || `Material ID: ${om.materialId}`;
                                    return (
                                        <ListItem key={index}>
                                            <ListItemText primary={materialName} secondary={`Cantidad Usada: ${om.quantityUsed}`} />
                                        </ListItem>
                                    );
                                })}
                            </List>
                        )}
                    </Paper>
                </Grid>

                {/* Panel Derecho: Timeline */}
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, height: '100%', minHeight: 400 }}>
                        <Typography variant="h6" gutterBottom>Línea de Tiempo</Typography>
                        <Divider sx={{ mb: 2 }} />
                        
                        <List sx={{ width: '100%', bgcolor: 'background.paper' }}>
                            {history.length === 0 ? (
                                <Typography variant="body2" color="textSecondary" align="center" mt={4}>
                                    No hay historial registrado.
                                </Typography>
                            ) : (
                                history.map((hist) => (
                                    <ListItem key={hist.id} alignItems="flex-start" sx={{ mb: 2 }}>
                                        <ListItemAvatar>
                                            <Avatar sx={{ bgcolor: 'primary.main' }}>
                                                <TimelineIcon />
                                            </Avatar>
                                        </ListItemAvatar>
                                        <ListItemText
                                            primary={`Cambio a ${hist.newStatus}`}
                                            secondary={
                                                <React.Fragment>
                                                    <Typography sx={{ display: 'block' }} component="span" variant="body2" color="text.primary">
                                                        De: {hist.previousStatus || 'Creación'}
                                                    </Typography>
                                                    {new Date(hist.changedAt).toLocaleString('es-ES')}
                                                </React.Fragment>
                                            }
                                        />
                                    </ListItem>
                                ))
                            )}
                        </List>
                    </Paper>
                </Grid>
            </Grid>
        </Box>
    );
}
