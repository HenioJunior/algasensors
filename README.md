# 📄 Device Management — AlgaSensors

## 📌 Visão Geral

O microsserviço **Device Management** é responsável pelo ciclo de vida dos sensores no sistema **AlgaSensors**, incluindo:

- cadastro de sensores
- consulta
- atualização
- ativação/desativação
- integração com o serviço de monitoramento

Este serviço foi refatorado para seguir os princípios de:

- **DDD (Domain-Driven Design - tático)**
- **Clean Architecture**
- **Separation of Concerns**
- **Testabilidade e escalabilidade**

---

## 🏗️ Status

- [x] Refactor para Clean Architecture
- [x] UseCases implementados
- [x] Controllers desacoplados
- [ ] Domínio rico (em evolução)
- [ ] Testes completos (em andamento)

---

## 🧱 Arquitetura

A aplicação foi organizada em camadas bem definidas:

api/
application/
domain/
infrastructure/


### 📦 api

Responsável pela exposição HTTP:

- Controllers (REST)
- DTOs (`Request` / `Response`)
- Mappers
- Exception Handler

---

### 📦 application

Responsável pelos **casos de uso (Use Cases)**:

- Orquestra o fluxo da aplicação
- Não depende de infraestrutura
- Define portas (interfaces)

#### UseCases implementados

- `CreateSensorUseCase`
- `FindSensorByIdUseCase`
- `FindSensorsUseCase`
- `FindSensorDetailUseCase`
- `UpdateSensorUseCase`
- `EnableSensorUseCase`
- `DisableSensorUseCase`
- `DeleteSensorUseCase`

---

### 📦 domain

Responsável pelo **núcleo do negócio**:

- Entidades (`Sensor`)
- Value Objects (`SensorId`)
- Exceções de domínio

#### Exceções

- `SensorNotFoundException`
- `InvalidSensorIdException`

---

### 📦 infrastructure

Responsável por integrações externas:

- JPA / PostgreSQL
- Clients REST (Temperature Monitoring)
- Implementação de gateways

#### Componentes

- `SensorGatewayImpl`
- `SensorMonitoringClientImpl`

---

## 🔄 Fluxo de execução

### Exemplo: GET /api/sensors/{id}

Controller
-> FindSensorByIdUseCase
-> SensorGateway
-> Repository (JPA)
-> retorna Sensor
-> Mapper
-> Response


---

## 🔌 Integração com outros microsserviços

O Device Management integra com o serviço de **Temperature Monitoring** via HTTP.

### Ações

- Ativar monitoramento
- Desativar monitoramento
- Consultar status de monitoramento


## 📡 Endpoints

### 🔧 Sensores

#### 📥 Criação
- **POST** `/api/sensors`  
  Cria um novo sensor

---

#### 🔍 Consulta
- **GET** `/api/sensors`  
  Lista sensores com paginação

- **GET** `/api/sensors/{id}`  
  Busca um sensor por ID

- **GET** `/api/sensors/{id}/detail`  
  Retorna o sensor com informações de monitoramento

---

#### ✏️ Atualização
- **PUT** `/api/sensors/{id}`  
  Atualiza os dados do sensor

---

#### ⚙️ Ativação / Desativação
- **PUT** `/api/sensors/{id}/enable`  
  Ativa o sensor e o monitoramento

- **DELETE** `/api/sensors/{id}/enable`  
  Desativa o sensor e o monitoramento

---

#### 🗑️ Remoção
- **DELETE** `/api/sensors/{id}`  
  Remove o sensor
