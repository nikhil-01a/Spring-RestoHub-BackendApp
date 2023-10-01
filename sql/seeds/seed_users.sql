-- @author rehmanh
-- @date 10/1/23
-- @spec Create dummy users, roles and role mappings

insert into roles(role_type) values 
('SuperUser'), 
('Host'),
('Waiter');

insert into app_user(first_name, last_name, email_address, password) VALUES
('Tester', 'Person', 'testme@gmail.com', '12345'),
('Some', 'Guy', 'some@guy.com', '23456'),
('Another', 'Tester', 'tester@test.com', '99999'),
('Jimmy', 'Dude', 'dude@jim.com', '00000');

