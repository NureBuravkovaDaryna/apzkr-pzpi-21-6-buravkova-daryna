import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import ProgressBar from 'react-bootstrap/ProgressBar';
import { useParams } from 'react-router-dom';
import '../../styles/MachineDetailsPage.css';

function MachineControl() {
    const { id } = useParams();
    const [machine, setMachine] = useState(null);
    const [machineCoffees, setMachineCoffees] = useState([]);
    const [machineParameters, setMachineParameters] = useState({});
    const [machineStatus, setMachineStatus] = useState(null);
    const [region, setRegion] = useState(null);

    useEffect(() => {
        fetch(`https://localhost:7256/api/Machine/${id}`)
            .then(response => response.json())
            .then(data => {
                setMachine(data);
                return fetch(`https://localhost:7256/api/Region/${data.regionId}`);
            })
            .then(response => response.json())
            .then(data => setRegion(data))
            .catch(error => console.error('Error fetching data:', error));

        fetch(`https://localhost:7256/api/Coffee/CofeeMachine/${id}`)
            .then(response => response.json())
            .then(data => setMachineCoffees(data))
            .catch(error => console.error('Error fetching machine coffees:', error));

        fetch(`https://localhost:7256/api/MachineParametr?machineId=${id}`)
            .then(response => response.json())
            .then(data => setMachineParameters(data))
            .catch(error => console.error('Error fetching machine parameters:', error));

        fetch(`https://localhost:7256/api/MachineStatus/${id}`)
            .then(response => response.json())
            .then(data => setMachineStatus(data))
            .catch(error => console.error('Error fetching machine status:', error));
    }, [id]);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleString('uk-UA', { timeZone: 'UTC' });
    };

    if (!machine || !region) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container mt-4">
            <h2 className="text-center">Деталі машини</h2>
            <div className="row justify-content-center">
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h3 className="card-title">Інформація про машину</h3>
                            <p>ID: {machine.id}</p>
                            <p>Назва: {machine.name}</p>
                            <p>Адреса: {machine.address}</p>
                            <p>Працює: {machine.isWorking ? 'Так' : 'Ні'}</p>
                            <p>Регіон: {region.city}, {region.country}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="card-title">Параметри машини</h3>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>Температура</th>
                                <th>Сила</th>
                                <th>Об'єм</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{machineParameters.temperature}</td>
                                <td>{machineParameters.strength}</td>
                                <td>{machineParameters.volume}</td>
                            </tr>
                        </tbody>
                    </Table>
                </div>
            </div>
            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="card-title">Статус машини</h3>
                    {machineStatus ? (
                        <>
                            <div className="mb-3">
                                <h5>Рівень кави</h5>
                                <ProgressBar now={machineStatus.cofeeLevel} label={`${machineStatus.cofeeLevel}%`} />
                            </div>
                            <div className="mb-3">
                                <h5>Рівень води</h5>
                                <ProgressBar now={machineStatus.waterLevel} label={`${machineStatus.waterLevel}%`} />
                            </div>
                            <div className="mb-3">
                                <h5>Рівень молока</h5>
                                <ProgressBar now={machineStatus.milkLevel} label={`${machineStatus.milkLevel}%`} />
                            </div>
                            <div className="mb-3">
                                <h5>Рівень цукру</h5>
                                <ProgressBar now={machineStatus.sugarLevel} label={`${machineStatus.sugarLevel}%`} />
                            </div>
                            <div>
                                <h5>Дата моніторингу</h5>
                                <p>{formatDate(machineStatus.monitoringDate)}</p>
                            </div>
                        </>
                    ) : (
                        <p>Завантаження статусу машини...</p>
                    )}
                </div>
            </div>
            <div className="card mb-4">
                <div className="card-body">
                    <h3 className="card-title">Напої машини</h3>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Назва</th>
                            </tr>
                        </thead>
                        <tbody>
                            {machineCoffees.map(coffee => (
                                <tr key={coffee.id}>
                                    <td>{coffee.id}</td>
                                    <td>{coffee.name}</td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </div>
            </div>
        </div>
    );
}

export default MachineControl;
