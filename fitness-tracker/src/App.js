import React, { useState, useEffect } from 'react';
import './App.css'; // Import CSS file for styling

function App() {
  const [users, setUsers] = useState([]);
  const [workouts, setWorkouts] = useState([]);
  const [newUser, setNewUser] = useState({ name: '', email: '' });
  const [newWorkout, setNewWorkout] = useState({ type: '', duration: '', userId: '' });

  useEffect(() => {
    fetch('/api/users')
      .then(response => response.json())
      .then(data => setUsers(data))
      .catch(error => console.error('Error fetching users:', error));

    fetch('/api/workouts')
      .then(response => response.json())
      .then(data => setWorkouts(data))
      .catch(error => console.error('Error fetching workouts:', error));
  }, []);

  const handleUserChange = (e) => {
    setNewUser({ ...newUser, [e.target.name]: e.target.value });
  };

  const handleWorkoutChange = (e) => {
    setNewWorkout({ ...newWorkout, [e.target.name]: e.target.value });
  };

  const createUser = () => {
    fetch('/api/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newUser)
    })
      .then(response => response.json())
      .then(data => {
        setUsers([...users, data]);
        setNewUser({ name: '', email: '' });
      })
      .catch(error => console.error('Error creating user:', error));
  };

  const createWorkout = () => {
      fetch(`/api/workouts?userId=${newWorkout.userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          type: newWorkout.type,
          duration: newWorkout.duration,
          userInfo: { id: newWorkout.userId }
        })
      })
        .then(response => response.json())
        .then(data => {
          setWorkouts([...workouts, data]);
          setNewWorkout({ type: '', duration: '', userId: '' });
        })
        .catch(error => console.error('Error creating workout:', error));
    };

  return (
    <div className="container">
      <h1 className="title">Fitness Tracker</h1>

      <div className="section">
        <h2>Create User</h2>
        <div className="form">
          <input
            type="text"
            name="name"
            placeholder="Name"
            value={newUser.name}
            onChange={handleUserChange}
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={newUser.email}
            onChange={handleUserChange}
          />
          <button className="btn" onClick={createUser}>Create User</button>
        </div>
      </div>

      <div className="section">
        <h2>Create Workout</h2>
        <div className="form">
          <input
            type="text"
            name="type"
            placeholder="Workout Type"
            value={newWorkout.type}
            onChange={handleWorkoutChange}
          />
          <input
            type="number"
            name="duration"
            placeholder="Duration (minutes)"
            value={newWorkout.duration}
            onChange={handleWorkoutChange}
          />
          <select name="userId" value={newWorkout.userId} onChange={handleWorkoutChange}>
            <option value="">Select User</option>
            {users.map((user) => (
              <option key={user.id} value={user.id}>
                {user.name}
              </option>
            ))}
          </select>
          <button className="btn" onClick={createWorkout}>Create Workout</button>
        </div>
      </div>

      <div className="section">
        <h2>Users</h2>
        <ul className="list">
          {users.map((user) => (
            <li key={user.id}>{user.name} ({user.email})</li>
          ))}
        </ul>
      </div>

      <div className="section">
        <h2>Workouts</h2>
        <ul className="list">
          {workouts.map((workout) => (
            <li key={workout.id}>
                {workout.userInfo.name} - {workout.type} - {workout.duration} minutes
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default App;
