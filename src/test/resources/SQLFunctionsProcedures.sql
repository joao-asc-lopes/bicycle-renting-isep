----------------------------------------------------Battery---------------------------------------------------------------------
--Returns a battery given its id
CREATE OR REPLACE FUNCTION get_battery(id INTEGER)
RETURN SYS_REFCURSOR
AS
  cur_battery SYS_REFCURSOR;
BEGIN
  OPEN cur_battery FOR SELECT BATTERIES.battery_id,BATTERIES.max_charge,BATTERIES.current_charge,BATTERIES.weight FROM batteries
  WHERE batteries.battery_id = get_battery.id;
  RETURN cur_battery;
END;
/ 
--Adds a battery to the database.
CREATE OR REPLACE FUNCTION add_battery(maximum_cap NUMBER, current_cap NUMBER, weight NUMBER)
RETURN INTEGER IS l_id INTEGER;
BEGIN
    INSERT INTO batteries(max_charge, current_charge, voltage, weight) VALUES(maximum_cap, current_cap, 220, weight)
    RETURNING BATTERY_ID INTO l_id;
    RETURN l_id;
END;
/
--Removes a battery from the database.
CREATE OR REPLACE PROCEDURE remove_battery(id INTEGER)
IS
BEGIN
  DELETE FROM Batteries WHERE Batteries.battery_id = remove_battery.id;
IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20001, 'No battery in the DB with that ID');
END IF;
END;
/

----------------------------------------------------Bicycle---------------------------------------------------------------------
--Get a road bicycle by is ID
CREATE OR REPLACE FUNCTION get_road_bicycle(id_bike VARCHAR)
RETURN SYS_REFCURSOR
AS
  curBicycle SYS_REFCURSOR;
BEGIN
  OPEN curBicycle FOR SELECT BICYCLES.* FROM BICYCLES 
  INNER JOIN ROAD_BICYCLES ON BICYCLES.id_bike = ROAD_BICYCLES.id_bike
  WHERE ROAD_BICYCLES.id_bike = get_road_bicycle.id_bike;
  RETURN curBicycle;
END;
/

--Get a mountain bicycle by is ID
CREATE OR REPLACE FUNCTION get_mountain_bicycle(id_bike VARCHAR)
RETURN SYS_REFCURSOR
AS
  curBicycle SYS_REFCURSOR;
BEGIN
  OPEN curBicycle FOR SELECT BICYCLES.* FROM BICYCLES 
  INNER JOIN MOUNTAIN_BICYCLES ON BICYCLES.id_bike = MOUNTAIN_BICYCLES.id_bike
  WHERE MOUNTAIN_BICYCLES.id_bike = get_mountain_bicycle.id_bike;
  RETURN curBicycle;
END;
/

--Get a electric bicycle by is ID
CREATE OR REPLACE FUNCTION get_electric_bicycle(id_bike VARCHAR)
RETURN SYS_REFCURSOR
AS
  curBicycle SYS_REFCURSOR;
BEGIN
  OPEN curBicycle FOR SELECT BICYCLES.id_bike, BICYCLES.state, BATTERIES.BATTERY_ID, BICYCLES.WEIGHT, BICYCLES.aerodynamic_coefficient,BICYCLES.frontal_area FROM BICYCLES 
  INNER JOIN ELECTRIC_BICYCLES ON BICYCLES.id_bike = ELECTRIC_BICYCLES.id_bike
  INNER JOIN BATTERIES ON BATTERIES.BATTERY_ID=ELECTRIC_BICYCLES.id_battery 
  WHERE ELECTRIC_BICYCLES.id_bike = get_electric_bicycle.id_bike;
  RETURN curBicycle;
END;
/

--Get the type of the bicycle by id ID
CREATE OR REPLACE FUNCTION get_bicycle_type(bike VARCHAR2)
RETURN INTEGER AS type_bicycle INTEGER;
l_type INTEGER;
l_id INTEGER;
BEGIN
    l_type:=3;
	SELECT count(id_bike) INTO l_id FROM ELECTRIC_BICYCLES WHERE ELECTRIC_BICYCLES.id_bike = get_bicycle_type.bike;
	IF l_id = 0 THEN
		SELECT count(id_bike) INTO l_id FROM MOUNTAIN_BICYCLES WHERE MOUNTAIN_BICYCLES.id_bike = get_bicycle_type.bike;
		IF l_id = 0 THEN
			SELECT count(id_bike) INTO l_id FROM ROAD_BICYCLES WHERE ROAD_BICYCLES.id_bike = get_bicycle_type.bike;
			IF l_id = 0 THEN RAISE_APPLICATION_ERROR(-20070,'No bicycle in the DB with that ID');
			ELSE
				l_type:=2; --RoadBicycle
			END IF;
		ELSE
			l_type:=1; --MountainBicycle
		END IF;
	ELSE
		l_type:=0; --ElectricBicycle
	END IF;
	RETURN l_type;
END;
/


--Returns all the mountain bicycles in the system.
CREATE OR REPLACE FUNCTION get_all_mountain_bicycles
RETURN SYS_REFCURSOR
AS
    cur_bicycles SYS_REFCURSOR;
BEGIN
     OPEN cur_bicycles FOR SELECT BICYCLES.* FROM BICYCLES
     INNER JOIN MOUNTAIN_BICYCLES ON BICYCLES.ID_BIKE=MOUNTAIN_BICYCLES.id_bike;
     RETURN cur_bicycles;
END;
/

--returns all the electric bicycles in the database.
CREATE OR REPLACE FUNCTION get_all_electric_bicycles
RETURN SYS_REFCURSOR
AS
    cur_bicycles SYS_REFCURSOR;
