-- @author rehmanh
-- @date 10/3/23
-- @spec Create dummy Restaurants

insert into restaurant(name, street_address1, street_address2, city, state, zip_code, url, phoneNumber, capacity) 
values
('Burgers R Us', '1 Burger Way', '', 'Hoboken', 'NJ', '07030', '', '1112221234', 20),
('The Chicken Shop', '23 Chicken Rd', '3', 'Hoboken', 'NJ', '07030', 'https://www.google.com', '1112221234', 24),
('Steak n Shakes', '101 Boulevard Rd', '', 'Minneapolis', 'MN', '55345', 'https://www.linkedin.com', '1234322342', 200),
('Rice and Water', '123 Washington St', '', 'Hoboken', 'NJ', '07030', 'https://www.ricenwater.com', '1112222454', 28),
('Spicy Hall', '2121 Avenue Rd', '', 'Hoboken', 'NJ', '07031', '', '', 180),
('Kebab King', '17 Nelson Ave', '', 'Jersey City', 'NJ', '07307', '', '', 32),
('McDonalds', '2 Washington St', '', 'Hoboken', 'NJ', '07030', 'https://www.google.com', '2223334567', 240),
('Cookies', '1 Castle Point Terr', '', 'Hoboken', 'NJ', '07030', '', '9879877897', 40),
('Pizza NYC', '100 JFK Blvd', 'Apt 4', 'Jersey City', 'NJ', '07307', 'https://www.pizzanyc.com', '6544567657', 100);

