-- Create database
drop schema if exists findprop;
create schema findprop;
use findprop;

-- Create read/write loader user
drop user if exists findprop_load_user;
create user findprop_load_user identified by 'B>a%Cx%It3u91h4C@KJ]';
grant all on findprop.* to findprop_load_user;
grant SYSTEM_USER ON *.* to findprop_load_user;

-- Create read-only app user
drop user if exists findprop_app_user;
create user findprop_app_user identified by 'a.UjIp](#v+UkA8qw^#P';
grant select, execute on findprop.* to findprop_app_user;