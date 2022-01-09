
--Insert values into Receipts
INSERT INTO receipts(receipt_date, payment_value, iva) VALUES('12-JAN-17', 500, 15);
INSERT INTO receipts(receipt_date, payment_value, iva) VALUES('11-JUN-17', 400, 15);
INSERT INTO receipts(receipt_date, payment_value, iva) VALUES('15-JUN-18', 200, 15);
INSERT INTO receipts(receipt_date, payment_value, iva) VALUES('17-JAN-18', 570, 15);
INSERT INTO receipts(receipt_date, payment_value, iva) VALUES('12-JAN-18', 180, 15);

--Insert values into Locations
INSERT INTO locations(name_location, altitude, latitude, longitude) VALUES('Parque do Estrela', 45, 45,-55);
INSERT INTO locations(name_location, altitude, latitude, longitude) VALUES('Parque Afonso Henriques', 45, -45,55);
INSERT INTO locations(name_location, altitude, latitude, longitude) VALUES('Parque do Danny', 45, 45,170);
INSERT INTO locations(name_location, altitude, latitude, longitude) VALUES('Bulhï¿½o', 45, 45,55);
INSERT INTO locations(name_location, altitude, latitude, longitude) VALUES('Livraria Lello', 45, 25,25);
INSERT INTO locations(name_location, altitude, latitude, longitude) VALUES('Parque da Baixa', 45, 17,25);
INSERT INTO locations(name_location, altitude, latitude, longitude) VALUES('Parque do Oliveira', 45, 15,25);

--Insert values into Users

<<<<<<< Updated upstream
INSERT INTO users(username, email,password, salt, name, weight, height, credit_card, has_active_rental, points,average_speed) VALUES('danny45','daniel@gmail.pt', 'danny123', '01de723', 'Daniel Oliveira', 60, 178, 4736120391637658,1,0,15.0);
INSERT INTO users(username, email,password, salt, name, weight, height, credit_card, has_active_rental, points,average_speed) VALUES('ugandanSoldier68','uganda@gmail.pt', 'danny123', 'ca72ad', 'José Silva', 60, 178, 4786317932462252,0,0,15.0);
INSERT INTO users(username, email,password, salt, name, weight, height, credit_card, has_active_rental, points,average_speed) VALUES('maria_silva99','maria_silva321@gmail.pt', 'danny123', 'ce2980b', 'Maria SIlva', 60, 178, 4462868732135705,0,500,15.0);
INSERT INTO users(username, email,password, salt, name, weight, height, credit_card, has_active_rental, points,average_speed) VALUES('esteves_esteves','dr_esteves@gmail.pt', 'danny123', 'bd8e07e0', 'João Esteves', 60, 178, 4817058497241227,0,600,15.0);
INSERT INTO users(username, email,password, salt, name, weight, height, credit_card, has_active_rental, points,average_speed) VALUES('trengo45','rodrigo_maia@gmail.pt', 'danny123', 'd605a3e2', 'Rodrigo Maia', 60, 178, 4010736652828905,0,200,15.0);
INSERT INTO users(username, email,password, salt, name, weight, height, credit_card, has_active_rental, points,average_speed) VALUES('master123','cnt_master@isep.ipp.pt', 'danny123', '94fb848', 'Ascenção Lopes', 60, 178, 4289911425648799,1,110,15.0);
INSERT INTO users(username, email,password, salt, name, weight, height, credit_card, has_active_rental, points,average_speed) VALUES('alexalex','alex@gmail.pt', 'danny123', '87a115a6', 'Alexandre Rocha', 60, 178, 4738668428133008,1,2000,15.0);
=======
INSERT INTO users(username, email,password, salt, name, totalDistance, height, credit_card, has_active_rental, points) VALUES('danny45','daniel@gmail.pt', 'danny123', '01de723', 'Daniel Oliveira', 60, 178, 4736120391637658,1,0);
INSERT INTO users(username, email,password, salt, name, totalDistance, height, credit_card, has_active_rental, points) VALUES('ugandanSoldier68','uganda@gmail.pt', 'danny123', 'ca72ad', 'Josï¿½ Silva', 60, 178, 4786317932462252,0,0);
INSERT INTO users(username, email,password, salt, name, totalDistance, height, credit_card, has_active_rental, points) VALUES('maria_silva99','maria_silva321@gmail.pt', 'danny123', 'ce2980b', 'Maria SIlva', 60, 178, 4462868732135705,0,500);
INSERT INTO users(username, email,password, salt, name, totalDistance, height, credit_card, has_active_rental, points) VALUES('esteves_esteves','dr_esteves@gmail.pt', 'danny123', 'bd8e07e0', 'Joï¿½o Esteves', 60, 178, 4817058497241227,0,600);
INSERT INTO users(username, email,password, salt, name, totalDistance, height, credit_card, has_active_rental, points) VALUES('trengo45','rodrigo_maia@gmail.pt', 'danny123', 'd605a3e2', 'Rodrigo Maia', 60, 178, 4010736652828905,0,200);
INSERT INTO users(username, email,password, salt, name, totalDistance, height, credit_card, has_active_rental, points) VALUES('master123','cnt_master@isep.ipp.pt', 'danny123', '94fb848', 'Ascenï¿½ï¿½o Lopes', 60, 178, 4289911425648799,1,110);
INSERT INTO users(username, email,password, salt, name, totalDistance, height, credit_card, has_active_rental, points) VALUES('alexalex','alex@gmail.pt', 'danny123', '87a115a6', 'Alexandre Rocha', 60, 178, 4738668428133008,1,2000);
>>>>>>> Stashed changes

