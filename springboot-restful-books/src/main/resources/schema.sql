create table book (
    id integer not null,
    author varchar(255),
    release integer,
    title varchar(255),
primary key (id));

create table review (
    id integer not null,
    description varchar(255),
    book_id integer,
primary key (id));