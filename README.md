1.Wallet Service API
- A production-ready backend application built using Java and Spring Boot to handle wallet operations like deposit, withdrawal, and balance retrieval.

2.Features
- Deposit money into wallet  
- Withdraw money with validation  
- Get wallet balance  
- Handles insufficient balance errors  
- Global exception handling  
- Dockerized application  
- PostgreSQL database integration  
- Liquibase database migration  

3.Tech Stack
- Java 17
- Spring Boot 3
- PostgreSQL
- Liquibase
- Docker & Docker Compose
- REST APIs

4.API Endpoints
  Deposit / Withdraw
  POST/api/v1/wallet
  Request:
   {
    "walletId": "11111111-1111-1111-1111-111111111111",
    "operationType": "DEPOSIT",
    "amount": 1000
  }  
