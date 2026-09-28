import React from 'react';
import { Box, Button, Typography } from '@mui/material';

export default function Pagination({ currentPage, totalPages, onPageChange }) {
    if (totalPages <= 1) return null;

    const getPageNumbers = () => {
        if (totalPages <= 7) {
            return Array.from({ length: totalPages }, (_, i) => i);
        }

        if (currentPage <= 3) {
            return [0, 1, 2, 3, 4, '...', totalPages - 1];
        } else if (currentPage >= totalPages - 4) {
            return [
                0,
                '...',
                totalPages - 5,
                totalPages - 4,
                totalPages - 3,
                totalPages - 2,
                totalPages - 1
            ];
        } else {
            return [
                0,
                '...',
                currentPage - 1,
                currentPage,
                currentPage + 1,
                '...',
                totalPages - 1
            ];
        }
    };

    const pages = getPageNumbers();

    return (
        <Box display="flex" justifyContent="flex-end" alignItems="center" mt={4} gap={1}>
            <Button
                variant="outlined"
                color="primary"
                onClick={() => onPageChange(currentPage - 1)}
                disabled={currentPage === 0}
                sx={{ textTransform: 'none' }}
            >
                Anterior
            </Button>
            
            {pages.map((p, index) => {
                if (p === '...') {
                    return (
                        <Typography key={`ellipsis-${index}`} sx={{ mx: 1, color: 'text.secondary' }}>
                            ...
                        </Typography>
                    );
                }
                
                return (
                    <Button
                        key={p}
                        variant={currentPage === p ? 'contained' : 'outlined'}
                        color="primary"
                        onClick={() => onPageChange(p)}
                        sx={{ 
                            minWidth: '40px', 
                            padding: '6px 0',
                            ...(currentPage === p && {
                                backgroundColor: 'primary.dark',
                                color: 'white'
                            })
                        }}
                    >
                        {p + 1}
                    </Button>
                );
            })}

            <Button
                variant="outlined"
                color="primary"
                onClick={() => onPageChange(currentPage + 1)}
                disabled={currentPage >= totalPages - 1}
                sx={{ textTransform: 'none' }}
            >
                Siguiente
            </Button>
        </Box>
    );
}
