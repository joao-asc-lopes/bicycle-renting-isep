package lapr.project.utils;

import java.io.IOException;

public class PhysicsAlgorithms {

    private static final int EARTH_RADIUS = 6371; // Earth Radius in Km.
    private static final double GRAVITY = 9.8;      // m/s
    private static final double ATMOSPHERIC_DENSITY = 1.225; // Density of air in 15ºC on the sea level (Kg/m^3)
    private static final double WIND_LOAD_FORMULA_CONSTANT = 0.5;
    private static final double ROLLING_RESISTANCE = 0.0045;  ///unsigned
    private static final double ELECTRIC_YIELD = 0.8;    // 80%
    private static final double BIKE_SPEED = 22.5;   // (km/h)

    public PhysicsAlgorithms() {
       //Constructor for tests
    }


    /**
     * Returns the distance between 2 points characterized by (latitude1 and
     * longitude1) and (latitude2, longitude2)
     *
     * @param latitude1  The latitude of the point1.
     * @param longitude1 The longitude of the point1.
     * @param latitude2  The latitude of the point2.
     * @param longitude2 The longitude of the point2.
     * @return A double that is indicative of the distance in km of the points
     * characterized by geographical decimal points.
     */
    public static double distance(double latitude1, double longitude1, double latitude2, double longitude2) {

        if (latitude1 > 90 || latitude1 < -90 || latitude2 > 90 || latitude2 < -90) {
            throw new InvalidDataException("The latitude introduzed is invalid!"); // Necessary validation!
        }

        if (longitude1 > 180 || longitude1 < -180 || longitude2 > 180 || longitude2 < -180) {
            throw new InvalidDataException("The longitude introduzed is invalid!"); // Validation!
        }

        return distance2(latitude1, longitude1, latitude2, longitude2);
    }

    /**
     * The distance of two points characterized by geographical decimal points.
     *
     * @param latitude1  The latitude of point1.
     * @param longitude1 The longitude of the point1.
     * @param latitude2  The latitude of the point2.
     * @param longitude2 The longitude of the point2.
     * @return Returns a double indicative of the distance in m between both
     * points.
     */
    private static double distance2(double latitude1, double longitude1, double latitude2, double longitude2) {
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLong = Math.toRadians(longitude2 - longitude1);

        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);

        double a = haversin(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1000;
    }

    /**
     * The haversine formula, that indicates that the intermediate latitude and
     * longitude kms are given by the the sin of them raised by the square.
     *
     * @param number Number that is either the intermediate latitude or the
     *               intermediate longitude.
     * @return Returns a double resultante of the haversin formula.
     */
    private static double haversin(double number) {
        return Math.pow(Math.sin(number / 2), 2);
    }

    /**
     * The method that allows the user to calculate calories.
     *
     * @param vm                  The average speed of the user in the trip.
     * @param bikeWeight          The weight of the bike.
     * @param userWeight          The weight of the user.
     * @param userHeight          The height of the user.
     * @param latitude1           The latitude of the point x.
     * @param longitude1          The longitude of the point x.
     * @param windSpeed           The windspeed in the connection.
     * @param windDirection       The wind direction in the connection.
     * @param latitude2           The latitude of the point y.
     * @param longitude2          The longitude of the point y.
     * @param bikeAeroCoefficient
     * @return A double representative of the burnt calories in cal.
     * @throws InvalidDataException
     * @throws IOException
     */
    public static double calculateCalories(double vm, double bikeWeight, double frontalArea, double userWeight, double userHeight, double latitude1, double longitude1, double windSpeed, double windDirection, double latitude2, double longitude2, double bikeAeroCoefficient, double initialElevation, double finalElevation) {
        if (vm <= 0) {
            throw new InvalidDataException();
        }

        double totalWeight = bikeWeight + userWeight;
        double bearingAngle = calculateBearingAngle(latitude1, longitude1, latitude2, longitude2);

        double windDirectionReal = calculateWindDirection(bearingAngle, windDirection);
        double mechanicalPower = calculateMechanicalPower(frontalArea, totalWeight, windSpeed, windDirectionReal, vm, userHeight, latitude1, longitude1, latitude2, longitude2, bikeAeroCoefficient, initialElevation, finalElevation);
        double dist = distance(latitude1, longitude1, latitude2, longitude2);
        double tempo = (dist) / vm;
        if (Math.round(tempo) == 0) {
            return 0;
        } else {
            double mWork = mechanicalPower * tempo;  //Man's work (J)

            return mWork / 4.18; //1 cal = 4.18 J
        }
    }

