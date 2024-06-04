DO
$do$
BEGIN
   IF EXISTS (SELECT FROM pg_database WHERE datname = 'musicapp') THEN
ELSE
      PERFORM dblink_exec('dbname=' || current_database()  -- current db
                        , 'CREATE DATABASE musicapp');
END IF;
END
$do$;