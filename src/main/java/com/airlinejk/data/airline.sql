/**
 * Author:  Javier Amador
 * Created: 1 abr. 2021
 */

-- TABLE DROPS

drop table airplaneTypes cascade constraints;
drop table airplanes cascade constraints;
drop table schedules cascade constraints;
drop table routes cascade constraints;
drop table cities cascade constraints;
drop table countries cascade constraints;
drop table flights cascade constraints;
drop table reservations cascade constraints;
drop table paymentTypes cascade constraints;
drop table tickets cascade constraints;
drop table userss cascade constraints;

-- SEQUENCE DROPS

drop sequence seq_id_tickets; 
drop sequence seq_id_reservations;
drop sequence seq_id_flights;
drop sequence seq_id_schedules;

-- CREATE TABLES 

PROMPT ==========================
PROMPT OBJECTS
PROMPT ==========================

create table airplaneTypes (id varchar2(20) not null,  
							model varchar2(20) not null, 
							brand varchar2(20) not null, 
							passengersQ number not null, 
							rowQ number not null, 
							columnQ number not null) tablespace system;

							
create table airplanes (id varchar2(20) not null,
					anno number not null,
                    airplaneType_id varchar2(20) not null
                    ) tablespace system;
					

create table schedules (id number not null,
                    weekday varchar2(10) not null,
					departureTime varchar2(5) not null
                    ) tablespace system;
					
					
create table countries (id varchar2(10) not null,
                    name varchar2(20) not null
                    ) tablespace system;					


create table cities (id varchar2(20) not null,
                    name varchar2(30) not null,
					country_id varchar2(10) not null
                    ) tablespace system;


create table routes (id varchar2(20) not null,
                    durationHours number not null,
					durationMinutes number not null,
					origin varchar2(10) not null,
					destination varchar2(10) not null,
					airplane_id varchar2(20) not null,
					schedule number not null
                    ) tablespace system;					


create table flights (id number not null,
                    route varchar2(20) not null,
					departureDate date not null,
					returnDate date,
					price number not null,
					discount number not null,
					availableSeats number not null
                    ) tablespace system;
					
					
create table reservations (id number not null,
                    flightInfo varchar2(300) not null,
					flightId number not null,
					userID varchar2(20) not null,
					totalPrice number not null,
					airplane_id varchar2(20) not null,
					typeOfPayment varchar2(10) not null,
					seatQuantity number not null,
					checkedInQuantity number not null
                    ) tablespace system;


create table tickets (id number not null,
                    reservation number not null,
					flight number not null,
					rowN number,
					columnN number
                    ) tablespace system;
					
					
create table paymentTypes (code varchar2(10) not null,
                    description varchar2(20) not null
                    ) tablespace system;					

					
create table userss (username varchar2(20) not null,
					password varchar2(20) not null,
                    name varchar2(25) not null,
                    last_name varchar2(25) not null, 
					email varchar2(30) not null,
					dateOfBirth date not null,
                    address varchar2(50) not null, 
                    workPhone varchar2(15),
					cellphone varchar2(15) not null,					
                    isAdmin number(1) default 0 not null
                    ) tablespace system;					


-- CREATE SEQUENCE

create sequence seq_id_tickets start with 1 increment by 1 cache 2;
create sequence seq_id_reservations start with 1 increment by 1 cache 2;
create sequence seq_id_flights start with 1 increment by 1 cache 2;
create sequence seq_id_schedules start with 1 increment by 1 cache 2;


-- ALTER TABLE (PRIMARY KEY)

alter table airplaneTypes add constraint airplaneTypes_pk primary key(id) using index tablespace system;
alter table airplanes add constraint airplanes_pk primary key(id) using index tablespace system;
alter table schedules add constraint schedules_pk primary key(id) using index tablespace system;
alter table countries add constraint countries_pk primary key(id) using index tablespace system;
alter table cities add constraint cities_pk primary key(id) using index tablespace system;
alter table routes add constraint routes_pk primary key(id) using index tablespace system;
alter table flights add constraint flights_pk primary key(id) using index tablespace system;
alter table reservations add constraint reservations_pk primary key(id) using index tablespace system;
alter table tickets add constraint tickets_pk primary key(id) using index tablespace system;
alter table paymentTypes add constraint paymentTypes_pk primary key(code) using index tablespace system;
alter table userss add constraint userss_pk primary key(username) using index tablespace system;


-- ALTER TABLE (FOREIGN KEY)