BEGIN
     OPEN cur_bicycles FOR SELECT BICYCLES.id_bike,BICYCLES.state, BATTERIES.battery_id,BICYCLES.weight,BICYCLES.aerodynamic_coefficient, BICYCLES.frontal_area FROM BICYCLES
     INNER JOIN ELECTRIC_BICYCLES ON  BICYCLES.id_bike = ELECTRIC_BICYCLES.id_bike 
     INNER JOIN BATTERIES ON BATTERIES.battery_id=ELECTRIC_BICYCLES.id_battery;
     return cur_bicycles;
END;
/
--returns all the road bicycles in the database.
CREATE OR REPLACE FUNCTION get_all_road_bicycles
RETURN SYS_REFCURSOR
AS
   cur_bicycles SYS_REFCURSOR;
BEGIN
    OPEN cur_bicycles FOR SELECT BICYCLES.* FROM BICYCLES
    INNER JOIN ROAD_BICYCLES ON BICYCLES.id_bike = ROAD_BICYCLES.id_bike;
    RETURN cur_bicycles;
END;
/
--adds a road bicycle to the system.
CREATE OR REPLACE PROCEDURE add_road_bicycle(id VARCHAR, state NUMBER, weight NUMBER, coefficient NUMBER,frontal_area NUMBER)
IS
BEGIN
  INSERT INTO bicycles(id_bike, state, weight,aerodynamic_coefficient, frontal_area) VALUES(id,state, weight, coefficient,frontal_area);
  INSERT INTO road_bicycles VALUES(id);
END;
/
--adds a mountain bicycle to the database.
CREATE OR REPLACE PROCEDURE add_mountain_bicycle(id VARCHAR, state NUMBER, weight NUMBER, coefficient NUMBER, frontal_area NUMBER)
IS
BEGIN
  INSERT INTO bicycles(id_bike, state, weight, aerodynamic_coefficient, frontal_area) VALUES(id, state, weight, coefficient,frontal_area);
  INSERT INTO mountain_bicycles VALUES(id);
END;
/
--adds an electric bicycle to the database.
CREATE OR REPLACE PROCEDURE add_electric_bicycle(id VARCHAR,state NUMBER, weight NUMBER, id_battery INTEGER, coefficient NUMBER, frontal_area NUMBER)
IS
BEGIN
  INSERT INTO bicycles(id_bike, state, weight,aerodynamic_coefficient,frontal_area) VALUES(id, state, weight,coefficient, frontal_area);
  INSERT INTO electric_bicycles VALUES(id, id_battery);
END;
/
--Removes a bicycle from the database.
CREATE OR REPLACE PROCEDURE remove_bicycle(id VARCHAR)
IS
BEGIN
  DELETE FROM bicycles WHERE bicycles.id_bike LIKE remove_bicycle.id;
  IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20003, 'No Bicycle with that ID on the DB');
  END IF;
  DELETE FROM road_bicycles WHERE road_bicycles.id_bike LIKE remove_bicycle.id;
  DELETE FROM electric_bicycles WHERE electric_bicycles.id_bike LIKE remove_bicycle.id;
  DELETE FROM mountain_bicycles WHERE mountain_bicycles.id_bike LIKE remove_bicycle.id;
END;
/
--updates a bicycle's state and / or weight
CREATE OR REPLACE PROCEDURE update_bicycle(id VARCHAR, state NUMBER, weight NUMBER)
IS
BEGIN
    UPDATE Bicycles SET Bicycles.state = update_bicycle.state,
                        Bicycles.weight = update_bicycle.weight
    WHERE Bicycles.id_bike LIKE update_bicycle.id;
    IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20003, 'No Road/Mountain Bicycle with that ID on the DB');
    END IF;
END;
/
--updates as electric bicycle with a new battery / weight / state
CREATE OR REPLACE PROCEDURE update_electric_bicycle(id_bike VARCHAR ,state NUMBER, weight NUMBER, id_battery NUMBER)
IS
BEGIN
        UPDATE Bicycles SET Bicycles.state = update_electric_bicycle.state, Bicycles.weight = update_electric_bicycle.weight WHERE Bicycles.id_bike = update_electric_bicycle.id_bike;
        UPDATE Electric_Bicycles SET Electric_Bicycles.id_battery = update_electric_bicycle.id_battery WHERE Electric_Bicycles.id_bike LIKE update_electric_bicycle.id_bike;
        IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20003, 'No Electric Bicycle with that ID on the DB');
        END IF;
END;
/

-------------------------------------------------------Location----------------------------------------------------------------

create or replace FUNCTION get_interest_point(id_interest_point INTEGER)
RETURN SYS_REFCURSOR
AS
    cur_location SYS_REFCURSOR;
BEGIN
    OPEN cur_location FOR SELECT LOCATIONS.* FROM Locations
    INNER JOIN INTEREST_POINTS ON INTEREST_POINTS.id_interest_point=LOCATIONS.id_location
    WHERE Locations.id_location = get_interest_point.id_interest_point;
    RETURN cur_location;
END;
/

CREATE OR REPLACE FUNCTION get_park(id_park INTEGER)
RETURN SYS_REFCURSOR AS cur_location SYS_REFCURSOR;
BEGIN
    OPEN cur_location FOR SELECT LOCATIONS.*, PARKS.id_normal_slots,PARKS.id_electric_slots FROM PARKS, Locations
    WHERE Locations.id_location = get_park.id_park AND PARKS.id_park=LOCATIONS.id_location;
    RETURN cur_location;
END;
/


