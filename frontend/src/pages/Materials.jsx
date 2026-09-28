import React, { useEffect, useState } from 'react';
import { Box, Button, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, CircularProgress, IconButton, Dialog, DialogTitle, DialogContent, DialogActions, TextField } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import api from '../api/axiosConfig';
import { toast } from 'react-toastify';
import Pagination from '../components/Pagination';

export default function Materials() {
    const [materials, setMaterials] = useState([]);
    const [loading, setLoading] = useState(true);
    
    // Pagination
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    
    // Base Modal (Create / Edit)
    const [modalOpen, setModalOpen] = useState(false);
    const [currentMaterial, setCurrentMaterial] = useState({ name: '', sku: '', stockQuantity: 0 });
    const [saving, setSaving] = useState(false);

    // Add Stock Modal
    const [stockModalOpen, setStockModalOpen] = useState(false);
    const [stockMaterial, setStockMaterial] = useState(null);
    const [quantityToAdd, setQuantityToAdd] = useState(1);
    const [savingStock, setSavingStock] = useState(false);

    const fetchMaterials = async () => {
        setLoading(true);
        try {
            const response = await api.get(`/api/materials?page=` + page + `&size=20`);
            setMaterials(response.data.content || []);
            setTotalPages(response.data.totalPages || 1);
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al cargar materiales');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchMaterials();
    }, [page]);

    // Handlers for Base Modal
    const handleOpen = (material = { name: '', sku: '', stockQuantity: 0 }) => {
        setCurrentMaterial(material);
        setModalOpen(true);
    };

    const handleClose = () => {
        setModalOpen(false);
        setCurrentMaterial({ name: '', sku: '', stockQuantity: 0 });
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCurrentMaterial((prev) => ({ ...prev, [name]: value }));
    };

    const handleSave = async () => {
        setSaving(true);
        try {
            if (currentMaterial.id) {
                const updatePayload = {
                    name: currentMaterial.name,
                    sku: currentMaterial.sku,
                    stockQuantity: currentMaterial.stockQuantity || 0
                };
                await api.put(`/api/materials/` + currentMaterial.id, updatePayload);
                toast.success('Material actualizado exitosamente');
            } else {
                await api.post('/api/materials', currentMaterial);
                toast.success('Material creado exitosamente');
            }
            handleClose();
            fetchMaterials();
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al guardar material');
        } finally {
            setSaving(false);
        }
    };

    // Handlers for Add Stock Modal
    const handleOpenStockModal = (material) => {
        setStockMaterial(material);
        setQuantityToAdd(1);
        setStockModalOpen(true);
    };

    const handleCloseStockModal = () => {
        setStockModalOpen(false);
        setStockMaterial(null);
        setQuantityToAdd(1);
    };

    const handleAddStock = async () => {
        setSavingStock(true);
        try {
            await api.patch(`/api/materials/` + stockMaterial.id + `/add-stock`, { quantity: parseInt(quantityToAdd) });
            toast.success(`Stock incrementado exitosamente`);
            handleCloseStockModal();
            fetchMaterials();
        } catch (error) {
            console.error(error.response || error);
            toast.error('Error al incrementar stock');
        } finally {
            setSavingStock(false);
        }
    };

    return (
        <Box sx={{ flexGrow: 1 }}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h4">Catálogo de Materiales</Typography>
                <Button variant="contained" color="primary" onClick={() => handleOpen()}>
                    Registrar Material
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
                                <TableCell>SKU</TableCell>
                                <TableCell>Stock Actual</TableCell>
                                <TableCell>Acciones</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {materials.map((mat) => (
                                <TableRow key={mat.id}>
                                    <TableCell>{mat.id}</TableCell>
                                    <TableCell>{mat.name}</TableCell>
                                    <TableCell>{mat.sku}</TableCell>
                                    <TableCell>{mat.stockQuantity}</TableCell>
                                    <TableCell>
                                        <IconButton color="primary" title="Editar Material" onClick={() => handleOpen(mat)}>
                                            <EditIcon />
                                        </IconButton>
                                        <IconButton color="secondary" title="Añadir Stock" onClick={() => handleOpenStockModal(mat)}>
                                            <AddCircleIcon />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}

            <Pagination page={page} totalPages={totalPages} setPage={setPage} />

            {/* Base Form Modal */}
            <Dialog open={modalOpen} onClose={handleClose}>
                <DialogTitle>{currentMaterial.id ? 'Editar Material' : 'Registrar Material'}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Nombre"
                        name="name"
                        fullWidth
                        value={currentMaterial.name}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        label="SKU"
                        name="sku"
                        fullWidth
                        value={currentMaterial.sku}
                        onChange={handleChange}
                    />
                    {!currentMaterial.id && (
                        <TextField
                            margin="dense"
                            label="Stock Inicial"
                            name="stockQuantity"
                            type="number"
                            fullWidth
                            value={currentMaterial.stockQuantity}
                            onChange={handleChange}
                            inputProps={{ min: 0 }}
                        />
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="secondary">Cancelar</Button>
                    <Button onClick={handleSave} color="primary" variant="contained" disabled={saving}>
                        {saving ? <CircularProgress size={24} /> : 'Guardar'}
                    </Button>
                </DialogActions>
            </Dialog>

            {/* Add Stock Modal */}
            <Dialog open={stockModalOpen} onClose={handleCloseStockModal}>
                <DialogTitle>Añadir Stock - {stockMaterial?.name}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Cantidad a Añadir"
                        type="number"
                        fullWidth
                        value={quantityToAdd}
                        onChange={(e) => setQuantityToAdd(e.target.value)}
                        inputProps={{ min: 1 }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseStockModal} color="secondary">Cancelar</Button>
                    <Button onClick={handleAddStock} color="primary" variant="contained" disabled={savingStock}>
                        {savingStock ? <CircularProgress size={24} /> : 'Confirmar Incremento'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
}