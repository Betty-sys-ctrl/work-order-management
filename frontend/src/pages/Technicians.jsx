import React, { useEffect, useState } from 'react';
import { Box, Button, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Switch, CircularProgress, IconButton, Dialog, DialogTitle, DialogContent, DialogActions, TextField } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import api from '../api/axiosConfig';
import { toast } from 'react-toastify';
import Pagination from '../components/Pagination';

export default function Technicians() {
    const [technicians, setTechnicians] = useState([]);
    const [loading, setLoading] = useState(true);
    const [modalOpen, setModalOpen] = useState(false);
    const [currentTech, setCurrentTech] = useState({ name: '', email: '', specialty: '', active: true });
    const [saving, setSaving] = useState(false);
    
    // Pagination
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);

    const fetchTechnicians = async () => {
        setLoading(true);
        try {
            const response = await api.get(`/api/technicians?page=` + page + `&size=20`);
            setTechnicians(response.data.content || []);
            setTotalPages(response.data.totalPages || 1);
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al cargar técnicos');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTechnicians();
    }, [page]);

    const handleOpen = (tech = { name: '', email: '', specialty: '', active: true }) => {
        setCurrentTech(tech);
        setModalOpen(true);
    };

    const handleClose = () => {
        setModalOpen(false);
        setCurrentTech({ name: '', email: '', specialty: '', active: true });
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCurrentTech((prev) => ({ ...prev, [name]: value }));
    };

    const handleSave = async () => {
        setSaving(true);
        try {
            if (currentTech.id) {
                await api.put(`/api/technicians/` + currentTech.id, currentTech);
                toast.success('Técnico actualizado');
            } else {
                await api.post('/api/technicians', currentTech);
                toast.success('Técnico creado');
            }
            handleClose();
            fetchTechnicians();
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al guardar técnico');
        } finally {
            setSaving(false);
        }
    };

    const handleToggleActive = async (tech) => {
        try {
            const updatedTech = { ...tech, active: !tech.active };
            await api.put(`/api/technicians/` + tech.id, updatedTech);
            toast.success(`Técnico ` + (updatedTech.active ? 'activado' : 'inactivado'));
            fetchTechnicians();
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al cambiar estado del técnico');
        }
    };

    return (
        <Box sx={{ flexGrow: 1 }}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h4">Técnicos</Typography>
                <Button variant="contained" color="primary" onClick={() => handleOpen()}>
                    Registrar Técnico
                </Button>
            </Box>

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
                                <TableCell>Nombre</TableCell>
                                <TableCell>Correo</TableCell>
                                <TableCell>Especialidad</TableCell>
                                <TableCell>Activo</TableCell>
                                <TableCell>Acciones</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {technicians.map((tech) => (
                                <TableRow key={tech.id}>
                                    <TableCell>{tech.id}</TableCell>
                                    <TableCell>{tech.name}</TableCell>
                                    <TableCell>{tech.email}</TableCell>
                                    <TableCell>{tech.specialty}</TableCell>
                                    <TableCell>
                                        <Switch
                                            checked={tech.active}
                                            onChange={() => handleToggleActive(tech)}
                                            color="primary"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <IconButton color="primary" onClick={() => handleOpen(tech)}>
                                            <EditIcon />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}
            
            <Pagination page={page} totalPages={totalPages} setPage={setPage} />

            <Dialog open={modalOpen} onClose={handleClose}>
                <DialogTitle>{currentTech.id ? 'Editar Técnico' : 'Registrar Técnico'}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Nombre"
                        name="name"
                        fullWidth
                        value={currentTech.name}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        label="Correo"
                        name="email"
                        fullWidth
                        value={currentTech.email}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        label="Especialidad"
                        name="specialty"
                        fullWidth
                        value={currentTech.specialty}
                        onChange={handleChange}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="secondary">Cancelar</Button>
                    <Button onClick={handleSave} color="primary" variant="contained" disabled={saving}>
                        {saving ? <CircularProgress size={24} /> : 'Guardar'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
}