alter table airplanes add constraint airplanes_fk foreign key (airplaneType_id) references airplaneTypes on delete cascade;
alter table cities add constraint cities_fk foreign key (country_id) references countries on delete cascade;
alter table routes add constraint routes_ori_fk foreign key (origin) references cities on delete cascade;
alter table routes add constraint routes_des_fk foreign key (destination) references cities on delete cascade;
alter table routes add constraint routes_air_fk foreign key (airplane_id) references airplanes on delete cascade;
alter table routes add constraint routes_schdl_fk foreign key (schedule) references schedules on delete cascade;
alter table flights add constraint flights_route_fk foreign key (route) references routes on delete cascade;
alter table reservations add constraint reservations_use_fk foreign key (userID) references userss on delete cascade;
alter table reservations add constraint reservations_pay_fk foreign key (typeOfPayment) references paymentTypes on delete cascade;
alter table tickets add constraint tickets_res_fk foreign key (reservation) references reservations on delete cascade;
alter table tickets add constraint tickets_fli_fk foreign key (flight) references flights on delete cascade;


-- PROCEDURES

-- INSERTS


					
create or replace procedure ins_user(PUName in varchar2, PPass in varchar2, PName in varchar2,
 PLName in varchar2, PEmail in varchar2, PDBirth in date, PAdd in varchar2, PWPhone in varchar2,
 PCPhone in varchar2, PAdmin in number) is
begin
    insert into userss(username, password, name, last_name, email, dateOfBirth, address, workPhone, cellphone, isAdmin) 
	values(PUName, PPass, PName, PLName, PEmail, PDBirth, PAdd, PWPhone, PCPhone, PAdmin);
    commit;
end ins_user;
/
show error					

create or replace procedure ins_arplType(PId in varchar2, PModel in varchar2, PBrand in varchar2,
 PPassangerQ in number, PRowQ in number, PColumnQ in number) is
begin
    insert into airplaneTypes(id, model, brand, passengersQ, rowQ, columnQ) 
	values(PId,PModel,PBrand,PPassangerQ,PRowQ,PColumnQ);
    commit;
end ins_arplType;
/
show error

create or replace procedure ins_airplane(PId in varchar2, PAnno in number, PAType in varchar2) is
begin
    insert into airplanes(id, anno, airplaneType_id) 
	values(PId,PAnno,PAType);
    commit;
end ins_airplane;
/
show error

create or replace procedure ins_schedule(PWDay in varchar2, PDTime in varchar2) is
begin
    insert into schedules(id, weekday, departureTime) 
	values(seq_id_schedules.nextval,PWDay,PDTime);
    commit;
end ins_schedule;
/
show error

create or replace procedure ins_country(PId in varchar2, PName in varchar2) is
begin
    insert into countries(id, name) 
	values(PId,PName);
    commit;
end ins_country;
/
show error

create or replace procedure ins_city(PId in varchar2, PName in varchar2, PCId in varchar2) is
begin
    insert into cities(id, name, country_id) 
	values(PId,PName,PCId);
    commit;
end ins_city;
/
show error

create or replace procedure ins_payType(PCode in varchar2, PDesc in varchar2) is
begin
    insert into paymentTypes(code, description) 
	values(PCode,PDesc);
    commit;
end ins_payType;
/
show error

create or replace procedure ins_route(PId in varchar2, PDHours in number, PDMin in number, POri in varchar2, PDes in varchar2, PAId in varchar2, PSId in number) is
begin
    insert into routes(id, durationHours, durationMinutes, origin, destination, airplane_id, schedule) 
	values(PId,PDHours,PDMin,POri,PDes,PAId,PSId);
    commit;
end ins_route;
/
show error

create or replace procedure ins_flight(PRoute in varchar2, PDDate in date, PRDate in date, PPrice in number, PDiscount in number, PSeats in number) is
begin
    insert into flights(id, route, departureDate, returnDate, price, discount, availableSeats) 
	values(seq_id_flights.nextval,PRoute,PDDate,PRDate,PPrice,PDiscount,PSeats);
    commit;
end ins_flight;
/
show error

create or replace procedure ins_reservation(PFlight in varchar2,PFID in number, PUser in varchar2, PPrice in number, PAId in varchar2, 
PPType in varchar2, PSeatQ in number) is
begin
    insert into reservations(id, flightInfo, flightId, userID, totalPrice, airplane_id, typeOfPayment, seatQuantity, checkedInQuantity) 
	values(seq_id_reservations.nextval,PFlight, PFID, PUser, PPrice, PAId, PPType, PSeatQ,0);
    commit;
