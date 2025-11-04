# Incident Viewer Application

This application was built for a technical interview.

## 🏗️ Architecture

- **Backend**: Spring Boot + Hibernate (JPA)
- **Frontend**: Angular 19 with Standalone Components
- **Database**: PostgreSQL 17
- **Containerization**: Docker Compose

## 📁 Project Structure

```
incident-search-app/
├── backend/               # Spring Boot Application
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/incidents/
│   │       │       ├── entity/         # JPA Entities
│   │       │       ├── repository/     # Repositories
│   │       │       ├── dto/           # Data Transfer Objects
│   │       │       ├── service/       # Business Services
│   │       │       └── controller/    # REST Controllers
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
├── frontend/              # Angular Application
│   ├── src/
│   │   └── app/
│   │       ├── models/              # TypeScript Models
│   │       ├── services/            # Angular Services
│   │       └── components/          # Components
│   ├── package.json
│   └── angular.json
├── 01-ddl.sql            # Database Schema
├── 02-data.sql           # Test Data
└── compose.yaml          # Docker Compose Configuration
```

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- JDK 17+
- Node.js 18+

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd incident-viewer
```

2. **Configure environment variables**
```bash
cp .env.example .env
# Edit .env with your settings
```

3. **Start with Docker Compose**
```bash
docker-compose up -d
```

4. **Access the application**
- Frontend: http://localhost:4200
- Backend API: http://localhost:8080/api
- Database: localhost:5432

## 📊 Features

### Backend
- ✅ REST API for incident search
- ✅ Result pagination
- ✅ Search by title, description, severity, and owner
- ✅ Person and Incident entity management
- ✅ Data validation

### Frontend
- ✅ Search interface
- ✅ Paginated results display
- ✅ Multiple filters (title, description, severity, owner)
- ✅ "Reset" button to clear filters
- ✅ Angular 19 standalone components

## 🔍 API Endpoints

### Search Incidents
```
GET /api/incidents
```

**Query Parameters:**
- `title` (optional): Title (partial search)
- `description` (optional): Description
- `severity` (optional): Incident severity
- `ownerEmail` (optional): Owner email
- `ownerFirstName` (optional): Owner first name
- `ownerLastName` (optional): Owner last name
- `page` (default: 0): Page number
- `size` (default: 10): Page size

**Example Response:**
```json
{
  "content": [
    {
      "id": 1,
      "title": "Test incident",
      "description": "Test incident",
      "severity": "LOW",
      "ownerEmail": "john.smith@company.com",
      "ownerFirstName": "John",
      "ownerLastName": "Doe",
      "createdAt": "2025-11-01T10:24:53.92817"
    }
  ],
  "totalElements": 50,
  "totalPages": 5,
  "number": 0,
  "size": 10
}
```

## 📝 About the optimization commits 

✅ de183ed - perf: Add GIN indexes 
- Database: expect large improvements for textual searches (trigram similarity / fast LIKE) and for queries that filter by severity or owner and sort by creation time.
- Frontend: small UX fix so the button label matches loading state.
- Measured improvement request latency from ~300ms → ~220ms.
      
✅ 051c9c5 - feat: Add pagination
- Backend: update Controller, Service and Repository to use Page and Pageable to return incidents
- Frontend: implement table pagination and add translations
- Greatly increase performance for backend request and frontend display

✅ f40a6e3 - perf: Add backend cache
- Backend: implementation of caffeine cache 
- Advantages:
  - High performance (better than ConcurrentHashMap)
  - Automatic memory management (LRU eviction)
  - Configurable TTL and maximum size
  - Built-in cache statistics
- Disadvantages:
  - Not distributed (single instance)
  - Cache lost on restart

✅ c8993fd - perf: Add frontend cache
- Frontend: use interceptor to store request in-memory for 5 minutes
- The implementation provides a performance improvement by caching HTTP responses on the client side, reducing server load and improving response times for repeated searches. Users can manually clear the cache if needed using the new UI button.