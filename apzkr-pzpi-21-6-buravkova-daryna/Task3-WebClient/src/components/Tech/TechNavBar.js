import React from 'react';
import { Outlet, Link } from 'react-router-dom';
import '../../styles/NavBar.css'; 

function TechNavBar() {
    return (
        <div>
        <div className="sidebar">
            <h2>Панель спеціаліста</h2>
            <ul>
                <li><Link to="/technician/machines">Автомати</Link></li>
                <li><Link to="/technician/profile">Ваші дані</Link></li>
                <li><Link to="/">Вийти</Link></li> 
            </ul>            
        </div>
            <div className="content">
                <Outlet />
            </div>
        </div>
    );
}

export default TechNavBar;