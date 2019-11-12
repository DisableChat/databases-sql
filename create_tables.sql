create table summoner(
    Summoner_name   varchar(15) not null,
    MMR             int         not null,
    Division        varchar(15) not null,
    Honor_level     int         not null,
    Mastery_score   int         not null,
    wins            int         not null,
    losses          int         not null,
    primary key     (Summoner_name));

create table champion(
    Champion_name   varchar(15) not null,
    possition       char(3)     not null,
    passive         varchar(20) not null,
    q               varchar(20) not null,
    w               varchar(20) not null,
    e               varchar(20) not null,
    ultimate        varchar(20) not null,
    role            varchar(15) not null,
    primary key     (Champion_name));

create table rune_page(
    Rune_id_no      char(9)         not null,
    name            varchar(15),
    keystone        varchar(15)     not null,
    adapt_keystone  varchar(15)     not null,
    Sname           varchar(15)     not null,
    primary key     (Rune_id_no),
    foreign key     (Sname) references  summoner(Summoner_name));

create table riot_reward(
    CID         char(9)     not null,
    challenger_border char(3) not null,
    SumName     varchar(15) not null,
    chal_date   char(8)     not null,
    primary key (CID),
    foreign key (SumName)   references  summoner(Summoner_name));

create table play(
    Summoner_n  varchar(15) not null,
    Champion_n  varchar(15) not null,
    primary key (Summoner_n, Champion_n),
    foreign key (Summoner_n)    references  summoner(Summoner_name),
    foreign key (Champion_n)    references  champion(Champion_name));

create table equip(
    Name_of_champ varchar(15) not null,
    rune_id_num   char(9)     not null,
    primary key   (Name_of_champ, rune_id_num),
    foreign key   (Name_of_champ) references champion(Champion_name),
    foreign key   (rune_id_num)   references rune_page(Rune_id_no));
