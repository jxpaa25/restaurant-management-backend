# 🍽️ Restaurant Management System (Backend)

A microservice-based Restaurant Management System built with Java Spring Boot, designed to streamline digital ordering, table management, and restaurant operations via QR-code based customer interaction.

# 🚀 Overview

This backend system powers a modern digital menu and ordering platform where customers scan a table-specific QR code to place orders without needing to log in. Restaurant staff manage and process orders through role-based access control.

The system follows a microservices architecture and uses PostgreSQL for persistent storage, with Docker for containerization and deployment.

# 👥 User Roles

Administrator
Manages system users (Waiters & Managers)
Assigns roles and initializes accounts
Handles password setup flow for new users
Manager
Manages product catalog (food & drinks)
Monitors orders and business performance
Views reports and operational insights
Waiter
Handles incoming table orders
Updates order status and table states
Oversees service flow in real time
🍽️ Core Concept

Customers are identified via QR codes linked to tables, each mapping to a unique table ID. No login is required for customers. Orders are created per table and tracked through a lifecycle managed by staff.

# 🧾 Table & Order Workflow (Example States)

FREE – table is available
OCCUPIED – guests are seated
ORDER_PLACED – customer has placed an order
ACCEPTED – waiter has acknowledged the order
IN_PREPARATION – kitchen is preparing items
SERVED – order delivered
CLOSED – table session ended

# 🧱 Architecture

Microservices-based design
Spring Boot services (Auth, Order, Menu, Table, User Management)
API Gateway for routing requests
PostgreSQL per service (or shared DB depending on design choice)
Service communication via REST (optionally Kafka for async events)

# 🐳 DevOps

Dockerized services
Docker Compose for local development
Scalable deployment-ready structure

# 🎯 Goals

Learn real-world distributed system design
Build a production-style restaurant workflow system
Practice Spring Boot microservices architecture
Implement clean separation of concerns and scalable backend design
