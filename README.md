# MatriQ - Backend

Spring Boot backend for MatriQ, a material sourcing platform for architects, interior designers, and construction engineers. MatriQ streamlines material discovery, technical specification verification, and the Request for Quote (RFQ) process into a single digital workspace.

## Tech Stack
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL / MySQL

## Main Resources
- Materials - full CRUD, with approval workflow (Pending/Approved/Rejected)
- Quotes (RFQ) - full CRUD, linked to buyers and materials
- Users - authentication and role-based access (Buyer, Supplier, Admin)
- Supplier Profiles - company information for suppliers

## Roles
- Buyer - browse materials, create RFQs
- Supplier - manage own materials, respond to RFQs
- Admin - approve/reject materials, manage all resources

## Frontend Repository
https://github.com/shebnem-m/MatriQ-frontend

## Status
In development