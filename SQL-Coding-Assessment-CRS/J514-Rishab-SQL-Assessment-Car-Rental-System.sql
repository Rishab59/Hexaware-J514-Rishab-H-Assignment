create database hexaware;
use hexaware;

create table vehicle (
    carid int primary key,
    make varchar(50),
    model varchar(50),
    year int,
    dailyrate decimal(10, 2),
    available int,
    passengercapacity int,
    enginecapacity int
);

create table customer (
    customerid int primary key,
    firstname varchar(50),
    lastname varchar(50),
    email varchar(100),
    phonenumber varchar(15)
);

create table lease (
    leaseid int primary key,
    carid int references vehicle(carid),
    customerid int references customer(customerid),
    startdate date,
    enddate date,
    leasetype varchar(15)
);

create table payment (
    paymentid int primary key,
    leaseid int references lease(leaseid),
    paymentdate date,
    amount decimal(10, 2)
);

show tables;

insert into vehicle values
(1, 'toyota', 'camry', 2022, 50.00, 1, 4, 1450),
(2, 'honda', 'civic', 2023, 45.00, 1, 7, 1500),
(3, 'ford', 'focus', 2022, 48.00, 0, 4, 1400),
(4, 'nissan', 'altima', 2023, 52.00, 1, 7, 1200),
(5, 'chevrolet', 'malibu', 2022, 47.00, 1, 4, 1800),
(6, 'hyundai', 'sonata', 2023, 49.00, 0, 7, 1400),
(7, 'bmw', '3 series', 2023, 60.00, 1, 7, 2499),
(8, 'mercedes', 'c-class', 2022, 58.00, 1, 8, 2599),
(9, 'audi', 'a4', 2022, 55.00, 0, 4, 2500),
(10, 'lexus', 'es', 2023, 54.00, 1, 4, 2500);

select * from vehicle;

insert into customer values
(1, 'john', 'doe', 'johndoe@example.com', '555-555-5555'),
(2, 'jane', 'smith', 'janesmith@example.com', '555-123-4567'),
(3, 'robert', 'johnson', 'robert@example.com', '555-789-1234'),
(4, 'sarah', 'brown', 'sarah@example.com', '555-456-7890'),
(5, 'david', 'lee', 'david@example.com', '555-987-6543'),
(6, 'laura', 'hall', 'laura@example.com', '555-234-5678'),
(7, 'michael', 'davis', 'michael@example.com', '555-876-5432'),
(8, 'emma', 'wilson', 'emma@example.com', '555-432-1098'),
(9, 'william', 'taylor', 'william@example.com', '555-321-6547'),
(10, 'olivia', 'adams', 'olivia@example.com', '555-765-4321');

select * from customer;

insert into lease values
(1, 1, 1, '2023-01-01', '2023-01-05', 'daily'),
(2, 2, 2, '2023-02-15', '2023-02-28', 'monthly'),
(3, 3, 3, '2023-03-10', '2023-03-15', 'daily'),
(4, 4, 4, '2023-04-20', '2023-04-30', 'monthly'),
(5, 5, 5, '2023-05-05', '2023-05-10', 'daily'),
(6, 4, 3, '2023-06-15', '2023-06-30', 'monthly'),
(7, 7, 7, '2023-07-01', '2023-07-10', 'daily'),
(8, 8, 8, '2023-08-12', '2023-08-15', 'monthly'),
(9, 3, 3, '2023-09-07', '2023-09-10', 'daily'),
(10, 10, 10, '2023-10-10', '2023-10-31', 'monthly');

select * from lease;

insert into payment values
(1, 1, '2023-01-03', 200.00),
(2, 2, '2023-02-20', 1000.00),
(3, 3, '2023-03-12', 75.00),
(4, 4, '2023-04-25', 900.00),
(5, 5, '2023-05-07', 60.00),
(6, 6, '2023-06-18', 1200.00),
(7, 7, '2023-07-03', 40.00),
(8, 8, '2023-08-14', 1100.00),
(9, 9, '2023-09-09', 80.00),
(10, 10, '2023-10-25', 1500.00);

select * from payment;

-- 1. Update the daily rate for a Mercedes car to 68.
update vehicle set dailyrate = 68.00 where make = 'mercedes' and carid = 8;
select * from vehicle;

-- 2. Delete a specific customer and all associated leases and payments
delete from payment where leaseid in (select leaseid from lease where customerid = 2);
delete from lease where customerid = 2;
delete from customer where customerid = 2;
select * from payment;
select * from lease;
select * from customer;

-- 3. Rename the "paymentDate" column in the Payment table to "transactionDate".
alter table payment rename column paymentdate to transactiondate;
select * from payment;

-- 4. Find a specific customer by email.
select * from customer where email = 'johndoe@example.com';

-- 5. Get active leases for a specific customer.
select * from lease where customerid = 1 and enddate >= now();

-- 6. Find all payments made by a customer with a specific phone number
select p.* from payment p inner join lease l on p.leaseid = l.leaseid inner join customer c on l.customerid = c.customerid where c.phonenumber = '555-555-5555';

-- 7. Calculate the average daily rate of all available cars.
select avg(dailyrate) as avg_rate from vehicle where available = 1;

-- 8. Find the car with the highest daily rate.
select * from vehicle where dailyrate = (select max(dailyrate) from vehicle);

-- 9. Retrieve all cars leased by a specific customer.
select v.* from vehicle v inner join lease l on v.carid = l.carid where l.customerid = 1;

-- 10. Find the details of the most recent lease.
select * from lease order by enddate desc limit 1;

-- 11. List all payments made in the year 2023.
select * from payment where year(transactiondate) = 2023;

-- 12. Retrieve customers who have not made any payments.
select * from customer where customerid not in (select l.customerid from lease l inner join payment p on l.leaseid = p.leaseid);

-- 13. Retrieve Car Details and Their Total Payments.
select v.*, sum(p.amount) as total_payments from vehicle v inner join lease l on v.carid = l.carid inner join payment p on l.leaseid = p.leaseid group by v.carid;

-- 14. Calculate Total Payments for Each Customer.
select c.*, sum(p.amount) as total_payments from customer c inner join lease l on c.customerid = l.customerid inner join payment p on l.leaseid = p.leaseid group by c.customerid;

-- 15. List Car Details for Each Lease.
select v.*, l.* from vehicle v inner join lease l on v.carid = l.carid;

-- 16. Retrieve Details of Active Leases with Customer and Car Information.
select l.*, c.*, v.* from lease l inner join customer c on l.customerid = c.customerid inner join vehicle v on l.carid = v.carid where l.enddate >= now();

-- 17. Find the Customer Who Has Spent the Most on Leases.
select c.*, sum(p.amount) as total_spent from customer c inner join lease l on c.customerid = l.customerid inner join payment p on l.leaseid = p.leaseid group by c.customerid order by total_spent desc limit 1;

-- 18. List All Cars with Their Current Lease Information
select v.*, l.* from vehicle v left join lease l on v.carid = l.carid and l.enddate >= now();