--Insert values into Batteries

INSERT INTO batteries(max_charge, current_charge, voltage, totalDistance) VALUES(50,25,10,5);
INSERT INTO batteries(max_charge, current_charge, voltage, totalDistance) VALUES(50,35,10,8);
INSERT INTO batteries(max_charge, current_charge, voltage, totalDistance) VALUES(60,25,10,5);

--Insert values into Bicycles

<<<<<<< Updated upstream
INSERT INTO bicycles(id_bike,state, weight, aerodynamic_coefficient,frontal_area) VALUES('TEST_1',1,25, 1.10,5.0);
INSERT INTO bicycles(id_bike,state, weight, aerodynamic_coefficient,frontal_area) VALUES('TEST_2',1,25, 1.10,5.0);
INSERT INTO bicycles(id_bike,state, weight, aerodynamic_coefficient,frontal_area) VALUES('TEST_3',3,45, 1.10,5.0);
INSERT INTO bicycles(id_bike,state, weight, aerodynamic_coefficient,frontal_area) VALUES('TEST_4',3,45, 1.10,5.0);
INSERT INTO bicycles(id_bike,state, weight, aerodynamic_coefficient,frontal_area) VALUES('TEST_5',1,35, 1.10,5.0);
INSERT INTO bicycles(id_bike,state, weight, aerodynamic_coefficient,frontal_area) VALUES('TEST_6',2,35, 1.10,5.0);
INSERT INTO bicycles(id_bike,state, weight, aerodynamic_coefficient,frontal_area) VALUES('TEST_7',3,35, 1.10,5.0);
=======
INSERT INTO bicycles(state, totalDistance, aerodynamic_coefficient) VALUES(1,25, 1.10);
INSERT INTO bicycles(state, totalDistance, aerodynamic_coefficient) VALUES(1,25, 1.10);
INSERT INTO bicycles(state, totalDistance, aerodynamic_coefficient) VALUES(3,45, 1.10);
INSERT INTO bicycles(state, totalDistance, aerodynamic_coefficient) VALUES(3,45, 1.10);
INSERT INTO bicycles(state, totalDistance, aerodynamic_coefficient) VALUES(1,35, 1.10);
INSERT INTO bicycles(state, totalDistance, aerodynamic_coefficient) VALUES(2,35, 1.10);
INSERT INTO bicycles(state, totalDistance, aerodynamic_coefficient) VALUES(3,35, 1.10);
>>>>>>> Stashed changes

--Insert values into Electric_Bicycles
INSERT INTO electric_bicycles(id_bike, id_battery) VALUES('TEST_1',1);
INSERT INTO electric_bicycles(id_bike, id_battery) VALUES('TEST_2',1);
INSERT INTO electric_bicycles(id_bike, id_battery) VALUES('TEST_3',2);

--Insert values into Road_Bicycles

INSERT INTO road_bicycles(id_bike) VALUES('TEST_4');
INSERT INTO road_bicycles(id_bike) VALUES('TEST_5');

--Insert values into Mountain_Bicycles

INSERT INTO mountain_bicycles(id_bike) VALUES ('TEST_6');
INSERT INTO mountain_bicycles(id_bike) VALUES ('TEST_7');

--Insert values into Interest_points

INSERT INTO interest_points(id_interest_point) VALUES(4);
INSERT INTO interest_points(id_interest_point) VALUES(5);

