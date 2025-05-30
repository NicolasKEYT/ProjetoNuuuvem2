# Projeto Integrador – Cloud Developing 2025/1

> CRUD simples + API Gateway + Lambda /report + RDS + CI/CD

**Tópico17**:

1. 10418047 - Nicolas Gonçalves - AWS
2. 10417843 - Erik Almeida - Back e Documentação
3. 10419038 - Diego Estevão - Front e Documentação


## 1. Visão geral
O domínio é um CRUD em SpringBoot que pega os dados da API OMDb e exibe filmes com "War" no nome, permitindo listar todos, atualizar notas, deletar e visualizar filmes salvos. Tema escolhido pois adoramos guerra 🤘.

O Crud: 

Importa os filmes do OMDb

Lista os filmes salvos no banco
Exibe um filme aleatório da lista

Pode atualizar a nota do filme

Pode deletar um filme do banco

Exibe detalhes sobre o filme (sinopse titulo, ano e poster)
## 2. Arquitetura
![DiagramaArq](https://github.com/user-attachments/assets/ff638aaa-e96c-4107-a34d-883fee8b7421)


| Camada | Serviço | Descrição |
|--------|---------|-----------|
| Backend | ECS Fargate (ou EC2 + Docker) | API REST Node/Spring/… |
| Banco   | Amazon RDS              | PostgreSQL / MySQL em grupo de segurança privado |
| Gateway | Amazon API Gateway      | Rotas CRUD → ECS · `/report` → Lambda |
| Função  | AWS Lambda              | Consome a API, gera estatísticas JSON |
| CI/CD   | CodePipeline + GitHub   | push → build → ECR → deploy |

## 3. Como rodar localmente

Construir imagem e rodar o container

docker build -t java-filmes-app .

docker run -p 8080:8080 java-filmes-app

Acessar por: https://localhost:8080

## 4. Link do video 
https://youtu.be/MTmSzl9LiWQ
