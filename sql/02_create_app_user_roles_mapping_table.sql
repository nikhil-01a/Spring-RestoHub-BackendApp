-- @author rehmanh
-- @date 10/1/23
-- @spec create AppUserRoles mapping table

create table if not exists app_user_roles (
    id serial primary key,
    app_user_id int not null references app_user(id),
    role_id int not null references roles(id),
    constraint uq_app_user_role unique(app_user_id, role_id)
);