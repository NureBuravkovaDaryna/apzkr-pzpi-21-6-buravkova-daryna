import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

function ParameterModal({ show, handleClose, handleSave }) {
    const [temperature, setTemperature] = useState('');
    const [strength, setStrength] = useState('');
    const [volume, setVolume] = useState('');

    const handleSubmit = () => {
        handleSave(temperature, strength, volume);
        handleClose();
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Змінити параметри</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formTemperature">
                        <Form.Label>Температура</Form.Label>
                        <Form.Control
                            type="number"
                            value={temperature}
                            onChange={(e) => setTemperature(e.target.value)}
                        />
                    </Form.Group>
                    <Form.Group controlId="formStrength">
                        <Form.Label>Сила</Form.Label>
                        <Form.Control
                            type="number"
                            value={strength}
                            onChange={(e) => setStrength(e.target.value)}
                        />
                    </Form.Group>
                    <Form.Group controlId="formVolume">
                        <Form.Label>Об'єм</Form.Label>
                        <Form.Control
                            type="number"
                            value={volume}
                            onChange={(e) => setVolume(e.target.value)}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={handleSubmit}>
                    Зберегти зміни
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default ParameterModal;
