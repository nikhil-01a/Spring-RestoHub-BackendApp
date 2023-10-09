-- @author rehmanh
-- @date 10/8/23
-- @spec change column name phonenumber to phone_number for JPA

alter table restaurant drop column phone_number;
alter table restaurant rename column phonenumber to phone_number;