-- @author rehmanh
-- @date 10/1/23
-- @spec create AppUser table

create table if not exists app_user (
    id serial primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email_address varchar(255) unique not null,
    password varchar(30) not null, -- will be hashed/salted, not plain text
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp
);