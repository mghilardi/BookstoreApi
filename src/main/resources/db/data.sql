-- customers
INSERT INTO public.customers(id, name, created_at)
VALUES (101, 'Michael', current_timestamp);
INSERT INTO public.customers(id, name, created_at)
VALUES (102, 'Anna', current_timestamp);
INSERT INTO public.customers(id, name, created_at)
VALUES (103, 'Larissa', current_timestamp);
INSERT INTO public.customers(id, name, created_at)
VALUES (104, 'Maria', current_timestamp);
INSERT INTO public.customers(id, name, created_at)
VALUES (105, 'Peter', current_timestamp);
INSERT INTO public.customers(id, name, created_at)
VALUES (106, 'George', current_timestamp);

-- books
INSERT INTO public.books(id, author, description, isbn, price, quantity, title)
VALUES (101, 'Daniel A', 'The 1st book', '1234567890111', 11.99, 100, 'The 1st book');
INSERT INTO public.books(id, author, description, isbn, price, quantity, title)
VALUES (102, 'Daniel A', 'The 2nd book', '1234567890112', 12.99, 200, 'The 2nd book');
INSERT INTO public.books(id, author, description, isbn, price, quantity, title)
VALUES (103, 'Daniel A', 'The 3rd book', '1234567890123', 13.99, 300, 'The 3rd book');
INSERT INTO public.books(id, author, description, isbn, price, quantity, title)
VALUES (104, 'Scarlet A', 'The 1st book', '1234567890211', 11.99, 100, 'The 1st book');
INSERT INTO public.books(id, author, description, isbn, price, quantity, title)
VALUES (105, 'Scarlet A', 'The 2nd book', '1234567890212', 12.99, 200, 'The 2nd book');

-- orders
INSERT INTO public.orders (created_at, customer_id, id)
VALUES (current_timestamp, 102, 101);
INSERT INTO public.order_book (order_id, book_id)
VALUES (101, 101);
INSERT INTO public.order_book (order_id, book_id)
VALUES (101, 102);
INSERT INTO public.order_book (order_id, book_id)
VALUES (101, 104);
INSERT INTO public.order_book (order_id, book_id)
VALUES (101, 105);