CREATE OR REPLACE FUNCTION get_location_by_coordinates(latitude NUMBER, longitude NUMBER)
RETURN SYS_REFCURSOR AS cur_location SYS_REFCURSOR;
l_id_location INTEGER;
l_type INTEGER;
BEGIN
    SELECT id_location INTO l_id_location FROM LOCATIONS WHERE LOCATIONS.latitude=get_location_by_coordinates.latitude
    AND LOCATIONS.longitude=get_location_by_coordinates.longitude;
    SELECT count(id_park) INTO l_type FROM PARKS WHERE PARKS.id_park=l_id_location;
    IF l_type=0 THEN
        SELECT count(id_interest_point) INTO l_type FROM INTEREST_POINTS WHERE INTEREST_POINTS.id_interest_point=l_id_location;
        IF l_type=0 THEN RAISE_APPLICATION_ERROR(-20070,'A error occurred in this function');
        END IF;
        l_type:=0; --InterestPoint
        OPEN cur_location FOR SELECT l_type,LOCATIONS.id_location,LOCATIONS.name_location,LOCATIONS.altitude FROM LOCATIONS 
        WHERE LOCATIONS.id_location=l_id_location;
    ELSE
        l_type:=1; --Park
        OPEN cur_location FOR SELECT l_type, LOCATIONS.id_location,LOCATIONS.name_location,LOCATIONS.altitude,
        PARKS.id_normal_slots,PARKS.id_electric_slots FROM LOCATIONS
        INNER JOIN PARKS ON LOCATIONS.id_location=PARKS.id_park
        WHERE LOCATIONS.id_location=l_id_location;
    END IF;
    RETURN cur_location;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20060,'No location in the DB with that coordinates');
END;
/

CREATE OR REPLACE PROCEDURE add_interest_point(v_name_location VARCHAR2, v_altitude NUMBER, v_latitude NUMBER, v_longitude NUMBER)
AS
l_id INTEGER;
BEGIN
INSERT INTO LOCATIONS(name_location,altitude,latitude,longitude) VALUES(v_name_location,v_altitude,v_latitude,v_longitude)
RETURNING id_location INTO l_id;
INSERT INTO INTEREST_POINTS VALUES(l_id);
END;
/

CREATE OR REPLACE PROCEDURE add_park(v_name_location VARCHAR2, v_latitude NUMBER, v_longitude NUMBER, v_normal_max_capacity NUMBER,v_normal_free_slots NUMBER,v_electric_max_capacity NUMBER, v_electric_free_slots NUMBER,v_charge_rate NUMBER,v_intensity NUMBER,v_altitude NUMBER)
AS
l_id_location INTEGER;
l_id_normal_slots INTEGER;
l_id_electric_slots INTEGER;
BEGIN
INSERT INTO LOCATIONS(name_location,altitude,latitude,longitude) VALUES(v_name_location,v_altitude,v_latitude,v_longitude)
RETURNING id_location INTO l_id_location;
INSERT INTO SLOTS(MAX_CAPACITY,FREE_SLOTS,TYPE) VALUES(v_normal_max_capacity,v_normal_free_slots,0)
RETURNING ID_SLOTS INTO l_id_normal_slots;
INSERT INTO NORMAL_SLOTS VALUES(l_id_normal_slots);
INSERT INTO SLOTS(MAX_CAPACITY,FREE_SLOTS,TYPE) VALUES(v_electric_max_capacity,v_electric_free_slots,0)
RETURNING ID_SLOTS INTO l_id_electric_slots;
INSERT INTO ELECTRIC_SLOTS VALUES(l_id_electric_slots,v_charge_rate, v_intensity);
INSERT INTO PARKS(ID_PARK,ID_NORMAL_SLOTS,ID_ELECTRIC_SLOTS) VALUES(l_id_location, l_id_normal_slots,l_id_electric_slots);
END;
/

CREATE OR REPLACE PROCEDURE remove_interest_points(v_id INTEGER)
AS
BEGIN
DELETE FROM INTEREST_POINTS WHERE INTEREST_POINTS.id_interest_point=v_id;
IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20015,'No Interest Point in the DB with that ID');
END IF;
DELETE FROM LOCATIONS WHERE LOCATIONS.id_location=v_id;
END;
/

CREATE OR REPLACE PROCEDURE remove_park(v_id INTEGER)
AS
BEGIN
DELETE FROM PARKS WHERE PARKS.id_park=v_id;
IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20015,'No Park in the DB with that ID');
END IF;
DELETE FROM LOCATIONS WHERE LOCATIONS.id_location=v_id;
END;
/

CREATE OR REPLACE PROCEDURE update_interest_point (id_location INTEGER, new_name VARCHAR)
IS
BEGIN
    UPDATE (SELECT id_location, name_location FROM LOCATIONS L 
    INNER JOIN INTEREST_POINTS IP ON IP.id_interest_point= L.id_location) t
    SET t.name_location = update_interest_point.new_name 
    WHERE t.id_location = update_interest_point.id_location;
    IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20005,'No Interest Point in the DB with that ID');
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE update_park (id_location INTEGER, new_name VARCHAR, normal_max_capacity NUMBER, normal_free_slots NUMBER, electric_max_capacity NUMBER, electric_free_slots NUMBER)
IS
l_id_normal_slots INTEGER;
l_id_electric_slots INTEGER;
BEGIN
    SELECT id_normal_slots INTO l_id_normal_slots FROM PARKS WHERE PARKS.id_park=id_location;
    SELECT id_electric_slots INTO l_id_electric_slots FROM PARKS WHERE PARKS.id_park=id_location;
    UPDATE (SELECT id_location, name_location FROM LOCATIONS L 
    INNER JOIN PARKS P ON P.id_park= L.id_location) t
    SET t.name_location = update_park.new_name 
    WHERE t.id_location = update_park.id_location;
    UPDATE SLOTS SET SLOTS.max_capacity=normal_max_capacity, SLOTS.free_slots=normal_free_slots
    WHERE SLOTS.id_slots = l_id_normal_slots;
    UPDATE SLOTS SET SLOTS.max_capacity=electric_max_capacity, SLOTS.free_slots=electric_free_slots
    WHERE SLOTS.id_slots = l_id_electric_slots;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20005,'No Park in the DB with that ID');
