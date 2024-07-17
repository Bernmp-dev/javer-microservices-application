CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone BIGINT,
    correntista BOOLEAN,
    score_credito FLOAT,
    saldo_cc FLOAT
);