create table parking_order (order_id bigint not null,
parking_lot_name varchar(255),
car_number varchar(255),
 ending_time varchar(255),
 starting_time varchar(255),
 status integer not null,
  parking_lot_id bigint,
  primary key (order_id));
  alter table parking_order add constraint FK701du5mj20ogq7fyhccisnj foreign key (parking_lot_id) references parking_lot;