END;
/
--adds electric slots to the database.
CREATE OR REPLACE PROCEDURE add_electric_slots(free_slots INTEGER, max_capacity INTEGER, charge_rate NUMBER, intensity NUMBER)
AS
l_id_slot INTEGER;
BEGIN
    INSERT INTO slots(free_slots, max_capacity) VALUES(free_slots, max_capacity)returning slots.id_slots into l_id_slot;
    INSERT INTO electric_slots VALUES(l_id_slot,charge_rate, intensity);
END;
/
--adds normal slots to the database
CREATE OR REPLACE PROCEDURE add_normal_slots(free_slots INTEGER, max_capacity INTEGER)
AS
l_id_slot INTEGER;
BEGIN
    INSERT INTO slots(free_slots, max_capacity) VALUES(free_slots, max_capacity)returning slots.id_slots into l_id_slot;
    INSERT INTO normal_slots VALUES(l_id_slot);
END;
/

--Returns all bicycles parked in a Park
CREATE OR REPLACE PROCEDURE bike_into_park(id_bike VARCHAR, id_park INTEGER)
is
cursor cur_slots_park_normal IS
    SELECT *
    FROM Slots
    WHERE id_slots IN ( SELECT pa.id_normal_slots
                        FROM Parks pa
                        WHERE pa.id_park = id_park);
                        
 l_cur_slot_normal cur_slots_park_normal%rowtype;
                        
cursor cur_slots_park_electric IS
    SELECT *
    FROM Slots
    WHERE id_slots IN ( SELECT pa.id_electric_slots
                        FROM Parks pa
                        WHERE pa.id_park = id_park);
                        
 l_cur_slot_electric cur_slots_park_electric%rowtype;
                     
l_test INTEGER;
PARK_FULL EXCEPTION;
BEGIN
SELECT count(id_park) INTO l_test FROM PARKS WHERE PARKS.id_park=bike_into_park.id_park;
IF l_test=0 THEN RAISE_APPLICATION_ERROR(-20089,'No Park in the DB with that ID');
END IF;
SELECT count(id_bike) INTO l_test FROM BICYCLES WHERE BICYCLES.id_bike LIKE bike_into_park.id_bike;
IF l_test=0 THEN RAISE_APPLICATION_ERROR(-20089,'No Bicycle in the DB with that ID');
END IF;
l_test:=0;
SELECT COUNT(id_bike) INTO l_test FROM ELECTRIC_BICYCLES WHERE ELECTRIC_BICYCLES.id_bike LIKE bike_into_park.id_bike;
IF l_test=0 THEN
    OPEN cur_slots_park_normal;
        FETCH cur_slots_park_normal INTO l_cur_slot_normal;
    CLOSE cur_slots_park_normal;
    IF l_cur_slot_normal.free_slots = 0 THEN RAISE PARK_FULL;
    END IF;
    INSERT INTO Bicycle_Slot VALUES(bike_into_park.id_bike,l_cur_slot_normal.id_slots, CURRENT_TIMESTAMP);
    UPDATE Slots s
        set s.free_slots = s.free_slots -1
        WHERE s.id_slots = l_cur_slot_normal.id_slots;
ELSE
    OPEN cur_slots_park_electric;
                FETCH cur_slots_park_electric INTO l_cur_slot_electric;
            CLOSE cur_slots_park_electric;
            
            IF l_cur_slot_electric.free_slots = 0 THEN
            RAISE PARK_FULL;
            END IF;
            
            INSERT INTO Bicycle_Slot VALUES(bike_into_park.id_park, l_cur_slot_electric.id_slots, CURRENT_TIMESTAMP);
            
            UPDATE Slots ss
            set ss.free_slots = ss.free_slots -1
            WHERE ss.id_slots = l_cur_slot_electric.id_slots;
END IF;
EXCEPTION
WHEN PARK_FULL THEN 
   RAISE_APPLICATION_ERROR(-20002, 'The park is full for this type of bicycle!');
END;
/

--updates the place where the normal bicycles will be stored in the database when a park is deleted.
CREATE OR REPLACE PROCEDURE restock_bikes_normal(id_park INTEGER)
IS
cursor cur_park_bikes is
    SELECT id_bike
    FROM Bicycle_slot
    WHERE id_slot IN (SELECT pa.id_normal_slots
                       FROM Parks pa
                       WHERE pa.id_park = restock_bikes_normal.id_park);
                       
cursor cur_all_slots is
    SELECT *
    FROM Slots
    WHERE id_slots NOT IN (select pa.id_normal_slots
                            FROM parks pa
                            WHERE pa.id_park = restock_bikes_normal.id_park)
    AND free_slots > 0;
                       
    l_cur_park_bikes cur_park_bikes%rowtype; -- Bicycle in a normal slot from the park we are trying to remove.
    l_cur_all_slots cur_all_slots%rowtype;
    v_aux NUMBER;
    