end ins_reservation;
/
show error

create or replace procedure ins_ticket(PRes in number, PFlight in number, PRow in number, PCol in number) is
begin
    insert into tickets(id, reservation, flight, rowN, columnN) 
	values(seq_id_tickets.nextval,PRes, PFlight, PRow, PCol);
    commit;
end ins_ticket;
/
show error


-- UPDATES 

				
create or replace procedure upd_userInfo(PUName in varchar2, PEmail in varchar2,
 PAdd in varchar2, PWPhone in varchar2, PCPhone in varchar2) is
begin
    update userss set email = PEmail, address = PAdd, workPhone = PWPhone, cellphone = PCPhone
	where username = PUName;
    commit; 
end upd_userInfo;
/
show error	

create or replace procedure upd_userPassW(PUName in varchar2, PPass in varchar2) is
begin
    update userss set password = PPass
	where username = PUName;
    commit; 
end upd_userPassW;
/
show error					

create or replace procedure upd_arplType(PId in varchar2, PModel in varchar2, PBrand in varchar2,
 PPassangerQ in number, PRowQ in number, PColumnQ in number) is
begin
    update airplaneTypes set model = PModel, brand = PBrand, passengersQ = PPassangerQ, rowQ = PRowQ, columnQ = PColumnQ
	where id = PId;
    commit; 
end upd_arplType;
/
show error


create or replace procedure upd_airplane(PId in varchar2, PAnno in number, PAType in varchar2) is
begin
    update airplanes set anno = PAnno, airplaneType_id = PAType where id = PId;
    commit;
end upd_airplane;
/
show error


create or replace procedure upd_schedule(PId in number, PWDay in varchar2, PDTime in varchar2) is
begin
    update schedules set weekday = PWDay, departureTime = PDTime where id = PId;
    commit;
end upd_schedule;
/
show error

create or replace procedure upd_country(PId in varchar2, PName in varchar2) is
begin
    update countries set name = PName where id = PId;
    commit;
end upd_country;
/
show error

create or replace procedure upd_city(PId in varchar2, PName in varchar2, PCId in varchar2) is
begin
    update cities set name = PName, country_id = PCId where id = PId;
    commit;
end upd_city;
/
show error

create or replace procedure upd_payType(PCode in varchar2, PDesc in varchar2) is
begin
    update paymentTypes set description = PDesc where code = PCode;
    commit;
end upd_payType;
/
show error

create or replace procedure upd_route(PId in varchar2, PDHours in number, PDMin in number, POri in varchar2, PDes in varchar2, PAId in varchar2, PSId in number) is
begin
    update routes set durationHours = PDHours, durationMinutes = PDMin, origin = POri, destination = PDes, airplane_id = PAId, schedule = PSId where id = PId;
    commit;
end upd_route;
/
show error

create or replace procedure upd_flightInfo(PId in number, PRoute in varchar2, PDDate in date, PRDate in date, PPrice in number, PDiscount in number, PSeats in number) is
begin
    update flights set route = PRoute, departureDate = PDDate, returnDate = PRDate, price = PPrice, discount = PDiscount, availableSeats = PSeats
	where id = PId;
    commit;
end upd_flightInfo;
/
show error

create or replace procedure upd_flightSeats(PId in number, PSeats in number) is
begin
    update flights set availableSeats = PSeats
	where id = PId;
    commit;
end upd_flightSeats;
/
show error

create or replace procedure upd_resCSeats(PId in number, PCSeats in number) is
begin
    update reservations set checkedInQuantity = PCSeats
	where id = PId;
    commit;
end upd_resCSeats;
/
show error

create or replace procedure upd_ticket(PId in number, PRow in number, PCol in number) is
begin
    update tickets set rowN = PRow, columnN = PCol
	where id = PId;
    commit;
end upd_ticket;
/
show error


-- DELETES
create or replace procedure del_user(PUName in varchar2) is
begin
    delete from userss where username = PUName;
    commit;
end del_user;
/
show error

create or replace procedure del_arplType(PId in varchar2) is
begin
    delete from airplaneTypes where id = PId;
    commit;
end del_arplType;
/
show error


create or replace procedure del_airplane(PId in varchar2) is
begin
    delete from airplanes where id = PId;
    commit;
end del_airplane;
/
show error

create or replace procedure del_schedule(PId in number) is
begin
    delete from schedules where id = PId;
    commit;
end del_schedule;
/
show error

