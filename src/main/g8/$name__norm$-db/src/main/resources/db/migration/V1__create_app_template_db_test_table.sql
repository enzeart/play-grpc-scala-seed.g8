CREATE TABLE IF NOT EXISTS app_template_db_test
(
    id          uuid primary key,
    text_array  text[]      not null,
    int8_array  int8[]      not null,
    int4_array  int4[]      not null,
    int2_array  int2[]      not null,
    date        date        not null,
    time        time        not null,
    timetz      timetz      not null,
    timestamptz timestamptz not null,
    interval    interval    not null
);