BEGIN 
OPEN cur_park_bikes;
    LOOP
    v_aux := 0;
    FETCH cur_park_bikes INTO l_cur_park_bikes;
      EXIT WHEN cur_park_bikes%NOTFOUND;
         OPEN cur_all_slots;
            LOOP
            FETCH  cur_all_slots into l_cur_all_slots; -- Corresponde.
            EXIT WHEN cur_all_slots%NOTFOUND or v_aux = 1;
            IF l_cur_all_slots.free_slots > 0  THEN
            INSERT INTO Bicycle_slot VALUES(l_cur_park_bikes.id_bike,l_cur_all_slots.id_slots,CURRENT_TIMESTAMP);
            UPDATE Slots s
            set free_slots = l_cur_all_slots.free_slots -1
            WHERE s.id_slots = l_cur_all_slots.id_slots;
            v_aux := 1;
            end if;
            END LOOP;
            CLOSE cur_all_slots;
    END LOOP;
    CLOSE cur_park_bikes;

END;
/

CREATE OR REPLACE PROCEDURE restock_bikes_electric (id_park INTEGER)
IS
cursor cur_park_bikes is
    SELECT id_bike
    FROM Bicycle_slot
    WHERE id_slot IN (SELECT pa.id_electric_slots
                       FROM Parks pa
                       WHERE pa.id_park = restock_bikes_electric.id_park);
                       
cursor cur_all_slots is
    SELECT *
    FROM Slots
    WHERE id_slots NOT IN (select pa.id_normal_slots
                            FROM parks pa
                            WHERE pa.id_park = restock_bikes_electric.id_park)
    AND free_slots > 0;
                       
    l_cur_park_bikes cur_park_bikes%rowtype; -- Bicycle in a normal slot from the park we are trying to remove.
    l_cur_all_slots cur_all_slots%rowtype;
    v_aux NUMBER;
    
BEGIN 
OPEN cur_park_bikes;
    LOOP
    v_aux := 0;
    FETCH cur_park_bikes INTO l_cur_park_bikes;
      EXIT WHEN cur_park_bikes%NOTFOUND;
         OPEN cur_all_slots;
            LOOP
            FETCH  cur_all_slots into l_cur_all_slots; -- Corresponde.
            EXIT WHEN cur_all_slots%NOTFOUND or v_aux = 1;
            IF l_cur_all_slots.free_slots > 0  THEN
            INSERT INTO Bicycle_Slot VALUES(l_cur_park_bikes.id_bike,l_cur_all_slots.id_slots, CURRENT_TIMESTAMP);
            UPDATE Slots s
            set free_slots = l_cur_all_slots.free_slots -1
            WHERE s.id_slots = l_cur_all_slots.id_slots;
            v_aux := 1;
            end if;
            END LOOP;
            CLOSE cur_all_slots;
    END LOOP;
    CLOSE cur_park_bikes;

END;
/

create or replace FUNCTION get_All_Parks 
RETURN SYS_REFCURSOR
AS
    curAllParks SYS_REFCURSOR;
BEGIN
    OPEN curAllParks FOR
    SELECT l.id_location, l.name_location, l.altitude, l.latitude, l.longitude,pa.id_normal_slots, pa.id_electric_slots  
    FROM Locations l, Parks pa 
    WHERE l.id_location = pa.id_park AND l.id_location IN ( SELECT id_park From Parks);


RETURN curAllParks;
END;
/

create or replace FUNCTION get_All_Interest_Points
RETURN SYS_REFCURSOR
AS
    curAllInterestPoints SYS_REFCURSOR;
BEGIN
    OPEN curAllInterestPoints FOR
    SELECT l.id_location, l.name_location, l.altitude, l.latitude, l.longitude
    FROM Locations l
    WHERE l.id_location IN ( SELECT id_interest_point From Interest_Points);


RETURN curAllInterestPoints;
END;
/

--Returns a Normal_Slot
create or replace FUNCTION get_normal_slot(id_slots INTEGER)
RETURN sys_refcursor
IS
cur_normalSlot sys_refcursor;
BEGIN
OPEN cur_normalSlot FOR 
                        SELECT * 
                        FROM Slots s
                        INNER JOIN NORMAL_SLOTS ON NORMAL_SLOTS.id_slots=s.id_slots
                        WHERE s.id_slots = get_normal_slot.id_slots;
return cur_normalSlot;
END;
/

--Returns an Eletrical_Slot
create or replace FUNCTION get_electric_slot(id_slots INTEGER)
RETURN sys_refcursor
IS
cur_electricalSlot sys_refcursor;
BEGIN 
OPEN cur_electricalSlot FOR 
                            SELECT * 
                            FROM Slots s
                            INNER JOIN ELECTRIC_SLOTS ON ELECTRIC_SLOTS.id_slots=s.id_slots
                            WHERE s.id_slots = get_electric_slot.id_slots;
return cur_electricalSlot;
END;
/

create or replace FUNCTION get_electric_slot_id(id_park INTEGER)
RETURN INTEGER 

AS
	l_id_slot		INTEGER;
    l_id_park       INTEGER;
BEGIN
	IF id_park IS NOT NULL THEN
        SELECT count(id_park) INTO l_id_park FROM PARKS WHERE PARKS.id_park=get_electric_slot_id.id_park;
        IF l_id_park=0 THEN RAISE_APPLICATION_ERROR(-20006,'No Park in the DB with that ID');
        END IF;
		SELECT s.id_slots INTO l_id_slot
		FROM SLOTS s
        INNER JOIN PARKS p ON  s.id_slots = p.id_electric_slots 
        INNER JOIN ELECTRIC_SLOTS ON s.id_slots=ELECTRIC_SLOTS.id_slots
		WHERE p.id_park=get_electric_slot_id.id_park;
END IF;
RETURN l_id_slot;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20006,'No Normal Slot in the DB with that ID');
END;
/

