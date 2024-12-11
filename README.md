# Real-time Event Ticketing System

A modern event ticketing platform built with React and Spring Boot, featuring real-time updates using WebSocket technology. This system provides seamless ticket booking experiences with instant seat availability updates and booking confirmations.

## 🚀 Features

- Real-time ticket availability monitoring through WebSocket
- Multi-threaded ticket vending system
- Configurable vendor and customer simulation
- Dynamic ticket pool management
- Admin dashboard for system configuration
- Real-time activity logging and monitoring
- Concurrent ticket processing
- Rate-limited ticket distribution

## 🛠️ Tech Stack

### Frontend
- React.js
- shadcn/ui for component library
- WebSocket client for real-time updates
- Tailwind CSS for styling
- React Router for navigation
- Context API for state management

### Backend
- Spring Boot
- Spring WebSocket
- Spring Data JPA
- Spring Security
- MySQL Database
- Maven for build management
- Gson for CLI operations

## 📋 Prerequisites

Before running this project, ensure you have installed:

- Node.js (version 16.x or higher)
- npm (version 8.x or higher)
- Java JDK (version 17 or higher)
- Maven (version 3.8 or higher)
- MySQL (version 8.0 or higher)

## 🔧 Installation and Setup

### Backend Setup

1. Clone the repository
```bash
git clone https://github.com/yourusername/realtime-event-ticketing.git
```

2. Navigate to the backend directory
```bash
cd realtime-event-ticketing/backend
```

3. Configure database
- Update `application.properties` with your database credentials
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_ticketing
spring.datasource.username=your_username
spring.datasource.password=your_password

# WebSocket configuration
websocket.endpoint=/ws
websocket.allowed-origins=*
```

4. Build and run the Spring Boot application
```bash
mvn clean install
mvn spring-boot:run
```

### Frontend Setup

1. Navigate to the frontend directory
```bash
cd realtime-event-ticketing/frontend
```

2. Install dependencies
```bash
npm install
```

3. Install shadcn/ui components
```bash
npx shadcn-ui@latest init
```

4. Configure environment variables
- Create `.env` file in the frontend root directory
```env
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_WEBSOCKET_URL=ws://localhost:8080/ws
```

5. Start the development server
```bash
npm start
```

## 🚦 API Documentation

### Configuration Endpoints
- `POST /configure/add` - Add new system configuration
  ```json
  {
    "totalTickets": 100
    // other configuration parameters
  }
  ```
- `GET /configure/totTickets` - Get total number of available tickets

### Ticketing Control Endpoints
- `POST /ticketing/start` - Start the ticketing system
- `POST /ticketing/stop` - Stop the ticketing system

### WebSocket Communication

The system uses WebSocket for real-time updates at endpoint `/websocket`. The WebSocket messages follow this structure:
```json
{
    "type": "MESSAGE_TYPE",
    "payload": {
        // Message specific data
    }
}
```

#### Message Types
1. `ACTIVITY` - Broadcasts activity logs
2. `TICKET_STATUS` - Broadcasts ticket status updates

#### WebSocket Configuration
- Default endpoint: `/websocket`
- Supports cross-origin requests (configured for development)
- Maintains concurrent session management
- Automatically handles connection and disconnection events

### WebSocket Message Examples
```json
// Activity Log Message
{
    "type": "ACTIVITY",
    "payload": {
        // Activity log data
    }
}

// Ticket Status Update
{
    "type": "TICKET_STATUS",
    "payload": {
        // Ticket status data
    }
}

## 🏗️ System Architecture

### Components

#### Backend Services
- `ConfigService`: Manages system configuration and ticket availability
  - Handles total ticket count
  - Maintains remaining tickets
  - Stores configuration history

- `TicketingService`: Core ticketing system controller
  - Manages vendor and customer threads
  - Controls system start/stop operations
  - Validates system configuration
  - Handles concurrent ticket operations

#### Real-time Communication
- `TicketWebHandler`: WebSocket handler for real-time updates
  - Manages WebSocket sessions
  - Broadcasts activity logs
  - Sends ticket status updates
  - Handles connection lifecycle

#### Thread Management
- `Vendor Threads`: Simulate ticket vendors
  - Controlled release rate
  - Configurable number of vendors
  - Individual vendor tracking

- `Customer Threads`: Simulate ticket buyers
  - Configurable retrieval rate
  - Concurrent ticket requests
  - Real-time status updates

### Frontend Routes

```typescript
- "/" - Home page
- "/admin" - Administrator dashboard
- "/customer" - Customer interface
- "/users" - User management
- "/vendor" - Vendor dashboard
- "/configure" - System configuration
```

## 💾 Database Schema

Key entities in the system:

- Config
  - totalTickets
  - maxCap
  - noVendors
  - noCustomers
  - releaseRate
  - retrievalRate

## 🧪 Running Tests

### Backend Tests
```bash
mvn test
```

### Frontend Tests
```bash
npm test
```

## 🌐 Deployment

### Backend Deployment
1. Build the JAR file
```bash
mvn clean package
```

2. Run the application
```bash
java -jar target/event-ticketing-0.0.1-SNAPSHOT.jar
```

### Frontend Deployment
1. Build the production bundle
```bash
npm run build
```

2. Serve the static files using nginx or any web server

## 🔒 Security Features

- JWT based authentication
- Password encryption
- WebSocket connection security
- CORS configuration
- Input validation and sanitization

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 👥 Authors

- **Your Name** - *Initial work* - [YourGithub](https://github.com/yourusername)

## 🙏 Acknowledgments

- shadcn/ui for the beautiful UI components
- Spring Framework team
- MySQL community
- All contributors and testers

## 📞 Contact

- Your Name - [your.email@example.com](mailto:your.email@example.com)
- Project Link: [https://github.com/yourusername/realtime-event-ticketing](https://github.com/yourusername/realtime-event-ticketing)