    /**
     * Method that allows the calculus of the energy spent in a trip between 2 points.
     *
     * @param justBikeWeight      The weight of a bike without its battery.
     * @param batteryWeight       The battery weight.
     * @param windSpeed           The speed of the wind.
     * @param windDirection       The direction of the wind in degrees.
     * @param userWeight          The weight of the user in kg.
     * @param userHeight          The height of the user in m.
     * @param voltage             The voltage of the bicycle.
     * @param currentCharge       The current charge of the bicycle.
     * @param latitude1           The latitude of the point x.
     * @param longitude1          The longitude of the point x.
     * @param latitude2           The latitude of the point y.
     * @param longitude2          The longitude of the point y.
     * @param bikeAeroCoefficient The aerodynamic coefficient of a bicycle.
     * @return A double that indicates the calculated energy in joules.
     * @throws InvalidDataException
     * @throws IOException
     */
    public static double calculateDistanceCoveredByElectricalBicycle(double frontalArea, double justBikeWeight, double batteryWeight, double windSpeed, double windDirection, double userWeight, double userHeight, double voltage, double currentCharge, double latitude1, double longitude1, double latitude2, double longitude2, double bikeAeroCoefficient, double initialElevation, double finalElevation) {

        double bikeWeight = justBikeWeight + batteryWeight;
        if (justBikeWeight <= 0 || bikeWeight <= 0 || userWeight <= 0 || windSpeed < 0) {
            throw new InvalidDataException();
        }
        double bearingAngle = calculateBearingAngle(latitude1, longitude1, latitude2, longitude2);
        double windFavor = calculateWindDirection(bearingAngle, windDirection);
        double availableEnergy = calculateAvailableEnergy(voltage, currentCharge);
        double electricPower = calculateMechanicalPowerUsed(frontalArea, bikeWeight, userWeight, windSpeed, windFavor, BIKE_SPEED, userHeight, latitude1, longitude1, latitude2, longitude2, bikeAeroCoefficient, initialElevation, finalElevation);
        double duration = 0;
        if (electricPower > 0.0) {
            duration = availableEnergy / electricPower;
        }

        return (duration * BIKE_SPEED);

    }

    /**
     * Method that allows the calculus of the energy spent in a trip between 2 points.
     *
     * @param justBikeWeight      The weight of a bike without its battery.
     * @param batteryWeight       The battery weight.
     * @param windSpeed           The speed of the wind.
     * @param windDirection       The direction of the wind in degrees.
     * @param userWeight          The weight of the user in kg.
     * @param userHeight          The height of the user in m.
     * @param latitude1           The latitude of the point x.
     * @param longitude1          The longitude of the point x.
     * @param latitude2           The latitude of the point y.
     * @param longitude2          The longitude of the point y.
     * @param bikeAeroCoefficient The aerodynamic coefficient of a bicycle.
     * @return A double that indicates the calculated energy in joules. J
     * @throws InvalidDataException
     * @throws IOException
     */
    public static double calculateEnergySpentBetween2Points(double frontalArea, double justBikeWeight, double batteryWeight,
                                                            double windSpeed, double windDirection, double userWeight, double
                                                                    userHeight, double latitude1, double
                                                                    longitude1, double latitude2, double longitude2, double bikeAeroCoefficient,
                                                            double initialElevation, double finalElevation) {

        double bikeWeight = justBikeWeight + batteryWeight;
        if (bikeWeight <= 0 || userWeight <= 0 || windSpeed < 0) {
            throw new InvalidDataException();
        }

        double bearingAngle = calculateBearingAngle(latitude1, longitude1, latitude2, longitude2);
        double windFavor = calculateWindDirection(bearingAngle, windDirection);

        double distance = distance(latitude1, longitude1, latitude2, longitude2);
        double time = (distance) / BIKE_SPEED;
        double energySecond = calculateMechanicalPowerUsed(frontalArea, bikeWeight, userWeight, windSpeed, windFavor, BIKE_SPEED, userHeight, latitude1, longitude1, latitude2, longitude2, bikeAeroCoefficient, initialElevation, finalElevation);
        return energySecond * time;

    }

    /**
     * This method calculates the available energy
     *
     * @param voltage       The voltage of the bike.
     * @param currentCharge The current Charge of the bike.
     * @return A double indicative of the available energy in the bike.
     */
    private static double calculateAvailableEnergy(double voltage, double currentCharge) {
        return (voltage * currentCharge) / ELECTRIC_YIELD;
    }