create or replace FUNCTION get_normal_slot_id(id_park INTEGER)
RETURN INTEGER 

AS
	l_id_slot		INTEGER;
l_id_park       INTEGER;
BEGIN
	IF id_park IS NOT NULL THEN
        SELECT count(id_park) INTO l_id_park FROM PARKS WHERE PARKS.id_park=get_normal_slot_id.id_park;
        IF l_id_park=0 THEN RAISE_APPLICATION_ERROR(-20006,'No Park in the DB with that ID');
        END IF;

		SELECT s.id_slots INTO l_id_slot
		FROM SLOTS s
        INNER JOIN PARKS p ON s.id_slots=p.id_normal_slots
        INNER JOIN NORMAL_SLOTS ON s.id_slots=NORMAL_SLOTS.id_slots
		WHERE p.id_park=get_normal_slot_id.id_park;
	END IF;

RETURN l_id_slot;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20006,'No Electric Slot in the DB with that ID');
END;
/

create or replace FUNCTION get_Parked_Electric_Bicycles (id_Park INTEGER) 
RETURN SYS_REFCURSOR
IS
    curParkedElectricBicycles SYS_REFCURSOR;
v_park_id INTEGER;
BEGIN
SELECT count(id_park) INTO v_park_id FROM PARKS WHERE get_Parked_Electric_Bicycles.id_park=PARKS.id_park;
IF v_park_id=0 THEN RAISE_APPLICATION_ERROR(-20005,'No Park in the DB with that ID');
END IF;
    OPEN curParkedElectricBicycles FOR
    SELECT bi.id_bike, bi.state,bi.weight,bi.aerodynamic_coefficient,bi.frontal_area,
    bat.battery_id,bat.max_charge,bat.current_charge,bat.weight
    FROM bicycles bi, bicycle_slot bs , Batteries bat, Electric_Bicycles eb
    where bi.id_bike IN (SELECT eb.id_bike FROM Electric_Bicycles eb)
    AND bi.id_bike = bs.id_bike
     AND bs.id_slot IN (SELECT pa.id_electric_slots FROM Parks pa WHERE pa.id_park = get_Parked_Electric_Bicycles.id_Park) 
    AND bs.id_bike IN (SELECT ebb.id_bike FROM Electric_Bicycles ebb) 
    AND eb.id_battery = bat.battery_id
    AND bi.id_bike = eb.id_bike;


RETURN curParkedElectricBicycles;
END;
/

create or replace FUNCTION get_Parked_Mountain_Bicycles (id_Park INTEGER) 
RETURN SYS_REFCURSOR
IS
    curParkedMountainBicycles SYS_REFCURSOR;
v_park_id INTEGER;
BEGIN
SELECT id_park INTO v_park_id FROM PARKS WHERE get_Parked_Mountain_Bicycles.id_park=PARKS.id_park;
    OPEN curParkedMountainBicycles FOR
    SELECT bi.id_bike,bi.state, bi.weight,bi.aerodynamic_coefficient,bi.frontal_area
    FROM bicycles bi, bicycle_slot bs 
    WHERE bi.id_bike = bs.id_bike AND bs.id_bike IN (SELECT mountain.id_bike FROM Mountain_Bicycles mountain) 
    AND bs.id_slot IN (SELECT pa.id_normal_slots FROM Parks pa WHERE pa.id_park = get_Parked_Mountain_Bicycles.id_Park);



RETURN curParkedMountainBicycles;
EXCEPTION
WHEN NO_DATA_FOUND THEN  RAISE_APPLICATION_ERROR(-20005,'No Park in the DB with that ID');
END;
/

create or replace FUNCTION get_Parked_Road_Bicycles (id_Park INTEGER) 
RETURN SYS_REFCURSOR
IS
    curParkedRoadBicycles SYS_REFCURSOR;
v_park_id INTEGER;
BEGIN
SELECT id_park INTO v_park_id FROM PARKS WHERE get_Parked_Road_Bicycles.id_park=PARKS.id_park;
    OPEN curParkedRoadBicycles FOR
    SELECT bi.id_bike,bi.state, bi.weight,bi.aerodynamic_coefficient,bi.frontal_area
    FROM bicycles bi, bicycle_slot bs 
    WHERE bi.id_bike = bs.id_bike AND bs.id_bike IN (SELECT road.id_bike FROM Road_Bicycles road) 
    AND bs.id_slot IN (SELECT pa.id_normal_slots FROM Parks pa WHERE pa.id_park = get_Parked_Road_Bicycles.id_Park);
RETURN curParkedRoadBicycles;
EXCEPTION
WHEN NO_DATA_FOUND THEN  RAISE_APPLICATION_ERROR(-20005,'No Park in the DB with that ID');
END;
/

CREATE OR REPLACE FUNCTION get_park_id_bicycle_locked(id_bicycle VARCHAR)
RETURN SYS_REFCURSOR AS cur_park SYS_REFCURSOR;
l_slot INTEGER;
BEGIN
    SELECT id_slot INTO l_slot FROM BICYCLE_SLOT WHERE BICYCLE_SLOT.id_bike LIKE get_park_id_bicycle_locked.id_bicycle;
    OPEN cur_park FOR SELECT LOCATIONS.*,PARKS.id_normal_slots, PARKS.id_electric_slots FROM PARKS
    INNER JOIN SLOTS ON PARKS.id_normal_slots=SLOTS.id_slots OR PARKS.id_electric_slots=SLOTS.id_slots
    INNER JOIN LOCATIONS ON PARKS.id_park=LOCATIONS.id_location
    WHERE SLOTS.id_slots=l_slot;
    RETURN cur_park;
    EXCEPTION
