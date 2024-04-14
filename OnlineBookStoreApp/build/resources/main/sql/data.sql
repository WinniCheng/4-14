insert into book (author, availability, bookname, description, price)
values ('John', true, 'Rainbow', 'A book about rainbow', 50.5);

INSERT INTO users(username, password, fullname, email, address) VALUES ('keith', '{noop}keithpw', 'Keith Chan', 'keith@gmail.com', 'Ma On Sha');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

INSERT INTO users(username, password, fullname, email, address) VALUES ('mary', '{noop}marypw', 'Mary Chan', 'mary@gmail.com', 'Ma On Sha');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');