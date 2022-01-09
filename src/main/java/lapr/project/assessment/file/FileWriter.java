package lapr.project.assessment.file;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.bicycle.RoadBicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.Location;
import lapr.project.model.park.LocationFacade;
import lapr.project.model.park.Park;
import lapr.project.model.user.Rental;
import lapr.project.model.user.RentalService;
import lapr.project.utils.PhysicsAlgorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.List;

public class FileWriter {

    /**
     * Method that allows the administrator to get an output file.
     *
     * @param listLocation  The list of locations.
     * @param userLatitude  The latitude of the user.
     * @param userLongitude The longitude of a user.
     * @throws FileNotFoundException
     */

    public static void getReportPointsOfInterest(List<Park> listLocation, double userLatitude, double userLongitude, String outputFileName) throws FileNotFoundException {
        Formatter output = new Formatter(new File(outputFileName));

        for (Location l : listLocation) {
            output.format(l.getLatitude() + ";" + l.getLongitude() + ";" + PhysicsAlgorithms.distance(l.getLatitude(), l.getLongitude(), userLatitude, userLongitude));
            output.format("%n");
        }
        output.close();

    }

    /**
     * Method that allows the administrator to get an output file.
     *
     * @param listBicycle The list of bicycles.
     * @throws FileNotFoundException
     */

    public static void getReportBicycle(List<Bicycle> listBicycle, String outputFile) throws FileNotFoundException {
        Formatter output = new Formatter(new File(outputFile));

        for (Bicycle b : listBicycle) {
            if (b instanceof ElectricBicycle) {
                output.format(b.getId() + ";" + b.getType() + ";" + ((ElectricBicycle) b).getBattery().getCurrentCharge());
                output.format("%n");
            } else {
                output.format(b.getId() + ";" + b.getType() + ";" + " Does not have a Battery");
                output.format("%n");

            }

        }
        output.close();
    }

    /**
     * Gets the points report.
     *
     * @param listRental
     * @throws FileNotFoundException
     */

    public static void getPoints(List<Rental> listRental, String username, String outputFileName) throws FileNotFoundException {

        BicycleNetwork bn = new BicycleNetwork();
        Formatter output = new Formatter(new File(outputFileName));

        for (Rental r : listRental) {
            if (r.getUser().getUsername().equals(username)) {
                output.format(r.getBike().getId() + ";" + r.getUnlockDate() + ";" + r.getLockDate() + ";" + r.getPickUpPark().getLatitude() + ";" + r.getPickUpPark().getLongitude() + ";" + r.getPickUpPark().getAltitude() + ";" + r.getLeftAtPark().getLatitude() + ";" + r.getLeftAtPark().getLongitude() + ";" + r.getLeftAtPark().getAltitude() + ";" + Math.abs(r.getPickUpPark().getAltitude() - r.getLeftAtPark().getAltitude()) + ";" + bn.calculatePoints(r.getLeftAtPark(), r.getPickUpPark()));
                output.format("%n");
            }
        }
        output.close();
    }

    /**
     * Gets the report charge of the park.
     *
     * @param p THe park p.
     * @throws FileNotFoundException
     */

    public static void getReportCharge(Park p) throws FileNotFoundException {
        LocationFacade lf = new LocationFacade();

        List<Bicycle> bikeList = lf.getParkedBicycles(p.getIdLocation());
        List<ElectricBicycle> eleBike = lf.getElectricBicyclesPark(p.getIdLocation());
        Formatter output = new Formatter(new File("reportCharging.txt"));

        for (Bicycle b : bikeList) {
            if (b instanceof ElectricBicycle) {
                output.format(b.getId() + ";" + ((ElectricBicycle) b).getBattery().getCurrentCharge() + p.getEletricalSlots().calculateTimeToGetBatteryChargedInMinuts((ElectricBicycle) b, p.getEletricalSlots().chargePerSlot(eleBike.size())));
                output.format("%n");
            } else if (b instanceof RoadBicycle) {
                output.format(b.getId() + ";" + " Does not have electrical information since it is a Road Bicycle");
                output.format("%n");
            } else {
                output.format(b.getId() + "; Does not have electrical information since it is a Mountain Bicycle");
                output.format("%n");
            }

        }
        output.close();
    }

