import React, { useEffect, useState } from 'react';
import { Box, Button, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, CircularProgress, IconButton, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Select, MenuItem, InputLabel, FormControl, Grid } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import api from '../api/axiosConfig';
import { toast } from 'react-toastify';

export default function Orders() {
    const [orders, setOrders] = useState([]);
    const [technicians, setTechnicians] = useState([]);
    const [loading, setLoading] = useState(true);
    
    // Pagination & Filters
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [statusFilter, setStatusFilter] = useState('');
    const [technicianFilter, setTechnicianFilter] = useState('');
    
    // Modal state
    const [modalOpen, setModalOpen] = useState(false);
    const [currentOrder, setCurrentOrder] = useState({ title: '', description: '' });
    const [saving, setSaving] = useState(false);

    // Initial Load of Technicians for dropdown
    useEffect(() => {
        const fetchTechnicians = async () => {
            try {
                const response = await api.get('/api/technicians');
                setTechnicians(response.data);
            } catch (error) {
                console.error(error.response || error);
                toast.error('Error al cargar la lista de técnicos');
            }
        };
        fetchTechnicians();
    }, []);

    // Fetch Orders depending on pagination and filters
    const fetchOrders = async () => {
        setLoading(true);
        try {
            // Build query params
            const params = new URLSearchParams();
            params.append('page', page);
            params.append('size', 10);
            if (statusFilter) {
                params.append('status', statusFilter);
            }
            if (technicianFilter) {
                params.append('technicianId', technicianFilter);
            }

            const response = await api.get(`/api/orders?${params.toString()}`);
            setOrders(response.data.content || []);
            setTotalPages(response.data.totalPages || 1);
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al cargar órdenes de trabajo');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchOrders();
    }, [page, statusFilter, technicianFilter]);

    // Handlers
    const handleFilterChange = (filterType, value) => {
        setPage(0); // Reset pagination
        if (filterType === 'status') setStatusFilter(value);
        if (filterType === 'technician') setTechnicianFilter(value);
    };

    const handleOpen = () => {
        setCurrentOrder({ title: '', description: '' });
        setModalOpen(true);
    };

    const handleClose = () => {
        setModalOpen(false);
        setCurrentOrder({ title: '', description: '' });
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCurrentOrder((prev) => ({ ...prev, [name]: value }));
    };

    const handleSave = async () => {
        setSaving(true);
        try {
            await api.post('/api/orders', currentOrder);
            toast.success('Nueva orden creada exitosamente');
            handleClose();
            fetchOrders();
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al crear orden');
        } finally {
            setSaving(false);
        }
    };

    return (
        <Box sx={{ flexGrow: 1 }}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h4">Órdenes de Trabajo</Typography>
                <Button variant="contained" color="primary" onClick={handleOpen}>
                    Crear Nueva Orden
                </Button>
            </Box>

            <Paper sx={{ p: 2, mb: 3 }}>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={4}>
                        <FormControl fullWidth sx={{ minWidth: 200 }}>
                            <InputLabel id="status-label">Estado</InputLabel>
                            <Select
                                labelId="status-label"
                                value={statusFilter}
                                label="Estado"
                                onChange={(e) => handleFilterChange('status', e.target.value)}
                                sx={{ borderRadius: 1, boxShadow: 1 }}
                            >
                                <MenuItem value=""><em>Filtrar por Estado...</em></MenuItem>
                                <MenuItem value="PENDING">Pendiente</MenuItem>
                                <MenuItem value="IN_PROGRESS">En Progreso</MenuItem>
                                <MenuItem value="COMPLETED">Completada</MenuItem>
                                <MenuItem value="CANCELLED">Cancelada</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <FormControl fullWidth sx={{ minWidth: 200 }}>
                            <InputLabel id="tech-label">Técnico Asignado</InputLabel>
                            <Select
                                labelId="tech-label"
                                value={technicianFilter}
                                label="Técnico Asignado"
                                onChange={(e) => handleFilterChange('technician', e.target.value)}
                                sx={{ borderRadius: 1, boxShadow: 1 }}
                            >
                                <MenuItem value=""><em>Todos los Técnicos</em></MenuItem>
                                {technicians.map((tech) => (
                                    <MenuItem key={tech.id} value={tech.id}>{tech.name}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                </Grid>
            </Paper>

            {loading ? (
                <Box display="flex" justifyContent="center" m={5}>
                    <CircularProgress />
                </Box>
            ) : (
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead sx={{ bgcolor: '#f5f5f5' }}>
                            <TableRow>
                                <TableCell>ID</TableCell>
                                <TableCell>Título</TableCell>
                                <TableCell>Descripción</TableCell>
                                <TableCell>Estado</TableCell>
                                <TableCell>ID Técnico</TableCell>
                                <TableCell>Fecha Creación</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {orders.map((order) => (
                                <TableRow key={order.id}>
                                    <TableCell>{order.id}</TableCell>
                                    <TableCell>{order.title}</TableCell>
                                    <TableCell>{order.description}</TableCell>
                                    <TableCell>{order.status}</TableCell>
                                    <TableCell>{order.technicianId || 'Sin Asignar'}</TableCell>
                                    <TableCell>{order.createdAt ? new Date(order.createdAt).toLocaleString('es-ES') : ''}</TableCell>
                                </TableRow>
                            ))}
                            {orders.length === 0 && (
                                <TableRow>
                                    <TableCell colSpan={6} align="center">No hay órdenes registradas</TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}

            <Box display="flex" justifyContent="center" mt={3} gap={2}>
                <Button 
                    variant="outlined" 
                    onClick={() => setPage(page - 1)} 
                    disabled={page === 0}
                >
                    Página Anterior
                </Button>
                <Typography sx={{ alignSelf: 'center' }}>
                    Página {page + 1} de {totalPages}
                </Typography>
                <Button 
                    variant="outlined" 
                    onClick={() => setPage(page + 1)} 
                    disabled={page >= totalPages - 1}
                >
                    Página Siguiente
                </Button>
            </Box>

            <Dialog open={modalOpen} onClose={handleClose}>
                <DialogTitle>Crear Nueva Orden</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Título"
                        name="title"
                        fullWidth
                        value={currentOrder.title}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        label="Descripción"
                        name="description"
                        fullWidth
                        multiline
                        rows={4}
                        value={currentOrder.description}
                        onChange={handleChange}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="secondary">Cancelar</Button>
                    <Button onClick={handleSave} color="primary" variant="contained" disabled={saving || !currentOrder.title || !currentOrder.description}>
                        {saving ? <CircularProgress size={24} /> : 'Guardar'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
}
