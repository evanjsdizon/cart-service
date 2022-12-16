INSERT INTO users (name, address, email, password)
    VALUES ('Johnny Depp', '104 Dunbar St., New York, NY 10031', 'scissorhands@gmail.com', 'cGFzc3dvcmQ=');
INSERT INTO users (name, address, email, password)
    VALUES ('Amber Heard', '468 Euclid Street, New York, NY 10029', 'princessmera@gmail.com', 'd29yZHBhc3M=');
INSERT INTO users (name, address, email, password)
    VALUES ('Chadwick Boseman', '9241 Walt Whitman Dr., Brooklyn, NY 11209', 'wakandaforever@gmail.com', 'd2Fzc3BvcmQ=');

INSERT INTO product (name, price, count_remaining)
    VALUES ('Elden Ring', 1999.95, 69);
INSERT INTO product (name, price, count_remaining)
    VALUES ('Elden Ring Deluxe Edition', 2699.95, 0);
INSERT INTO product (name, price, count_remaining)
    VALUES ('God of War Ragnarok', 3490.00, 30);
INSERT INTO product (name, price, count_remaining)
    VALUES ('Call of Duty: Modern Warfare II', 3490.00, 12);

INSERT INTO cart (user_id, total_price)
    VALUES (3, 3490.00);

INSERT INTO cart_product (cart_id, product_id)
    VALUES (1, 3);