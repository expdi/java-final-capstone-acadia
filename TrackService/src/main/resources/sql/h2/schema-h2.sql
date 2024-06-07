drop table artist if exists;
drop table track if exists;
drop table artist_track if exists;
create memory table artist
(
    id integer primary key auto_increment,
    name varchar(255)
);
create memory table track
(
    duration   numeric(21),
    id     integer auto_increment
        primary key,
    issue_date date,
    media_type smallint
        constraint track_media_type_check
            check ((media_type >= 0) AND (media_type <= 3)),
    album      varchar(255),
    price      varchar(255),
    title      varchar(255)
);
create memory table artist_track
(
    artist_id integer not null
        constraint fk_artist_track_artist
            references artist,
    track_id  integer not null
        constraint fk_artist_track_track
            references track
);
