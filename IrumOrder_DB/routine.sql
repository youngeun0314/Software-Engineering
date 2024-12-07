create table irum_order.routine
(
    routine_id     int auto_increment
        primary key,
    user_id        int                  not null,
    menu_id        int                  not null,
    menu_detail_id int                  not null,
    routine_day    int                  not null,
    routine_time   time                 not null,
    is_activated   tinyint(1) default 1 not null,
    constraint routine_ibfk_1
        foreign key (user_id) references irum_order.user (user_id),
    constraint routine_ibfk_2
        foreign key (menu_id) references irum_order.menu (menu_id),
    constraint routine_ibfk_3
        foreign key (menu_detail_id) references irum_order.menudetails (menu_detail_id)
);

create index menu_id
    on irum_order.routine (menu_id);

create index user_id
    on irum_order.routine (user_id);

