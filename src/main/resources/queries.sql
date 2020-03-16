INSERT INTO product (product_name, price) VALUES ('Mobile', 15000);
INSERT INTO product (product_name, price) VALUES ('Television', 25000);
INSERT INTO product (product_name, price) VALUES ('Computer', 35000);
INSERT INTO product (product_name, price) VALUES ('IPad', 20000);
INSERT INTO product (product_name, price) VALUES ('Refrigerator', 24000);
INSERT INTO product (product_name, price) VALUES ('Air-Cooler', 40000);
INSERT INTO product (product_name, price) VALUES ('Cooker', 5000);
INSERT INTO product (product_name, price) VALUES ('Fan', 2000);
INSERT INTO product (product_name, price) VALUES ('Washing-Machine', 15000);
INSERT INTO product (product_name, price) VALUES ('Grinder', 7000);
INSERT INTO product (product_name, price) VALUES ('Water-Purifier', 12000);
INSERT INTO product (product_name, price) VALUES ('Water-Heater', 2500);
INSERT INTO product (product_name, price) VALUES ('Iron-Box', 1000);


INSERT INTO USER (user_name, PASSWORD) VALUES ('User1', 'Password1');
INSERT INTO USER (user_name, PASSWORD) VALUES ('User2', 'Password2');
INSERT INTO USER (user_name, PASSWORD) VALUES ('User3', 'Password3');

insert into payment_mode (payment_mode_name) values ('Credit Cards');
insert into payment_mode (payment_mode_name) values ('Debit Cards');
insert into payment_mode (payment_mode_name) values ('Net Banking');
insert into payment_mode (payment_mode_name) values ('Cash Cards');
insert into payment_mode (payment_mode_name) values ('Mobile Payments');
insert into payment_mode (payment_mode_name) values ('Cash on Delivery');

insert into delivery_mode (delivery_mode_name) values ('Click & collect');
insert into delivery_mode (delivery_mode_name) values ('Home delivery');
insert into delivery_mode (delivery_mode_name) values ('Two man delivery');
insert into delivery_mode (delivery_mode_name) values ('Locker system');

ALTER TABLE cart ALTER cart_id SET DEFAULT 0;
ALTER TABLE cart_product ALTER product_id SET DEFAULT 0;
ALTER TABLE product ALTER product_id SET DEFAULT 0;
ALTER TABLE user ALTER user_id SET DEFAULT 0;
ALTER TABLE purchase ALTER COLUMN purchase_id SET DEFAULT 0;
ALTER TABLE payment_mode ALTER COLUMN payment_mode_id SET DEFAULT 0;
ALTER TABLE delivery_mode ALTER COLUMN delivery_mode_id SET DEFAULT 0;

COMMIT;