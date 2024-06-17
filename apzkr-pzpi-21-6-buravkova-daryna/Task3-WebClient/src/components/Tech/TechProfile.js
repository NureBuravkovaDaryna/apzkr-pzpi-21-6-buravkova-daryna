import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

function TechProfile() {
    const [profile, setProfile] = useState(null);
    const [showEditModal, setShowEditModal] = useState(false);
    const [editedProfile, setEditedProfile] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
    });

    useEffect(() => {
        const userId = localStorage.getItem('userId');
        fetch(`https://localhost:7256/api/Technician/${userId}`)
            .then(response => response.json())
            .then(data => {
                setProfile(data);
                setEditedProfile({
                    firstName: data.firstName,
                    lastName: data.lastName,
                    email: data.email,
                    phone: data.phone,
                });
            })
            .catch(error => console.error('Error fetching profile:', error));
    }, []);

    const handleShowEditModal = () => setShowEditModal(true);
    const handleCloseEditModal = () => setShowEditModal(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditedProfile(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSaveChanges = () => {
        const userId = localStorage.getItem('userId');
        fetch(`https://localhost:7256/api/Technician/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ ...editedProfile, id: userId }),
        })
            .then(response => response.json())
            .then(data => {
                setProfile(data);
                handleCloseEditModal();
            })
            .catch(error => console.error('Error updating profile:', error));
    };

    return (
        <div className="container mt-4">
            <div className="row">
                <div className="col-md-6">
                    <div className="profile-container">
                        <h2 className="mb-4">Ваш профіль</h2>
                        {profile && (
                            <div className="profile-details">
                                <p><strong>Ім'я:</strong> {profile.firstName}</p>
                                <p><strong>Прізвище:</strong> {profile.lastName}</p>
                                <p><strong>Email:</strong> {profile.email}</p>
                                <p><strong>Номер телефону:</strong> {profile.phone}</p>
                                <Button variant="btn btn-primary  mt-3" onClick={handleShowEditModal}>
                                    Редагувати дані профілю
                                </Button>
                            </div>
                        )}
                    </div>
                </div>
                <div className="col-md-6">
                    <div className="profile-schedule-support">
                        <h2 className="mb-4">Графік роботи</h2>
                        <div className="schedule-details">
                            <p><strong>Понеділок-П'ятниця:</strong> 9:00 - 18:00</p>
                            <p><strong>Субота:</strong> 10:00 - 16:00</p>
                            <p><strong>Неділя:</strong> Вихідний</p>
                        </div>
                        <h2 className="mb-4">Контакти для підтримки</h2>
                        <div className="support-contacts">
                            <p><strong>Телефон підтримки:</strong> +38 (012) 345-67-89</p>
                            <p><strong>Email підтримки:</strong> support@example.com</p>
                        </div>
                    </div>
                </div>
            </div>

            <Modal show={showEditModal} onHide={handleCloseEditModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Редагувати профіль</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formFirstName">
                            <Form.Label>Ім'я</Form.Label>
                            <Form.Control
                                type="text"
                                name="firstName"
                                value={editedProfile.firstName}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="formLastName">
                            <Form.Label>Прізвище</Form.Label>
                            <Form.Control
                                type="text"
                                name="lastName"
                                value={editedProfile.lastName}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="formEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                name="email"
                                value={editedProfile.email}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="formPhone">
                            <Form.Label>Номер телефону</Form.Label>
                            <Form.Control
                                type="text"
                                name="phone"
                                value={editedProfile.phone}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={handleSaveChanges}>
                        Зберегти зміни
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default TechProfile;
