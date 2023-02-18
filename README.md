# BudgetAppv2

REST API to control your expenses and income.

## Table of Contents
* [General Info](#general-information)
* [Usage](#usage)
* [Technologies Used](#technologies-used)
* [Database Diagram](#database-diagram)


## General Information
Anyone can create their own account and log into it, they will have access to their created expense groups (e.g. Company, Investment, Family, Home). In each group you can create a category (e.g. Credit, Subscriptions, Accounts), and for each category you can create a transaction (Income, Transfer, Loan Repayment)

I am creating an application for personal use and for learning. 

## Usage
> Docker

```shell
docker run -d -it -p 8081:8080/tcp --name budget wojtur/budget:v2
```

## Technologies Used
- Spring Security + JWT
- Spring JPA
- Hibernate
- PostgreSQL
- Docker
- Mock

## Database Diagram:
![Diagram](https://user-images.githubusercontent.com/79547731/215884117-917a4860-e9fe-4b11-ad1c-56181fa4c003.PNG)


## API Reference (To refactor)

#### Login

```http
  POST /users/login
```

#### Get all users

```http
  GET /users
```


#### Get user

```http
  GET /users/{id}
```


#### Create user
```http
  POST /users
```


#### Update user
```http
  PUT /users/{id}
```

#### Delete user
```http
  DELETE /users/{id}
```

