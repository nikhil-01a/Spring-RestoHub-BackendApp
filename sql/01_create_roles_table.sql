-- @author rehmanh
-- @date 10/1/23
-- @spec create Roles table

create table if not exists roles (
    id serial primary key,
    role_type varchar(255) unique not null
);