    /**
     * Method that allows the calculus of the electric energy.
     *
     * @param bikeWeight                 The weight of the bicycle. kg
     * @param userWeight                 The weight of the user. kg
     * @param windSpeed                  The speed of the wind. m/s
     * @param windDirection              The direction of the wind.
     * @param bikeSpeed                  The speed of the bicycle.
     * @param userHeight                 The height of the user.
     * @param latitude1                  The latitude of the point x.
     * @param longitude1                 The longitude of the point x.
     * @param latitude2                  The latitude of the point y.
     * @param longitude2                 The longitude of the point y.
     * @param bikeAerodynamicCoefficient
     * @return A double representative of the electric power spent. Watts
     * @throws InvalidDataException
     * @throws IOException
     */
    private static double calculateMechanicalPowerUsed(double frontalArea, double bikeWeight, double userWeight, double windSpeed, double windDirection, double bikeSpeed, double userHeight, double latitude1, double longitude1, double latitude2, double longitude2, double bikeAerodynamicCoefficient, double initialElevation, double finalElevation) {

        double totalWeight = bikeWeight + userWeight;
        double mechanicalPower = calculateMechanicalPower(frontalArea, totalWeight, windSpeed, windDirection, bikeSpeed, userHeight, latitude1, longitude1, latitude2, longitude2, bikeAerodynamicCoefficient, initialElevation, finalElevation);
        return mechanicalPower / ELECTRIC_YIELD;
    }

    /**
     * Method that allows the calculus of the mechanical power.
     *
     * @param totalWeight   The total weight.
     * @param windSpeed     The speed of the wind.
     * @param windDirection The direction of the wind in degrees.
     * @param bikeSpeed     The speed of the bike.
     * @param userHeight    Thje height of the user.
     * @param latitude1     The latitude of the point x.
     * @param longitude1    The longitude of the point x.
     * @param latitude2     The latitude of the point y.
     * @param longitude2    THe longitude of the point y.
     * @return A double representative of the mechanical power.
     * @throws InvalidDataException
     * @throws IOException
     */
    private static double calculateMechanicalPower(double frontalArea, double totalWeight, double windSpeed,
                                                   double windDirection, double bikeSpeed, double userHeight, double latitude1, double longitude1,
                                                   double latitude2, double longitude2, double bikeAerodynamicCoefficient, double initialElevation,
                                                   double finalElevation) {
        double resistiveForce = calculateResistiveForce(frontalArea, totalWeight, windSpeed, windDirection, bikeSpeed, userHeight, latitude1, longitude1, latitude2, longitude2, bikeAerodynamicCoefficient, initialElevation, finalElevation);
        return resistiveForce * bikeSpeed;
    }

    /**
     * Allows the calculus of the resistive force.
     *
     * @param totalWeight   The total weight of the user + bike.
     * @param windSpeed     The windspeed of the wind.
     * @param windDirection The direction of the wind in degrees.
     * @param bikeSpeed     The speed of the bicycle.
     * @param userHeight    The height of the user.
     * @param latitude1     The latitude of the point x.
     * @param longitude1    The longitude of the point x.
     * @param latitude2     The latitude of the point y.
     * @param longitude2    The longitude of the point y.
     * @return A double representative of the resistive force.
     * @throws InvalidDataException
     * @throws IOException
     */
    private static double calculateResistiveForce(double frontalArea, double totalWeight, double windSpeed,
                                                  double windDirection, double bikeSpeed, double userHeight, double latitude1, double longitude1,
                                                  double latitude2, double longitude2, double bikeAerodynamicCoefficent, double initialElevation,
                                                  double finalElevation) {
        double area = frontalArea;
        double weightForce = calculateWeightForce(GRAVITY, totalWeight);
        double dragForce = calculateDragForce(windSpeed, bikeSpeed, area, bikeAerodynamicCoefficent);
        double graviticForce = calculateGraviticForce(latitude1, longitude1, latitude2, longitude2, weightForce, initialElevation, finalElevation);
        double bearingResistance = calculateBearingResistance(weightForce, latitude1, longitude1, latitude2, longitude2, initialElevation, finalElevation);
        if (windDirection > 0) { //if windDirection is 0, it's because the wind is against the cyclist
            double energySpent = (graviticForce + bearingResistance) - dragForce;
            if (energySpent < 0) {
                return 0;
            }
            return energySpent;
        } else if (windDirection < 0) {
            return (graviticForce + bearingResistance) + dragForce;
        } else {
            return (graviticForce + bearingResistance);
        }
    }

    /**
     * This method is the public method that Calculate the area of the bike with
     * the person
     *
     * @param bikeWidth:  Width of handlebars
     * @param bikeHeight: Height of bicycle
     * @param userHeight: Height of User
     * @return product of totalHeight and the width of the bike
     */

