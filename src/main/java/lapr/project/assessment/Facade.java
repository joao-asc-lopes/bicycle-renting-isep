package lapr.project.assessment;

import lapr.project.assessment.file.FileReader;
import lapr.project.assessment.file.FileWriter;
import lapr.project.controller.admin.AddParkController;
import lapr.project.controller.admin.AddPathController;
import lapr.project.controller.admin.ReportParkController;
import lapr.project.controller.user.*;
import lapr.project.model.bicycle.*;
import lapr.project.model.bikenetwork.Path;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;
import lapr.project.model.user.Rental;
import lapr.project.model.user.User;
import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PhysicsAlgorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Facade implements Serviceable {
    /**
     * Add Bicycles to the system.
     * <p>
     * Basic: Add one bicycle to one park.
     * Intermediate: Add several bicycles to one park.
     * Advanced: Add several bicycles to several parks.
     *
     * @param s Path1 to file with bicycles to add, according
     *          to input/bicycles.csv.
     * @return Number of added bicycles.
     */
    @Override
    public int addBicycles(String s) {
        return FileReader.readBicycleFile(new File(s));
    }

    @Override
    public int addParks(String s) {
        return FileReader.readParkFile(new File(s));
    }

    @Override
    public int addPOIs(String s) {
        return FileReader.readInterestPointFIle(new File(s)).size();
    }

    @Override
    public int addUsers(String s) {
        return FileReader.readUserFile(new File(s));
    }

    @Override
    public int addPaths(String s) {
        return FileReader.readPathFile(new File(s));
    }

    @Override
    public int getNumberOfBicyclesAtPark(double v, double v1, String s) {
        AvailableBicyclesController abc = new AvailableBicyclesController();
        AddParkController apc = new AddParkController();
        Park aux = apc.getParkWithCoordinates(v, v1);
        return abc.getBicyclesInPark(aux.getIdLocation()).size();
    }

    /**
     * Get the number of free parking places at a given park for the loaned
     * bicycle.
     *
     * @param parkLatitudeInDegrees  Park latitude in Decimal degrees.
     * @param parkLongitudeInDegrees Park Longitude in Decimal degrees.
     * @param username               The username that has an unlocked bicycle.
     * @return The number of free slots at a given park for the user's
     * bicycle type.
     */
    @Override
    public int getFreeSlotsAtPArk(double parkLatitudeInDegrees, double parkLongitudeInDegrees, String username) {
        AddParkController apc = new AddParkController();
        RentalPaymentController rpc = new RentalPaymentController();
        Park aux = apc.getParkWithCoordinates(parkLatitudeInDegrees, parkLongitudeInDegrees);
        Bicycle.BicycleType userBicycleType = rpc.getUserActiveRentalBicycle(username).getType();
        if (userBicycleType.equals(Bicycle.BicycleType.ELECTRIC)) {
            return aux.getEletricalSlots().getNumberFreeSlots();
        } else {
            return aux.getNormalSlots().getNumberFreeSlots();
        }
    }

    /**
     * Get a list of the nearest parks to the user.
     *
     * @param userLatitudeInDegrees  User latitude in Decimal Degrees.
     * @param userLongitudeInDegrees User longitude in Decimal Degrees.
     * @param outputFileName         Path to file where output should be written,
     *                               according to file output/pois.csv. Sort by
     *                               distance in ascending order.
     */
    @Override
    public void getNearestParks(double userLatitudeInDegrees, double userLongitudeInDegrees, String outputFileName) {
        List<Park> lista = new NearestParksController().getClosestFiveParksFromUser(userLatitudeInDegrees, userLongitudeInDegrees);//OUTPUTS DO DANIEL
        try {
            FileWriter.getReportPointsOfInterest(lista, userLatitudeInDegrees, userLongitudeInDegrees, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int distanceTo(double v, double v1, double v2, double v3) {
        return (int) Math.round(PhysicsAlgorithms.distance(v, v1, v2, v3));
    }

    /**
     * Unlocks a specific bicycle.
     *
     * @param username           User that requested the unlock.
     * @param bicycleDescription Bicycle description to unlock.
     * @return The time in miliseconds at which the bicycle was unlocked.
     */
    @Override
    public long unlockBicycle(String username, String bicycleDescription) {
        RentalPaymentController rpc = new RentalPaymentController();
        UnlockBicycleController ubc = new UnlockBicycleController();
        AvailableBicyclesController avc = new AvailableBicyclesController();
        User user = rpc.getUser(username);
        List<Bicycle> listBikes = avc.getAllBicycles();
        for (Bicycle b : listBikes) {
            if (b.getStatus().statusCode() == 1) { // It is available
                ubc.unlockBicycle(b.getId(), user);
                return rpc.getTimeUnlckedBicycle(b.getId()) * 1000;
            }
        }
        return -1; // There are no bikes available.

    }

    /**
     * Lock a specific bicycle at a park.
     * <p>
     * Basic: Lock a specific bicycle at a park.
     * Intermediate: Charge the user if 1h is exceeded.
     * Advanced: Add points to user.
     *
     * @param bicycleDescription     Bicycle to lock.
     * @param parkLatitudeInDegrees  Park latitude in Decimal degrees.
     * @param parkLongitudeInDegrees Park Longitude in Decimal degrees.
     * @return The time in miliseconds at which the bicycle was locked.
     */
    @Override
    public long lockBicycle(String bicycleDescription, double parkLatitudeInDegrees, double parkLongitudeInDegrees) {
        LockBicycleController lbc = new LockBicycleController();
        AddParkController apc = new AddParkController();

        User u = lbc.getBicycleUser(bicycleDescription);
        Park p1 = apc.getParkWithCoordinates(parkLatitudeInDegrees, parkLongitudeInDegrees);
        Park p2 = lbc.getStartingPark(bicycleDescription);

        lbc.awardUserPoints(p1, p2, u);

        return System.currentTimeMillis();
    }

    /**
     * Return the current debt for the user.
     *
     * @param username       The user to get the debt from.
     * @param outputFileName The path for the file to output the debt,
     *                       according to file output/balance.csv.
     *                       Sort the information by unlock time in ascending
     *                       order (oldest to newest).
     * @return The User's current debt in euros, rounded to two decimal places
     */
    @Override
    public double getUserCurrentDebt(String username, String outputFileName) {
        RentalPaymentController rpc = new RentalPaymentController();
        List<Rental> aux = rpc.returnUserUnpaidRentalsTotal(username);
        try {
            FileWriter.getBalanceReport(aux, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rpc.calculateValue(aux, rpc.getUser(username));

    }

    /**
     * Return the current points for the user.
     *
     * @param username       The user to get the points report from.
     * @param outputFileName The path for the file to output the points,
     *                       according to file output/points.csv.
     *                       Sort the information by unlock time in ascenind
     *                       order (oldest to newest).
     * @return The User's current points.
     */
    @Override
    public double getUserCurrentPoints(String username, String outputFileName) {
        RentalPaymentController rpc = new RentalPaymentController();
        User aux = rpc.getUser(username);
        try {
            FileWriter.getPoints(rpc.getAllRentals(), username, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return aux.getPoints();
    }

    /**
     * Method that allows the user to unlock any bicycle.
     *
     * @param v  The latitude in degrees of the park.
     * @param v1 THe longitude in degrees of the park.
     * @param s  The username of the user
     * @param s1 The output file according to the exampe the teacher.
     * @return The time in millisecconds at which the bicycle was unlocked.
     */
    @Override
    public long unlockAnyBicycleAtPark(double v, double v1, String s, String s1) {
        AddParkController apc = new AddParkController();
        RentalPaymentController rpc = new RentalPaymentController();
        User user = rpc.getUser(s);
        UnlockBicycleController ubc = new UnlockBicycleController();
        AvailableBicyclesController avc = new AvailableBicyclesController();
        Park park = apc.getParkWithCoordinates(v, v1);
        List<Bicycle> listBikes = avc.getBicyclesInPark(park.getIdLocation());
        List<Bicycle> theChosenBicycle = new ArrayList<>();
        for (Bicycle b : listBikes) {
            if (b.getStatus().statusCode() == 1) {
                ubc.unlockBicycle(b.getId(), user);
                theChosenBicycle.add(b);
                break;
            }
        }
        if (theChosenBicycle.isEmpty()) {
            try {
                FileWriter.getReportBicycle(theChosenBicycle, s1);
            } catch (FileNotFoundException e) {
                return -1; // The file was not found.
            }
            return rpc.getTimeUnlckedBicycle(theChosenBicycle.get(0).getId()) * 1000;
        } else {
            return -1;
        }

    }

    /**
     * Allows the user to unlock specifically an electric bicycle.
     *
     * @param v  The latitude of the park.
     * @param v1 The longitude of the park.
     * @param s  The username of the user.
     * @param s1 The output file of the chosen bicycle.
     * @return A long of the milisecconds the bike was unlocked.
     */
    @Override
    public long unlockAnyElectricBicycleAtPark(double v, double v1, String s, String s1) {
        AddParkController apc = new AddParkController();
        RentalPaymentController rpc = new RentalPaymentController();
        User user = rpc.getUser(s);
        UnlockBicycleController ubc = new UnlockBicycleController();
        AvailableBicyclesController avc = new AvailableBicyclesController();
        Park park = apc.getParkWithCoordinates(v, v1);
        List<ElectricBicycle> eleList = avc.getElectricBicycleInPark(park.getIdLocation());
        List<Bicycle> ebListChosen = new ArrayList<>();
        for (ElectricBicycle b : eleList) {
            if (b.getStatus().statusCode() == 1) {
                ubc.unlockBicycle(b.getId(), user);
                ebListChosen.add(b);
                break;
            }
        }
        if (ebListChosen.size() > 0) {
            try {
                FileWriter.getReportBicycle(ebListChosen, s1);
            } catch (FileNotFoundException e) {
                return -1; // The file was not found.
            }
            return rpc.getTimeUnlckedBicycle(ebListChosen.get(0).getId()) * 1000;
        } else {
            return -1;
        }
    }

    /**
     * Calculates the electrical energy from one location to another.
     *
     * @param v  The latitude origin.
     * @param v1 The longitude origin.
     * @param v2 The latitude destiny.
     * @param v3 THe longitude destiny.
     * @param s  The username username.
     * @return Doubles.
     */
    @Override
    public double calculateElectricalEnergyToTravelFromOneLocationToAnother(double v, double v1, double v2, double v3, String s) {
        RentalPaymentController rpc = new RentalPaymentController();
        AddPathController apc = new AddPathController();
        AddParkController aprk = new AddParkController();
        User aux = rpc.getUser(s);
        Park originPark = aprk.getParkWithCoordinates(v, v1);
        Park destinyPark = aprk.getParkWithCoordinates(v2, v3);
        Path path = apc.getPath(originPark, destinyPark);
        Bicycle bike = rpc.getUserActiveRentalBicycle(s);
        if (bike instanceof ElectricBicycle) {
            double electricalEnergy = PhysicsAlgorithms.calculateEnergySpentBetween2Points(bike.getFrontalArea(), bike.getWeight(), ((ElectricBicycle) bike).getBattery().getWeight(), path.getWindSpeed(), path.getWindDirection(), aux.getWeight(), aux.getHeight(), v, v1, v2, v3, bike.getBicycleAerodynamicalCoefficient(), originPark.getAltitude(), destinyPark.getAltitude());
            return electricalEnergy;
        } else {
            throw new InvalidDataException("The user has no electrical bicycle!");
        }
    }

    /**
     * Method that allows the user to get a bicycle suggested to him.
     *
     * @param v  Latitude of the origin park in degrees.
     * @param v1 Longitude of the origin park in degrees.
     * @param v2 Latitude of the destiny park in degrees.
     * @param v3 Longitude of the destiny park in degrees.
     * @param s  Username name.
     * @param s1 File output of all the bicycles ( See what teacher wants).
     * @return
     */

    @Override
    public int suggestElectricalBicyclesToGoFromOneParkToAnother(double v, double v1, double v2, double v3, String s, String s1) {
        SuggestEletricBicicleController sebc = new SuggestEletricBicicleController();
        AddParkController apc = new AddParkController();
        AvailableBicyclesController avController = new AvailableBicyclesController();
        RentalPaymentController rpc = new RentalPaymentController();
        User aux = rpc.getUser(s);
        Park originPark = apc.getParkWithCoordinates(v, v1);
        Park destinyPark = apc.getParkWithCoordinates(v2, v3);
        List<Bicycle> bikeList = avController.getBicyclesInPark(originPark.getIdLocation());
        try {
            FileWriter.getReportBicycle(bikeList, s1);
        } catch (FileNotFoundException e) {
            return 0; // The file was not found.
        }
        try {
            sebc.suggestBicycle(originPark, destinyPark, aux);
        } catch (IOException e) {
            return 0; // There are no bikes.
        }
        return 1; // There is a bike suggested.
    }

    /**
     * Method that returns in seconds how much the time the bicycle was unlocked.
     *
     * @param s Bicycle description.
     * @return Long representative of the seconds of oh much time was the bicycle unlocked.
     */
    @Override
    public long forHowLongWasTheBicycleUnlocked(String s) {
        BicycleFacade bf = new BicycleFacade();
        RentalPaymentController rpc = new RentalPaymentController();
        List<Rental> rentalList = rpc.getAllRentals();
        Bicycle bike = bf.getBicycle(s);
        long seconds = 0;
        for (Rental r : rentalList) {
            if (r.getBike().equals(bike)) {
                seconds = seconds + (r.getUnlockDate().getSecond() - r.getLockDate().getSecond());
            }
        }
        return seconds;
    }

    /**
     * Calculate the shortest Route from one park to another.
     * <p>
     * Basic: Only one shortest Route between two Parks is available.
     * Intermediate: Consider that connections between locations are not
     * bidirectional.
     * Advanced: More than one Route between two parks are available with
     * different number of points inbetween and different evelations difference.
     *
     * @param originLatitudeInDegrees       Origin latitude in Decimal degrees.
     * @param originLongitudeInDegrees      Origin Longitude in Decimal degrees.
     * @param destinationLatitudeInDegrees  Destination Park latitude in
     *                                      Decimal degrees.
     * @param destinationLongitudeInDegrees Destination Park Longitude in
     *                                      Decimal degrees.
     * @param outputFileName                Write to the file the Route between two parks
     *                                      according to file output/paths.csv. More than one
     *                                      path may exist. If so, sort routes by the ascending
     *                                      number of points between the parks and by ascending
     *                                      order of elevation difference.
     * @return The distance in meters for the shortest path.
     */
    @Override
    public long shortestRouteBetweenTwoParks(double originLatitudeInDegrees, double originLongitudeInDegrees, double destinationLatitudeInDegrees, double destinationLongitudeInDegrees, String outputFileName) {
        ShortestPathParkToParkController spppc = new ShortestPathParkToParkController();
        AddParkController apc = new AddParkController();
        Park p1 = apc.getParkWithCoordinates(originLatitudeInDegrees, originLongitudeInDegrees);
        Park p2 = apc.getParkWithCoordinates(destinationLatitudeInDegrees, destinationLongitudeInDegrees);
        List<Route> auxList = spppc.shortestPathParkToPark(p1, p2);
        Route r = auxList.get(0);
        try {
            FileWriter.getPathReport(auxList, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (long) r.getTotalDistance();
    }

    /**
     * Calculate the most energetically efficient route from one park to
     * another using any bicycle.
     * <p>
     * Basic: Does not consider wind.
     * Intermediate: Considers wind.
     * Advanced: Considers the different mechanical and aerodynamic
     * coefficients.
     *
     * @param originLatitudeInDegrees       Origin latitude in Decimal degrees.
     * @param originLongitudeInDegrees      Origin Longitude in Decimal degrees.
     * @param destinationLatitudeInDegrees  Destination Park latitude in
     *                                      Decimal degrees.
     * @param destinationLongitudeInDegrees Destination Park Longitude in
     *                                      Decimal degrees.
     * @param typeOfBicyle                  The type of bicycle required e.g. "electric", "mtb"
     *                                      or "road".
     * @param username                      The username.
     * @param outputFileName                Write to the file the Route between two parks
     *                                      according to file output/paths.csv. More than one
     *                                      path may exist. If so, sort routes by the ascending
     *                                      number of points between the parks and by ascending
     *                                      order of elevation difference.
     * @return The distance in meters for the shortest path.
     */
    @Override
    public long mostEnergyEfficientRouteBetweenTwoParks(double originLatitudeInDegrees, double originLongitudeInDegrees, double destinationLatitudeInDegrees, double destinationLongitudeInDegrees, String typeOfBicyle, String username, String outputFileName) {
        ShortestPathEfficientElectricalController speec = new ShortestPathEfficientElectricalController();
        AddParkController apc = new AddParkController();
        RentalPaymentController rpc = new RentalPaymentController();
        User user = rpc.getUser(username);
        Park p1 = apc.getParkWithCoordinates(originLatitudeInDegrees, originLongitudeInDegrees);
        Park p2 = apc.getParkWithCoordinates(destinationLatitudeInDegrees, destinationLongitudeInDegrees);
        Bicycle usedBike = null;
        if ("mtb".equals(typeOfBicyle)) {
            usedBike = new MountainBicycle("TestID", Bicycle.BicycleStatus.AVAILABLE, 5, 0.01, 0.5);
        } else if ("road".equals(typeOfBicyle)) {
            usedBike = new RoadBicycle("TestID", Bicycle.BicycleStatus.AVAILABLE, 5, 0.01, 0.5);
        } else if ("electric".equals(typeOfBicyle)) {
            usedBike = new ElectricBicycle("TestID", Bicycle.BicycleStatus.AVAILABLE, new Battery(9999, 1000, 500, 0), 5, 0.01, 0.5);
        }
        List<Route> auxList = speec.shortestElectricalPath(user, usedBike, p1, p2);
        try {
            FileWriter.getPathReport(auxList, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Calculate the shortest Route from one park to another.
     * <p>
     * Basic: Only one shortest Route between two Parks is available.
     * Intermediate: Consider that connections between locations are not
     * bidirectional.
     * Advanced: More than one Route between two parks are available with
     * different number of points inbetween and different evelations difference.
     *
     * @param originLatitudeInDegrees       Origin latitude in Decimal degrees.
     * @param originLongitudeInDegrees      Origin Longitude in Decimal degrees.
     * @param destinationLatitudeInDegrees  Destination Park latitude in
     *                                      Decimal degrees.
     * @param destinationLongitudeInDegrees Destination Park Longitude in
     *                                      Decimal degrees.
     * @param inputPOIs                     Path to file that contains the POIs that the route
     *                                      must go through, according to file input/pois.csv.
     * @param outputFileName                Write to the file the Route between two parks
     *                                      according to file output/paths.csv. More than one
     *                                      path may exist. If so, sort routes by the ascending
     *                                      number of points between the parks and by ascending
     *                                      order of elevation difference.
     * @return The distance in meters for the shortest path.
     */
    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(double originLatitudeInDegrees, double originLongitudeInDegrees, double destinationLatitudeInDegrees, double destinationLongitudeInDegrees, String inputPOIs, String outputFileName) {
        ShortestPathInterestPointsController speec = new ShortestPathInterestPointsController();
        AddParkController apc = new AddParkController();
        RentalPaymentController rpc = new RentalPaymentController();
        Park p1 = apc.getParkWithCoordinates(originLatitudeInDegrees, originLongitudeInDegrees);
        Park p2 = apc.getParkWithCoordinates(destinationLatitudeInDegrees, destinationLongitudeInDegrees);
        List<Location> lista = FileReader.readInterestPointFIle(new File(inputPOIs));
        List<Route> listaAux = speec.getShortestPathIterable(p1, p2, lista);
        try {
            FileWriter.getPathReport(listaAux, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listaAux.size();

    }

    /**
     * Get a report for the bicycle charging status at a given park.
     *
     * @param parkLatitudeInDegrees  Park latitude in Decimal degrees.
     * @param parkLongitudeInDegrees Park Longitude in Decimal degrees.
     * @param outputFileName         Path to file where bicycle information should be
     *                               written, according to file output/bicycles.csv.
     *                               Sort items by descending order of time to finish
     *                               charge in seconds and secondly by ascending bicycle
     *                               description order.
     * @return The number of bicycles charging at the moment that are not
     * 100% fully charged.
     */
    @Override
    public long getParkChargingReportForPark(double parkLatitudeInDegrees, double parkLongitudeInDegrees, String outputFileName) {
        ReportParkController rpc = new ReportParkController();
        AddParkController apc = new AddParkController();
        Park p1 = apc.getParkWithCoordinates(parkLatitudeInDegrees, parkLongitudeInDegrees);
        List<Bicycle> lista = new ArrayList<>(rpc.getEletricBikesPark(p1));
        try {
            FileWriter.getReportBicycle(lista, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (Bicycle b : lista) {
            ElectricBicycle eb = (ElectricBicycle) b;
            if (Double.compare(eb.getMissingCharge(),0.0) == 0) {
                count++;
            }
        }

        return count;
    }


    /**
     * Calculate the most energetically efficient route from one park to
     * another with sorting options.
     *
     * @param originLatitudeInDegrees       Origin latitude in Decimal degrees.
     * @param originLongitudeInDegrees      Origin Longitude in Decimal degrees.
     * @param destinationLatitudeInDegrees  Destination Park latitude in
     *                                      Decimal degrees.
     * @param destinationLongitudeInDegrees Destination Park Longitude in
     *                                      Decimal degrees.
     * @param typeOfBicycle                 The type of bicycle required e.g. "electric", "mtb"
     *                                      or "road".
     * @param username                      The user that asked for the routes.
     * @param maxNumberOfSuggestions        The maximum number of suggestions to
     *                                      provide.
     * @param ascendingOrder                If routes should be ordered by ascending or
     *                                      descending order
     * @param sortingCriteria               The criteria to user for ordering "energy",
     *                                      "shortest_distance", "number_of_points"
     * @param inputPOIs                     Path to file that contains the POIs that the route
     *                                      must go through, according to file input/pois.csv.
     *                                      By default, the file is empty.
     * @param outputFileName                Write to the file the Route between two parks
     *                                      according to file output/paths.csv. More than one
     *                                      path may exist.
     * @return The number of suggestions
     */
    @Override
    public int suggestRoutesBetweenTwoLocations(double originLatitudeInDegrees, double originLongitudeInDegrees, double destinationLatitudeInDegrees, double destinationLongitudeInDegrees, String typeOfBicycle, String username, int maxNumberOfSuggestions, boolean ascendingOrder, String sortingCriteria, String inputPOIs, String outputFileName) {
        SuggestRoutesBetweenTwoLocationsController suggestC = new SuggestRoutesBetweenTwoLocationsController();
        AddParkController apc = new AddParkController();
        Park p1 = apc.getParkWithCoordinates(originLatitudeInDegrees, originLongitudeInDegrees);
        Park p2 = apc.getParkWithCoordinates(destinationLatitudeInDegrees, destinationLongitudeInDegrees);
        RentalPaymentController rpc = new RentalPaymentController();
        User aux = rpc.getUser(username);
        Bicycle bike = null;
        if ("mtb".equals(typeOfBicycle)) {
            bike = new MountainBicycle("teste", Bicycle.BicycleStatus.AVAILABLE, 2, 2, 2);
        } else if ("road".equals(typeOfBicycle)) {
            bike = new RoadBicycle("teste", Bicycle.BicycleStatus.AVAILABLE, 2, 2, 2);

        } else if ("electric".equals(typeOfBicycle)) {
            bike = new ElectricBicycle("teste", Bicycle.BicycleStatus.AVAILABLE, new Battery(1, 1, 1), 2, 2, 2);

        }

        List<Location> list = FileReader.readInterestPointFIle(new File(inputPOIs));

        List<Route> result = suggestC.suggestRouteBetweenTwoLocations(p1, p2, bike, aux, maxNumberOfSuggestions, ascendingOrder, sortingCriteria, list);
        try {
            FileWriter.getPathReport(result, outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result.size();
    }


    /**
     * Get the invoice for the current month.
     * This should include all bicycle loans that charged the user, the
     * number of points the user had before the actual month, the number of
     * points earned during the month, the number of points converted to euros.
     *
     * @param month      The month of the invoice e.g. 1 for January.
     * @param username   The user for which the invoice should be created.
     * @param outputPath Path to file where the invoice should be written,
     *                   according to file output/invoice.csv.
     * @return User debt in euros rounded to two decimal places.
     */
    @Override
    public double getInvoiceForMonth(int month, String username, String outputPath) {
        RentalPaymentController rpc = new RentalPaymentController();
        User aux = rpc.getUser(username);
        List<Rental> lista = rpc.returnUserUnpaidRentalsOfMonth(username, month);
        double value = rpc.calculateValue(lista, aux);
        try {
            FileWriter.getInvoiceCharge(lista);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return (double)Math.round(value*100)/100;
    }
}