--Insert values into Slots
INSERT INTO slots(max_capacity, free_slots,type) VALUES(20, 5,0);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(10, 9,1);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(10, 0,1);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(40, 18,1);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(40, 18,0);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(40, 18,0);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(40, 18,1);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(40, 18,1);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(40, 18,1);
INSERT INTO slots(max_capacity, free_slots,type) VALUES(40, 18,1);
--Insert values into Electric_Slots
INSERT INTO electric_slots(id_slots,charge_rate, intensity) VALUES(1,50,50);
INSERT INTO electric_slots(id_slots,charge_rate, intensity) VALUES(2,50,50);
INSERT INTO electric_slots(id_slots,charge_rate, intensity) VALUES(3,50,50);
INSERT INTO electric_slots(id_slots,charge_rate, intensity) VALUES(4,50,50);
INSERT INTO electric_slots(id_slots,charge_rate, intensity) VALUES(5,50,50);

--Insert values into Normal_Slots
INSERT INTO normal_slots(id_slots) VALUES(6);
INSERT INTO normal_slots(id_slots) VALUES(7);
INSERT INTO normal_slots(id_slots) VALUES(8);
INSERT INTO normal_slots(id_slots) VALUES(9);
INSERT INTO normal_slots(id_slots) VALUES(10);
--Insert values into Park

INSERT INTO parks(id_park, id_normal_slots, id_electric_slots) VALUES(1,6,1);
INSERT INTO parks(id_park, id_normal_slots, id_electric_slots) VALUES(2,7,2);
INSERT INTO parks(id_park, id_normal_slots, id_electric_slots) VALUES(3,8,3);
INSERT INTO parks(id_park, id_normal_slots, id_electric_slots) VALUES(6,9,4);
INSERT INTO parks(id_park, id_normal_slots, id_electric_slots) VALUES(7,10,5);

--Insert values into Rentals

INSERT INTO rentals(id_bike, username, pick_up_park, left_at_park, unlock_date, lock_date, state_rental,rental_payment) VALUES('TEST_4','danny45',1,2,TO_TIMESTAMP('2017-07-02 06:14:00', 'YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-07-02 16:30:00', 'YYYY-MM-DD HH24:MI:SS'),1,0);
INSERT INTO rentals(id_bike, username, pick_up_park, left_at_park, unlock_date, lock_date, state_rental,rental_payment) VALUES('TEST_5','alexalex',3,1,TO_TIMESTAMP('2017-03-02 06:14:00', 'YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-03-02 16:30:00', 'YYYY-MM-DD HH24:MI:SS'),1,0);
INSERT INTO rentals(id_bike, username, pick_up_park, left_at_park, unlock_date, lock_date, state_rental,rental_payment) VALUES('TEST_1','master123',2,3,TO_TIMESTAMP('2017-02-02 06:14:00', 'YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-02-02 16:30:00', 'YYYY-MM-DD HH24:MI:SS'),1,0);

--Insert values into Receipt_Rental

INSERT INTO Receipt_Rental(id_receipt, id_rental) VALUES(1,1);
INSERT INTO Receipt_Rental(id_receipt, id_rental) VALUES(2,2);
INSERT INTO Receipt_Rental(id_receipt, id_rental) VALUES(3,3);

--Insert values int Bicycle_slots
INSERT INTO Bicycle_Slot(id_bike, id_slot, left_at_date) VALUES('TEST_2',1,TO_TIMESTAMP('2017-07-02 06:14:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Bicycle_Slot(id_bike, id_slot, left_at_date) VALUES('TEST_6',3,TO_TIMESTAMP('2017-07-02 06:14:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Bicycle_Slot(id_bike, id_slot, left_at_date) VALUES('TEST_7',2,TO_TIMESTAMP('2017-07-02 06:14:00', 'YYYY-MM-DD HH24:MI:SS'));

--Insert values int Path_Information
INSERT INTO Path_Information(id_initial_park,id_final_park,wind_speed,wind_dir,kinectic_friction) VALUES(1,2,30,0,50);
INSERT INTO Path_Information(id_initial_park,id_final_park,wind_speed,wind_dir,kinectic_friction) VALUES(2,1,22,1,50);
INSERT INTO Path_Information(id_initial_park,id_final_park,wind_speed,wind_dir,kinectic_friction) VALUES(3,2,12,-1,50);
INSERT INTO Path_Information(id_initial_park,id_final_park,wind_speed,wind_dir,kinectic_friction) VALUES(3,1,5,0,50);
INSERT INTO Path_Information(id_initial_park,id_final_park,wind_speed,wind_dir,kinectic_friction) VALUES(2,3,15,1,50);