WHEN NO_DATA_FOUND THEN  RAISE_APPLICATION_ERROR(-20005,'No bicycle locked in with that ID');
END;
/


----------------------------------------------------Rental---------------------------------------------------------------------
---Returns an active rental
 CREATE OR REPLACE FUNCTION get_active_rental(rental_id VARCHAR)
return sys_refcursor as cur_rental_active sys_refcursor;
BEGIN
OPEN cur_rental_active FOR SELECT id_rental,id_bike,username,pick_up_park,unlock_date FROM rentals WHERE rentals.id_bike = get_active_rental.rental_id 
and rentals.state_rental =0 ; 
RETURN cur_rental_active; 
END;
/

--Adds a Rental to the database
CREATE OR REPLACE PROCEDURE add_rental(bicycleId VARCHAR, user_email VARCHAR,pick_up_park NUMBER,left_at_park NUMBER, unlock_date TIMESTAMP,lock_date TIMESTAMP,state_rental INTEGER,rental_payment INTEGER)
IS
l_max_id INTEGER;
BEGIN
SELECT MAX(id_rental) INTO l_max_id from rentals ;
    IF l_max_id IS NULL THEN l_max_id:=1;
    ELSE l_max_id := l_max_id+1;
    END IF;
    INSERT INTO RENTALS VALUES(l_max_id,bicycleId, user_email,pick_up_park,left_at_park, unlock_date, lock_date,state_rental,rental_payment);
END;
/

--Removes a Rental from the database
CREATE OR REPLACE PROCEDURE remove_rental(id_rental NUMBER)
AS
BEGIN
    DELETE FROM RENTALS WHERE rentals.id_rental=remove_rental.id_rental;
    IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20007,'No Rental in the DB with that ID');
    END IF;
END;
/

--Returns a Rental
CREATE OR REPLACE FUNCTION get_rental(id_rental NUMBER)
return sys_refcursor as cur_rental_active sys_refcursor;
l_id INTEGER;
BEGIN
SELECT id_rental INTO l_id FROM RENTALS WHERE RENTALS.id_rental=get_rental.id_rental;
OPEN cur_rental_active FOR SELECT * FROM rentals WHERE rentals.id_rental=id_rental;
RETURN cur_rental_active; 
EXCEPTION 
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20080,'No Rental in the DB with that ID');
END;
/

-- Returns a Bicycle Current User
create or replace FUNCTION get_bicycle_current_user(id_bike VARCHAR)
RETURN SYS_REFCURSOR
AS
cur_park SYS_REFCURSOR;
l_id_bike INTEGER;
BEGIN
SELECT id_bike INTO l_id_bike FROM BICYCLES WHERE BICYCLES.id_bike LIKE get_bicycle_current_user.id_bike;
    OPEN cur_park FOR 
    SELECT username
    FROM rentals Re
    WHERE Re.id_bike LIKE id_bike AND unlock_date = (SELECT MAX(unlock_date)
                                           FROM rentals R
                                           WHERE R.id_bike LIKE id_bike);
                                           
    RETURN cur_park;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20008,'No Bicycle in the DB with that ID');
END;
/
-- Returns the park where a bicycle was picked up by its current user
create or replace FUNCTION get_starting_park(id_bike VARCHAR)
RETURN SYS_REFCURSOR
AS
cur_park SYS_REFCURSOR;
l_id_bike INTEGER;
BEGIN
SELECT id_bike INTO l_id_bike FROM BICYCLES WHERE BICYCLES.id_bike LIKE get_starting_park.id_bike;
    OPEN cur_park FOR 
    SELECT pick_up_park 
    FROM rentals Re 
    WHERE Re.id_bike LIKE get_starting_park.id_bike AND unlock_date = (SELECT MAX(unlock_date)
                                               FROM rentals R
                                               WHERE R.id_bike LIKE get_starting_park.id_bike);

    RETURN cur_park;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20008,'No Bicycle in the DB with that ID');
END;
/

CREATE OR REPLACE FUNCTION get_time_bicycle_unlocked(id_bike VARCHAR)
RETURN NUMBER AS l_time NUMBER;
l_unlock_time TIMESTAMP;
BEGIN
    SELECT unlock_date INTO l_unlock_time FROM RENTALS 
    WHERE RENTALS.id_bike LIKE get_time_bicycle_unlocked.id_bike AND RENTALS.lock_date IS NULL;
    select extract( day from diff )*24*60*60 +
         extract( hour from diff )*60*60 +
          extract( minute from diff )*60 +
           round(extract( second from diff )) total_seconds INTO l_time
     from (select systimestamp - to_timestamp(l_unlock_time) diff
             from dual);
    RETURN l_time;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20040,'No bicycle with that ID associated with a active Rental');
END;
/

CREATE OR REPLACE FUNCTION get_bicycle_user_active_rental(id_user VARCHAR2)
RETURN VARCHAR AS l_bike VARCHAR(30);
BEGIN
    SELECT id_bike INTO l_bike FROM RENTALS 
    WHERE RENTALS.username = get_bicycle_user_active_rental.id_user AND RENTALS.lock_date IS NULL;
    RETURN l_bike;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20040,'No bicycle with that ID associated with a active Rental');
END;
/

--Retrieves all unpaid rentals from a certain user
CREATE OR REPLACE FUNCTION get_all_unpaid_rentals(username VARCHAR2)
RETURN SYS_REFCURSOR AS l_ids SYS_REFCURSOR;
BEGIN
	OPEN l_ids FOR SELECT * FROM RENTALS R
	WHERE R.username=get_all_unpaid_rentals.username AND R.lock_date IS NOT NULL AND NOT EXISTS (SELECT id_rental FROM RECEIPT_RENTAL WHERE id_rental=R.id_rental);
	RETURN l_ids;
