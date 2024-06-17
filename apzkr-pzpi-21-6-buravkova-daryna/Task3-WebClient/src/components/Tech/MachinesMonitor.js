import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/MachinesMonitor.css';

function MachinesMonitor() {
    const [machines, setMachines] = useState([]);
    const [searchName, setSearchName] = useState('');
    const [setLocations] = useState([]);
    const [locationDetails, setLocationDetails] = useState({});
    const [selectedStatus, setSelectedStatus] = useState('');

    useEffect(() => {
        fetch('https://localhost:7256/api/Region')
            .then(response => response.json())
            .then(data => {
                setLocations(data);
                const locationMap = {};
                data.forEach(location => {
                    locationMap[location.id] = location;
                });
                setLocationDetails(locationMap);
            })
            .catch(error => console.error('Error fetching locations:', error));
    },);

    useEffect(() => {
        const technicianId = localStorage.getItem('userId'); // Get technician ID from local storage
        console.log('Technician ID:', technicianId); // Log technician ID
        if (technicianId) {
            fetch(`https://localhost:7256/api/Technician/machines?technicianId=${technicianId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Fetched machines data:', data); // Log fetched data
                    if (Array.isArray(data)) {
                        setMachines(data);
                    } else {
                        console.error('Unexpected data format:', data);
                        setMachines([]); // Set as empty array if format is unexpected
                    }
                })
                .catch(error => console.error('Error fetching machines:', error));
        }
    }, []);

    const handleSearchNameChange = (event) => {
        setSearchName(event.target.value);
    };

    const handleStatusChange = (event) => {
        setSelectedStatus(event.target.value);
    };

    const getAddress = (machine) => {
        const location = locationDetails[machine.regionId];
        return location ? `${machine.address}, ${location.city}, ${location.country}` : machine.address;
    };

    const getStatus = (isWorking) => {
        return isWorking ? 'Працює' : 'Не працює';
    };
    
    const filteredMachines = machines.filter(machine => {
        const matchesName = searchName ? machine.name.toLowerCase().includes(searchName.toLowerCase()) : true;
        const matchesStatus = selectedStatus ? machine.isWorking === (selectedStatus === 'true') : true;
        return matchesStatus && matchesName;
    });

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">Контроль автоматів з кавою</h2>
            <div className="row mb-4">
                <div className="col">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Пошук за назвою"
                        value={searchName}
                        onChange={handleSearchNameChange}
                    />
                </div>
                <div className="col">
                    <select className="form-control" value={selectedStatus} onChange={handleStatusChange}>
                        <option value="">Всі статуси</option>
                        <option value="true">Працює</option>
                        <option value="false">Не працює</option>
                    </select>
                </div>
            </div>
            <div className="row">
                {filteredMachines.length === 0 ? (
                    <p className="text-center">Немає автоматів для відображення.</p>
                ) : (
                    filteredMachines.map(machine => (
                        <div key={machine.id} className="col-md-4 mb-4">
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">{machine.name}</h5>
                                    <p className="card-text">Адреса: {getAddress(machine)}</p>
                                    <p className="card-text">Статус: {getStatus(machine.isWorking)}</p>
                                    <Link to={`/technician/machines/${machine.id}`} className="btn btn-primary">
                                        Переглянути детальніше
                                    </Link>
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

export default MachinesMonitor;
