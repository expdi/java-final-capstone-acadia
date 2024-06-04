delete from artist_track;
delete from artist;
alter sequence artist_id_seq restart;
delete from track;
alter sequence track_id_seq restart;


insert into public.artist (name)
values  ('Taylor Swift');

insert into public.track (duration, issue_date, media_type, album, price, title)
values  (3, '2024-06-03', 1, 'Red', '3.50', 'I Almost Do');

insert into public.artist (name)
values  ('Pink Floyd');

insert into public.track (duration, issue_date, media_type, album, price, title)
values  (3, '1983-06-03', 1, 'Dark Side Of The Moon', '3.50', 'On The Run');


insert into public.artist_track (artist_id, track_id)
values  (1, 1);

insert into public.artist_track (artist_id, track_id)
values  (2, 2);