    /**
     * Method that allows the calculus of  the drag force.
     *
     * @param windSpeed                     The wind speed.
     * @param bikeSpeed                     The bicycle speed.
     * @param area                          The area.
     * @param bicycleAerodynamicCoefficient
     * @return A double that is representative of the dragging force.
     */
    private static double calculateDragForce(double windSpeed, double bikeSpeed, double area,
                                             double bicycleAerodynamicCoefficient) {
        double speed = bikeSpeed - windSpeed;
        double dragCoefficient = bicycleAerodynamicCoefficient;

        return WIND_LOAD_FORMULA_CONSTANT * ATMOSPHERIC_DENSITY * area * dragCoefficient * Math.pow(speed, 2);
    }

    /**
     * This method allows the calculus of the gravitic force.
     *
     * @param latitude1   The latitude of the point x.
     * @param longitude1  The longitude of the point x.
     * @param latitude2   The latitude of the point y.
     * @param longitude2  The longitude of the point y.
     * @param weightForce The weightForce.
     * @return A double representative of the gravitic force.
     * @throws InvalidDataException
     * @throws IOException
     */
    private static double calculateGraviticForce(double latitude1, double longitude1, double latitude2,
                                                 double longitude2, double weightForce, double initialElevation, double finalElevation) {
        double averageElevation = Math.abs(finalElevation - initialElevation);

        double d = distance(latitude1, longitude1, latitude2, longitude2);
        if (Math.round(d) == 0) {
            return 0;
        } else {
            double inclination = (Math.tan(averageElevation / d));
            //inclination is the quotient of the elevation and distance, and convert to degrees
            return weightForce * Math.sin(Math.toDegrees(inclination));
        }

    }

    /**
     * This method is the public method that Calculate
     *
     * @param weightForce: it's the x component of the weight Force
     * @return product of the weight force and the rolling resistance
     */
    private static double calculateBearingResistance(double weightForce, double latitude1, double longitude1,
                                                     double latitude2, double longitude2, double initialElevation, double finalElevation) {
        double averageElevation = Math.abs(finalElevation - initialElevation);

        double d = distance(latitude1, longitude1, latitude2, longitude2);

        if (Math.round(d) == 0) {
            return 0;
        } else {
            double inclination = (Math.tan(averageElevation / d));
            return weightForce * ROLLING_RESISTANCE * Math.cos(inclination);
        }

    }

    /**
     * This method is the public method that Calculate
     *
     * @param gravity:     constatnt (9.8 m/s)
     * @param totalWeight: sum of user's weight and the bike weight
     * @return product of the total weight and the gravity
     */
    private static double calculateWeightForce(double gravity, double totalWeight) {
        return totalWeight * gravity;
    }

    /**
     * Method that allows the user to calculate a bearing angle given 2 points.
     *
     * @param latitude1  The latitude of point x.
     * @param longitude1 The longitude of point x.
     * @param latitude2  The latitude of point y.
     * @param longitude2 The longitude of point y.
     * @return A double that is representative of the bearing angle.
     */
    public static double calculateBearingAngle(double latitude1, double longitude1, double latitude2,
                                               double longitude2) {
        if (latitude1 > 90 || latitude1 < -90 || latitude2 > 90 || latitude2 < -90) {
            throw new InvalidDataException("Please insert a valid latitude!");
        }
        if (longitude1 > 180 || longitude1 < -180 || longitude2 > 180 || longitude2 < -180) {
            throw new InvalidDataException("Please insert a valid longitude!");
        }

        double x = Math.cos(Math.toRadians(latitude2)) * Math.sin(Math.toRadians(longitude2 - longitude1));

        double y = Math.cos(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2)) - Math.sin(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(longitude2 - longitude1));

        return Math.toDegrees(Math.atan2(x, y));


    }

    /**
     * Method that allows the user to calculate the wind direction.
     *
     * @param bearingAngle  The angle in degrees representative of the heading direction.
     * @param windDirection The angle in degrees representative of the wind direction.
     * @return A number that if 0 indicates that the force of the wind is null, if bigger than 0 it says it is in favour, if negative is against.
     */

    public static double calculateWindDirection(double bearingAngle, double windDirection) {
        double bearing = bearingAngle;
        if (bearingAngle < 0) {
            bearing = 360 - bearingAngle;
        }
        double wind = windDirection;
        if (windDirection < 0) {
            wind = 360 - windDirection;
        }
        if (Math.round(Math.abs(bearing - wind)) == 90 || Math.round(Math.abs(bearing - wind)) == 270) {
            return 0;
        }
        if (Math.abs(bearing - wind) < 90) { // esta no primeiro quadrante
            return 1;
        } else if (Math.abs(bearing - wind) <= 180) { // esta no segundo quadrante
            return -1;
        } else if (Math.abs(bearing - wind) < 270) { // terceiro
            return -1;
        } else { // quadrante
            return 1;
        }


    }


}
