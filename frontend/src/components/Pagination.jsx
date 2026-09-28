import React from 'react';
import { Box, Button, ButtonGroup } from '@mui/material';

export default function Pagination({ page, totalPages, setPage }) {
    return (
        <Box display="flex" justifyContent="flex-end" mt={4}>
            <ButtonGroup variant="outlined" color="primary">
                <Button 
                    onClick={() => setPage(page - 1)} 
                    disabled={page === 0}
                >
                    Anterior
                </Button>
                <Button 
                    disabled 
                    sx={{ color: 'text.primary !important', borderColor: 'primary.main !important' }}
                >
                    {page + 1}
                </Button>
                <Button 
                    onClick={() => setPage(page + 1)} 
                    disabled={page >= totalPages - 1 || totalPages === 0}
                >
                    Siguiente
                </Button>
            </ButtonGroup>
        </Box>
    );
}