create or replace procedure del_country(PId in varchar2) is
begin
    delete from countries where id = PId;
    commit;
end del_country;
/
show error

create or replace procedure del_city(PId in varchar2) is
begin
    delete from cities where id = PId;
    commit;
end del_city;
/
show error

create or replace procedure del_payType(PCode in varchar2) is
begin
    delete from paymentTypes where code = PCode;
    commit;
end del_payType;
/
show error

create or replace procedure del_route(PId in varchar2) is
begin
    delete from routes where id = PId;
    commit;
end del_route;
/
show error

create or replace procedure del_flight(PId in number) is
begin
    delete from flights where id = PId;
    commit;
end del_flight;
/
show error

create or replace procedure del_reservation(PId in number) is
begin
    delete from reservations where id = PId;
    commit;
end del_reservation;
/
show error

create or replace procedure del_ticket(PId in number) is
begin
    delete from tickets where id = PId;
    commit;
end del_ticket;
/
show error

--=================================================================================================================

PROMPT ==========================
PROMPT INSERTS 
PROMPT ==========================


insert into userss values ('123','123', 'Javier', 'Amador Delgado', 'jaad@mail.com',
TO_DATE('30/05/2000', 'DD/MM/YYYY'),'Escazu Centro, Costa Rica','1111-1111','2222-2222',1);

insert into userss values ('321','321', 'Kevin', 'Flores Garcia', 'kvfg@mail.com',
TO_DATE('24/01/1999', 'DD/MM/YYYY'),'Esparza','8631-3721','2232-2416',0);

insert into userss values ('basicUser1','basic', 'Jonh', 'Doe Smith', 'jd@mail.com',
TO_DATE('20/06/2001', 'DD/MM/YYYY'),'New York, USA','1111-1111','2222-2222',0);

insert into userss values ('basicUser2','basic', 'Jane', 'Doe Smith', 'jds@mail.com',
TO_DATE('12/03/1998', 'DD/MM/YYYY'),'Toronto, Canada','1111-1111','2222-2222',0);

insert into userss values ('basicUser3','basic', 'Fulano', 'Mengano Sutano', 'fms@mail.com',
TO_DATE('30/05/2000', 'DD/MM/YYYY'),'San Jose, Costa Rica','1111-1111','2222-2222',0);


insert into userss values ('Admin','admin', 'Alberto', 'Achio Morales', 'aaadmin@mail.com',
TO_DATE('30/05/2000', 'DD/MM/YYYY'),'Escazu Centro, Costa Rica','1111-1111','2222-2222',1);

insert into countries values ('CR','Costa Rica');
insert into countries values ('USA','Estados Unidos');
insert into countries values ('MEX','Mexico');
insert into countries values ('CHI','China');
insert into countries values ('CAN','Canada');
insert into countries values ('UK','Inglaterra');
insert into countries values ('ITA','Italia');
insert into countries values ('ESP','España');


insert into cities values ('SJ','San Jose','CR');
insert into cities values ('NY','Nueva York','USA');
insert into cities values ('CDMX','Ciudad de Mexico','MEX');
insert into cities values ('PK','Pekin','CHI');
insert into cities values ('LD','London','UK');
insert into cities values ('TOR','TORONTO','CAN');
insert into cities values ('RM','Roma','ITA');
insert into cities values ('MAD','Madrid','ESP');

insert into airplaneTypes values ('B777','777','Boeing',36,6,6);
insert into airplaneTypes values ('B748','748','Boeing',36,6,6);
insert into airplaneTypes values ('AB90','90','Airbus',36,6,6);
insert into airplaneTypes values ('AB72','72','Airbus',36,6,6);
insert into airplaneTypes values ('XP99','99','XPlane',36,6,6);
insert into airplaneTypes values ('XP6O','60','XPlane',36,6,6);

insert into airplanes values ('Z8C91',2014,'B777');
insert into airplanes values ('W8R62',2017,'B748');
insert into airplanes values ('ABC23',2011,'AB90');
insert into airplanes values ('XYZ48',2014,'AB72');
insert into airplanes values ('E6RP4',2013,'XP99');
insert into airplanes values ('M3R35',2019,'XP6O');


insert into schedules values (seq_id_schedules.nextval,'Lunes','14:00');
insert into schedules values (seq_id_schedules.nextval,'Martes','08:00');
insert into schedules values (seq_id_schedules.nextval,'Miercoles','13:00');
insert into schedules values (seq_id_schedules.nextval,'Jueves','17:00');
insert into schedules values (seq_id_schedules.nextval,'Viernes','13:00');
insert into schedules values (seq_id_schedules.nextval,'Sabado','20:00');
insert into schedules values (seq_id_schedules.nextval,'Domingo','06:00');