    /**
     * Gets the invoice charge report.
     *
     * @param listRentals
     * @throws FileNotFoundException
     */

    public static void getInvoiceCharge(List<Rental> listRentals) throws FileNotFoundException {
        RentalService rs = new RentalService();

        BicycleNetwork bn = new BicycleNetwork();
        Formatter output = new Formatter(new File("getInvoicesReport.txt"));


        for (Rental r : listRentals) {
            double previousPoints = r.getUser().getPoints();
            double chargedValue = rs.calculateValue(listRentals, r.getUser());
            output.format(r.getUser().getUsername());
            output.format("%n");
            output.format("Previous points:" + (r.getUser().getPoints() - bn.calculatePoints(r.getLeftAtPark(), r.getPickUpPark())));
            output.format("%n");
            output.format("Earned Points:" + bn.calculatePoints(r.getLeftAtPark(), r.getPickUpPark()));
            output.format("%n");
            previousPoints = previousPoints - r.getUser().getPoints();
            output.format("Discounted Points:" + previousPoints);
            output.format("%n");
            output.format("Actual Points:" + r.getUser().getPoints());
            output.format("%n");
            output.format("Charged Value:" + chargedValue);
            output.format("%n");
            output.format(r.getBike().getId() + ";" + r.getUnlockDate() + ";" + r.getLockDate() + ";" + r.getPickUpPark().getLatitude() + ";" + r.getPickUpPark().getLongitude() + ";" + r.getLeftAtPark().getLatitude() + ";" + r.getLeftAtPark().getLongitude() + ";" + (r.getLockDate().getSecond() - r.getUnlockDate().getSecond()) + ";" + chargedValue);
            output.format("%n");

        }
        output.close();

    }

    /**
     * Gets the balance report.
     *
     * @param listRental
     * @throws FileNotFoundException
     */

    public static void getBalanceReport(List<Rental> listRental, String outputFile) throws FileNotFoundException {
        RentalService rs = new RentalService();

        Formatter output = new Formatter(new File(outputFile));

        for (Rental r : listRental) {
            double chargedValue = rs.calculateValue(listRental, r.getUser());
            output.format(r.getBike().getId() + ";" + r.getUnlockDate().getSecond() + ";" + r.getLockDate().getSecond() + ";" + r.getPickUpPark().getLatitude() + ";" + r.getPickUpPark().getLongitude() + ";" + r.getLeftAtPark().getLatitude() + ";" + r.getLeftAtPark().getLongitude() + ";" + (r.getUnlockDate().getSecond() - r.getLockDate().getSecond()) + ";" + chargedValue);
            output.format("%n");
        }
        output.close();
    }

    /**
     * Method that allows the facade to obtain the report of all the routes.
     *
     * @param listRoute List of all the routes in the system.
     * @throws FileNotFoundException
     */

    public static void getPathReport(List<Route> listRoute, String outputFileName) throws FileNotFoundException {
        Formatter output = new Formatter(new File(outputFileName));
        int counter = 0;

        for (Route r : listRoute) {
            output.format("Path" + counter);
            output.format("%n");
            output.format("total_distance:" + r.getTotalDistance());
            output.format("%n");
            output.format("total_energy:" + r.getTotalEnergy());
            output.format("%n");
            output.format("elevation:" + r.getElevation());
            output.format("%n");
            for (Location l : r.getPath()) {
                output.format(l.getLatitude() + ";" + l.getLongitude());
                output.format("%n");
            }
            counter++;
        }
        output.close();
    }


}
