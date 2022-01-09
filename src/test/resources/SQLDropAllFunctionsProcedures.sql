--Battery
DROP FUNCTION get_battery;
DROP FUNCTION add_battery;
DROP PROCEDURE remove_battery;
--Bicycle
DROP FUNCTION get_road_bicycle;
DROP FUNCTION get_mountain_bicycle;
DROP FUNCTION get_electric_bicycle;
DROP FUNCTION get_bicycle_type;
DROP FUNCTION get_all_mountain_bicycles;
DROP FUNCTION get_all_electric_bicycles;
DROP FUNCTION get_all_road_bicycles;
DROP PROCEDURE add_road_bicycle;
DROP PROCEDURE add_mountain_bicycle;
DROP PROCEDURE add_electric_bicycle;
DROP PROCEDURE remove_bicycle;
DROP PROCEDURE update_bicycle;
DROP PROCEDURE update_electric_bicycle;
--Location
DROP FUNCTION get_interest_point;
DROP FUNCTION get_park;
DROP FUNCTION get_location_by_coordinates;
DROP PROCEDURE add_interest_point;
DROP PROCEDURE add_park;
DROP PROCEDURE remove_interest_points;
DROP PROCEDURE remove_park;
DROP PROCEDURE update_interest_point;
DROP PROCEDURE update_park;
DROP PROCEDURE add_electric_slots;
DROP PROCEDURE add_normal_slots;
DROP PROCEDURE bike_into_park;
DROP PROCEDURE restock_bikes_normal;
DROP PROCEDURE restock_bikes_electric;
DROP FUNCTION get_all_parks;
DROP FUNCTION get_all_interest_points;
DROP FUNCTION get_normal_slot;
DROP FUNCTION get_electric_slot;
DROP FUNCTION get_electric_slot_id;
DROP FUNCTION get_normal_slot_id;
DROP FUNCTION get_parked_electric_bicycles;
DROP FUNCTION get_parked_mountain_bicycles;
DROP FUNCTION get_parked_road_bicycles;
DROP FUNCTION get_park_id_bicycle_locked;
--Rental
DROP FUNCTION get_active_rental;
DROP PROCEDURE add_rental;
DROP PROCEDURE remove_rental;
DROP FUNCTION get_rental;
DROP FUNCTION get_bicycle_current_user;
DROP FUNCTION get_starting_park;
DROP FUNCTION get_time_bicycle_unlocked;
DROP FUNCTION get_bicycle_user_active_rental;
DROP FUNCTION get_all_unpaid_rentals;
--User
DROP FUNCTION get_user;
DROP PROCEDURE add_user;
DROP PROCEDURE update_user_active_rental;
DROP PROCEDURE remove_user;
DROP PROCEDURE add_user_points;
DROP PROCEDURE insert_receipt_rental;
DROP FUNCTION create_receipt;
--WindInformation
DROP FUNCTION get_path_information;
DROP PROCEDURE add_path_information;
DROP PROCEDURE update_path_information;
