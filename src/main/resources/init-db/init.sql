CREATE USER session_storage WITH password 'session_storage';

CREATE DATABASE "session_storage" WITH OWNER = postgres ENCODING = 'UTF8';

\connect "session_storage";
