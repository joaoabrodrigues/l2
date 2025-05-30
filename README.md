# Pré-requisitos

Docker

## Para rodar:

```shell
docker-compose up --build
```

Este comando irá buildar a imagem do docker da aplicação e fará o download da imagem do PostgreSQL para subir localmente.

Após o build e o start dos containers, a app estará disponível na porta 8080.

O Swagger-UI estará disponível na URI: http://localhost:8080/swagger-ui/index.html


## Imporatnte

O projeto está usando o spring security e todas as rotas estão protegidas.

Para obter o token de autenticação, realize o login na rota: POST /users/login

Com as credenciais: 
```shell
curl --location 'http://localhost:8080/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "admin@example.com",
    "password": "admin"
}'
```

E utilize o token retornado como Bearer Token para chamar as demais rotas, como:

```shell
curl --location 'http://localhost:8080/boxes' \
     --header 'Content-Type: application/json' \
     --header 'Authorization: <TOKEN OBTIDO>' \
     --data '{
              "height": 50,
              "width": 80,
              "length": 60
            }'
```

```shell
curl --location 'http://localhost:8080/orders' \
     --header 'Content-Type: application/json' \
     --header 'Authorization: ••••••' \
     --data '{
        "pedidos": [
            {
                "pedido_id": 1,
                "produtos": [
                    {
                        "produto_id": "PS5",
                        "dimensoes": {
                            "altura": 40,
                            "largura": 10,
                            "comprimento": 25
                        }
                    },
                    {
                        "produto_id": "Volante",
                        "dimensoes": {
                            "altura": 40,
                            "largura": 30,
                            "comprimento": 30
                        }
                    }
                ]
            }
        ]
}'
```


## Itens pré-cadastrados

O usuário admin já está previamente cadastrado, assim como as caixas propostas no desafio (com ids 1,2,3)