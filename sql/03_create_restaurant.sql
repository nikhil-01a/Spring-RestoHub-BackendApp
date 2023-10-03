-- @author rehmanh
-- @date 10/3/23
-- @spec create Restaurant table

create table if not exists restaurant (
    id serial primary key,
    name varchar(255) not null,
    street_address1 varchar(255) not null,
    street_address2 varchar(255),
    city varchar(50) not null,
    state varchar(50) not null,
    zip_code varchar(50) not null,
    url varchar(255),
    phoneNumber varchar(50),
    capacity integer not null
);