insert into paymentTypes values ('BTC','Bitcoin');
insert into paymentTypes values ('CR','Colones');
insert into paymentTypes values ('CRC','CR Coin');
insert into paymentTypes values ('DOL','Dolares');
insert into paymentTypes values ('EU','Euros');


insert into routes values ('CR-USA',4,25,'SJ','NY','Z8C91',1);
insert into routes values ('CR-CHI',13,50,'SJ','PK','W8R62',2);
insert into routes values ('USA-ITA',9,35,'NY','RM','E6RP4',3);
insert into routes values ('CAN-USA',2,30,'TOR','NY','ABC23',4);
insert into routes values ('UK-ESP',1,40,'LD','MAD','XYZ48',5);
insert into routes values ('CHI-MEX',14,20,'PK','CDMX','M3R35',6);
				
insert into routes values ('USA-CR',4,25,'NY','SJ','Z8C91',1);
insert into routes values ('CHI-CR',13,50,'PK','SJ','W8R62',2);
insert into routes values ('ITA-USA',9,35,'RM','NY','E6RP4',3);
insert into routes values ('USA-CAN',2,30,'NY','TOR','ABC23',4);
insert into routes values ('ESP-UK',1,40,'MAD','LD','XYZ48',5);
insert into routes values ('MEX-CHI',14,20,'CDMX','PK','M3R35',6);

insert into flights values (seq_id_flights.nextval,'CR-USA',TO_DATE('23/05/2021', 'DD/MM/YYYY'),null,300000,0.0,36);
insert into flights values (seq_id_flights.nextval,'CR-CHI',TO_DATE('27/05/2021', 'DD/MM/YYYY'),null,500000,0.10,48);
insert into flights values (seq_id_flights.nextval,'CR-USA',TO_DATE('28/05/2021', 'DD/MM/YYYY'),TO_DATE('29/05/2021', 'DD/MM/YYYY'),600000,0.0,36);
insert into flights values (seq_id_flights.nextval,'CAN-USA',TO_DATE('01/06/2021', 'DD/MM/YYYY'),null,150000,0.0,90);
insert into flights values (seq_id_flights.nextval,'CHI-MEX',TO_DATE('05/06/2021', 'DD/MM/YYYY'),TO_DATE('01/07/2021', 'DD/MM/YYYY'),1000000,0.0,60);
insert into flights values (seq_id_flights.nextval,'UK-ESP',TO_DATE('27/06/2021', 'DD/MM/YYYY'),null,251400,0.20,72);
insert into flights values (seq_id_flights.nextval,'USA-ITA',TO_DATE('28/06/2021', 'DD/MM/YYYY'),null,851800,0.0,99);
insert into flights values (seq_id_flights.nextval,'USA-CR',TO_DATE('29/06/2021', 'DD/MM/YYYY'),null,300000,0.50,36);
insert into flights values (seq_id_flights.nextval,'CHI-CR',TO_DATE('30/06/2021', 'DD/MM/YYYY'),null,250000,0.50,36);

insert into reservations values (seq_id_reservations.nextval, 'CR-USA - 23/05/2021 14:00', 1, '321', 300000, 'Z8C91', 'CR', 1,0);
insert into reservations values (seq_id_reservations.nextval, 'CR-USA - 23/05/2021 14:00', 1, 'basicUser1', 600000, 'Z8C91', 'BTC', 2,0);
insert into reservations values (seq_id_reservations.nextval, 'CR-USA - 23/05/2021 14:00', 1, 'basicUser3', 900000, 'Z8C91', 'CR', 3,0);
insert into reservations values (seq_id_reservations.nextval, 'CR-CHI - 27/05/2021 08:00', 2, '321', 500000, 'W8R62', 'CR', 1,0);
insert into reservations values (seq_id_reservations.nextval, 'CAN-USA - 01/06/2021 17:00', 4, 'basicUser2', 150000, 'ABC23', 'DOL', 1,0);



--insert into tickets values(seq_id_tickets.nextval,1, 1, 0, 5);
--insert into tickets values(seq_id_tickets.nextval,1, 1, 1, 5);
--insert into tickets values(seq_id_tickets.nextval,1, 1, 2, 5);
--insert into tickets values(seq_id_tickets.nextval,1, 1, 3, 5);

commit;