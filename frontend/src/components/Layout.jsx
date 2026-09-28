import React from 'react';
import { Outlet, Link, useNavigate } from 'react-router-dom';
import { Box, Drawer, List, ListItem, ListItemIcon, ListItemText, AppBar, Toolbar, Typography, Button } from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import PeopleIcon from '@mui/icons-material/People';
import BuildIcon from '@mui/icons-material/Build';
import AssignmentIcon from '@mui/icons-material/Assignment';

const drawerWidth = 240;

export default function Layout() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('jwt_token');
        navigate('/login');
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
                <Toolbar>
                    <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
                        Work Order Management
                    </Typography>
                    <Button color="inherit" onClick={handleLogout}>Logout</Button>
                </Toolbar>
            </AppBar>
            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    ['& .MuiDrawer-paper']: { width: drawerWidth, boxSizing: 'border-box' },
                }}
            >
                <Toolbar />
                <Box sx={{ overflow: 'auto' }}>
                    <List>
                        <ListItem button component={Link} to="/">
                            <ListItemIcon><DashboardIcon /></ListItemIcon>
                            <ListItemText primary="Dashboard" />
                        </ListItem>
                        <ListItem button component={Link} to="/technicians">
                            <ListItemIcon><PeopleIcon /></ListItemIcon>
                            <ListItemText primary="Técnicos" />
                        </ListItem>
                        <ListItem button component={Link} to="/materials">
                            <ListItemIcon><BuildIcon /></ListItemIcon>
                            <ListItemText primary="Materiales" />
                        </ListItem>
                        <ListItem button component={Link} to="/orders">
                            <ListItemIcon><AssignmentIcon /></ListItemIcon>
                            <ListItemText primary="Órdenes" />
                        </ListItem>
                    </List>
                </Box>
            </Drawer>
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <Toolbar />
                <Outlet />
            </Box>
        </Box>
    );
}