END;
/

CREATE OR REPLACE FUNCTION get_all_unpaid_rentals_month(username VARCHAR2, month NUMBER)
RETURN SYS_REFCURSOR AS l_ids SYS_REFCURSOR;
BEGIN
	OPEN l_ids FOR SELECT * FROM RENTALS R
	WHERE R.username=get_all_unpaid_rentals_month.username AND extract(month from R.unlock_date) = get_all_unpaid_rentals_month.month AND extract(year from R.unlock_date) = extract(year from SYSDATE) AND R.lock_date IS NOT NULL AND NOT EXISTS (SELECT id_rental FROM RECEIPT_RENTAL WHERE id_rental=R.id_rental);
                                                               
	RETURN l_ids;
END;
/


--------------------------------------------------------User-------------------------------------------------------------------
--Retrieves a user with a certain email from the database.
CREATE OR REPLACE FUNCTION get_user(u_username VARCHAR)
RETURN SYS_REFCURSOR
AS
cur_user SYS_REFCURSOR;
l_username VARCHAR2(100);
BEGIN
SELECT username INTO l_username from USERS WHERE USERS.username=get_user.u_username;
OPEN cur_user FOR SELECT * FROM USERS WHERE USERS.username = get_user.u_username;
RETURN cur_user;
EXCEPTION
WHEN NO_DATA_FOUND THEN RAISE_APPLICATION_ERROR(-20012,'No User in the DB with that username');
END;
/
--adds a user to the database.
CREATE OR REPLACE PROCEDURE add_user(username VARCHAR,uemail VARCHAR, upassword VARCHAR, usalt VARCHAR, uname VARCHAR, uweight NUMBER, uheight NUMBER, ucc NUMBER, uActiveRental NUMBER, points INTEGER, average_speed NUMBER)
AS
BEGIN
INSERT INTO USERS VALUES(username,uemail, upassword, usalt, uname, uweight, uheight, ucc, uActiveRental, points,average_speed);
END;
/
--updates a user's active rental status, given his email.
CREATE OR REPLACE PROCEDURE update_user_active_rental(u_username VARCHAR, uActiveRental NUMBER)
IS
BEGIN
UPDATE USERS SET USERS.has_active_rental = update_user_active_rental.uActiveRental
WHERE USERS.username = update_user_active_rental.u_username;
IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20010,'No User in the DB with that username');
END IF;
END;
/

--removes a user from the database
CREATE OR REPLACE PROCEDURE remove_user(p_user_name VARCHAR)
IS
l_receipt INTEGER;
BEGIN
DELETE FROM USERS WHERE USERS.username = remove_user.p_user_name;
IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20011,'No User in the DB with that username');
    END IF;
END;
/

--Awards a certain number of points to a user
create or replace PROCEDURE add_user_points(p_user_name VARCHAR, p_points NUMBER)
AS
BEGIN
    UPDATE Users Set points = points + p_points WHERE username = p_user_name;
    IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20011,'No User in the DB with that username');
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE insert_receipt_rental(id_rental INTEGER, id_receipt INTEGER)
AS
BEGIN
	INSERT INTO RECEIPT_RENTAL VALUES(id_rental,id_receipt);
END;
/

CREATE OR REPLACE FUNCTION create_receipt(payment_value FLOAT, date_receipt TIMESTAMP, iva NUMBER)
RETURN INTEGER AS l_id_receipt INTEGER;
BEGIN
	SELECT MAX(id_receipt) INTO l_id_receipt FROM RECEIPTS;
	IF l_id_receipt IS NULL THEN l_id_receipt:=1;
	ELSE l_id_receipt:=l_id_receipt+1;
	END IF;
	INSERT INTO RECEIPTS VALUES(l_id_receipt,date_receipt,payment_value,iva) ;
    RETURN l_id_receipt;
END;
/


---------------------------------------------------Path Information-----------------------------------------------------------
create or replace FUNCTION get_path_information(id_initial_park NUMBER, id_final_park NUMBER)
return sys_refcursor as cur_path_info sys_refcursor;
BEGIN
OPEN cur_path_info FOR SELECT PATH_INFORMATION.wind_speed, PATH_INFORMATION.wind_dir, PATH_INFORMATION.kinectic_friction FROM path_information
WHERE path_information.id_initial_park=get_path_information.id_initial_park
AND path_information.id_final_park=get_path_information.id_final_park;
RETURN cur_path_info; 
END;
/

create or replace PROCEDURE add_path_information(id_initial_park NUMBER, id_final_park NUMBER,wind_speed NUMBER, wind_dir NUMBER, kinectic_friction NUMBER)
AS
BEGIN
INSERT INTO path_Information VALUES(id_initial_park, id_final_park, wind_speed, wind_dir, kinectic_friction);
IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20080,'No parks with that IDs');
END IF;
END;
/

create or replace PROCEDURE update_path_information(id_initial_park NUMBER, id_final_park NUMBER,wind_speed NUMBER, wind_dir NUMBER, kinectic_friction NUMBER)
IS
BEGIN
    UPDATE path_Information SET path_Information.wind_speed = update_path_Information.wind_speed,
    path_Information.wind_dir= update_path_Information.wind_dir,
    path_Information.kinectic_friction= update_path_Information.kinectic_friction 
    WHERE path_Information.id_initial_park  = update_path_Information.id_initial_park 
    AND path_Information.id_final_park  = update_path_Information.id_final_park;
    IF SQL%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20012,'No Path Information in the DB from the two parks received');
    END IF;
END;
/
