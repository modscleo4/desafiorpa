# Desafio de programador JAVA RPA
Implementação de um robô de automação para busca de preços de produtos em sites de vendas.

## Descrição
"Somos uma empresa que fazemos cotação de preços e precisamos de uma automação que possa buscar os preços em sites de venda. Nosso foco é achar os três menores preços de um determinado produto em um site de vendas e trazer suas informações para o cliente."

## Especificações
- Sites de vendas:
    - Mercado Livre
    - Casas Bahia
- Produto: Xbox Series S

## Requisitos
- Java 17
- Maven
- SQLite
- Spring Boot
- Selenium
    - ChromeDriver
- Slf4j
- JUnit
- Docker

## Instruções
### Execução
```bash
mvn clean compile spring-boot:run
```

### Testes
```bash
mvn clean test
```

### Imagem Docker
```bash
docker build -t rpa .
```
