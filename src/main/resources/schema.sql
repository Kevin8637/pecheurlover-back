CREATE TABLE IF NOT EXISTS user(
    id_user INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS product(
    id_product INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name_product VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL,
    imageURL VARCHAR(100),
    cook_tips VARCHAR(100),
    vegetables_tips VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS invoice(
    id_invoice INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user INT,
    id_product INT,
    invoice DATE NOT NULL,
    total_price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user(id_user),
    FOREIGN KEY (id_product) REFERENCES product(id_product)
);

CREATE TABLE IF NOT EXISTS orders(
    id_order INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_invoice INT NOT NULL,
    email VARCHAR(100) NOT NULL,
    total_price DOUBLE NOT NULL,
    FOREIGN KEY (id_invoice) REFERENCES invoice(id_invoice)
)