CREATE TABLE echo_beacon (
    id BIGSERIAL PRIMARY KEY,
    numero_identificacao INT,
    status_conexao VARCHAR(255) NOT NULL
);
