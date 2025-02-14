CREATE TABLE IF NOT EXISTS user(
    email VARCHAR(100) PRIMARY KEY NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS product(
    id_product INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    country VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    imageUrl TEXT NOT NULL,
    bait VARCHAR(100),
    cook_tips VARCHAR(100),
    vegetables_tips VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS invoice(
    id_invoice INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email VARCHAR(100) NOT NULL,
    invoice_date DATE NOT NULL,
    total_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders(
    id_invoice INT NOT NULL,
    id_product INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_invoice, id_product),
    FOREIGN KEY (id_invoice) REFERENCES invoice(id_invoice),
    FOREIGN KEY (id_product) REFERENCES product(id_product)
);
