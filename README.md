Certainly! Here's an updated README.md file that includes the project structure:

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
- React.js with TypeScript
- shadcn/ui component library
- WebSocket client for real-time updates
- Tailwind CSS for styling
- React Router v6 for navigation
- Context API for state management

### Backend
- Spring Boot
- Spring WebSocket with TextWebSocketHandler
- Spring Data JPA for data persistence
- Concurrent Hash Map for session management
- MySQL Database
- Maven for build management
- Jackson for JSON processing
- Java Logger for system logging

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

Refer to the [API Documentation section](#-api-documentation) for details on the available API endpoints and WebSocket communication.

## 🏗️ Project Structure

The project is structured as follows:

```
realtime-event-ticketing/
├── backend/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── ticketing/
│   │               ├── config/
│   │               ├── controller/
│   │               ├── model/
│   │               ├── repository/
│   │               ├── service/
│   │               └── websocket/
│   └── resources/
│       └── application.properties
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── contexts/
│   │   ├── hooks/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── styles/
│   │   ├── types/
│   │   ├── utils/
│   │   ├── App.tsx
│   │   └── index.tsx
│   ├── .env
│   ├── package.json
│   └── tsconfig.json
├── LICENSE.md
└── README.md
```

The backend project is structured using the standard Spring Boot project layout, with the main Java code located in the `com.example.ticketing` package. The frontend project is structured using a common React project layout, with the main components, pages, and services organized in their respective directories.

### 🧩 Components Used
- **Shadcn/UI Components**:
  - `Card`: Layout and content containers
  - `Button`: Interactive controls
  - `Input`: User input fields
  - `Label`: Form field labels
  - `ScrollArea`: Scrollable content area

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

Refer to the [System Architecture section](#-system-architecture) for details on the different components and their responsibilities.

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

We welcome contributions to improve the Real-time Event Ticketing System! Refer to the [Contributing section](#-contributing) for guidelines on how to contribute.

## 📝 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 👥 Authors

- **Dasun Shanaka Jayawardhana** - *Initial work & Maintenance* - [Ds-Jayawardhana](https://github.com/Ds-Jayawardhana)

## 🙏 Acknowledgments

- [shadcn/ui](https://ui.shadcn.com/) for the modern React components
- Spring Framework team for the robust backend framework
- MySQL community for the reliable database system
- All contributors who have helped improve this project
- Special thanks to everyone who has reported issues and suggested improvements

## 📞 Contact

- Dasun Jayawardhana - [dsjayawardhana03@gmail.com](mailto:dsjayawardhana03@gmail.com)
- Project Link: [https://github.com/Ds-Jayawardhana/Ticketing_System](https://github.com/Ds-Jayawardhana/Ticketing_System)
- Issues: [https://github.com/Ds-Jayawardhana/Ticketing_System/issues](https://github.com/Ds-Jayawardhana/Ticketing_System/issues)
