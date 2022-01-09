package lapr.project.assessment.file;

import lapr.project.controller.admin.AddParkController;
import lapr.project.controller.admin.CreateBicycleController;
import lapr.project.controller.admin.CreateInterestPointController;
import lapr.project.controller.admin.CreatePathController;
import lapr.project.controller.user.LockBicycleController;
import lapr.project.controller.user.UserRegistrationController;
import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.Location;
import lapr.project.model.park.Park;
import lapr.project.utils.InvalidDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FileReader {

    private FileReader() {
    }

    /**
     * Method that imports data from a text file containing Parks
     *
     * @param parkFile
     */
    public static int readParkFile(File parkFile) {

        AddParkController addParkC = new AddParkController();
        int cont = 0;
        try (Scanner ler1 = new Scanner(parkFile)) {
            while (ler1.hasNext()) {
                String linhaTxt = ler1.nextLine();
                if (!linhaTxt.startsWith("#")) {
                    String[] temp = linhaTxt.split(";");
                    try {
                        addParkC.addPark(temp[3], Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Integer.parseInt(temp[4]), Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[5]), Double.parseDouble(temp[7]), Double.parseDouble(temp[8]), Double.parseDouble(temp[2]));
                        cont++;
                    } catch (InvalidDataException ide) {
                        //do nothing
                    }
                }
            }

        } catch (FileNotFoundException e) {
            //Do nothing, did not find file.
        }
        return cont;
    }


    /**
     * Method that imports data from a text file containing Interest Points
     *
     * @param interestPointFile
     */
    public static List<Location> readInterestPointFIle(File interestPointFile) {
        CreateInterestPointController createIPC = new CreateInterestPointController();
        List<Location> lista = new ArrayList<>();
        try (Scanner ler1 = new Scanner(interestPointFile)) {
            while (ler1.hasNext()) {
                String linhaTxt = ler1.nextLine();
                if (!linhaTxt.startsWith("#")) {
                    String[] temp = linhaTxt.split(";");
                    try {
                        lista.add(createIPC.addInterestPoint(temp[3], Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2])));
                    } catch (InvalidDataException ide) {
                        //do nothing
                    }
                }
            }

        } catch (FileNotFoundException e) {
            //do nothing did not find file.
        }
        return lista;
    }

    /**
     * Method that imports data from a text file containing Users
     *
     * @param userFile
     */
    public static int readUserFile(File userFile) {
        UserRegistrationController userRegistC = new UserRegistrationController();
        int cont = 0;
        try (Scanner ler1 = new Scanner(userFile)) {
            while (ler1.hasNext()) {
                String linhaTxt = ler1.nextLine();
                if (!linhaTxt.startsWith("#")) {
                    String[] temp = linhaTxt.split(";");
                    try {
                        userRegistC.registerUser(temp[0], temp[1], "password_by_default", "salt", Float.parseFloat(temp[3]), Float.parseFloat(temp[2]), Long.parseLong(temp[5]), Double.parseDouble(temp[4]));
                        cont++;
                    } catch (InvalidDataException ide) {
                        //do nothing
                    }
                }
            }

        } catch (FileNotFoundException e) {
            //do nothing did not find file.
        }
        return cont;
    }

    /**
     * Method that imports data from a text file containing Bicycles
     *
     * @param bikeFile
     */
    public static int readBicycleFile(File bikeFile) {
        Park aux;
        CreateBicycleController createBikeC = new CreateBicycleController();
        AddParkController addParkC = new AddParkController();
        LockBicycleController lockC = new LockBicycleController();
        int cont = 0;
        try (Scanner ler1 = new Scanner(bikeFile)) {
            while (ler1.hasNext()) {
                String linhaTxt = ler1.nextLine();
                if (!linhaTxt.startsWith("#")) {
                    String[] temp = linhaTxt.split(";");
                    try {
                        if (("electric").equalsIgnoreCase(temp[2])) {
                            createBikeC.createElectricBicycle(temp[0], Bicycle.BicycleStatus.AVAILABLE, Float.parseFloat(temp[1]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]), 0, Double.parseDouble(temp[7]), Double.parseDouble(temp[8]));

                        } else if (("mtb").equalsIgnoreCase(temp[2])) {
                            createBikeC.createMountainBicycle(temp[0], Bicycle.BicycleStatus.AVAILABLE, Float.parseFloat(temp[1]), Double.parseDouble(temp[7]), Double.parseDouble(temp[8]));
                        } else if (("road").equalsIgnoreCase(temp[2])) {
                            createBikeC.createRoadBicycle(temp[0], Bicycle.BicycleStatus.AVAILABLE, Float.parseFloat(temp[1]), Double.parseDouble(temp[7]), Double.parseDouble(temp[8]));
                        } else {
                            throw new InvalidDataException("Bicycle Type is invalid, please verify your text file");
                        }
                        aux = addParkC.getParkWithCoordinates(Double.parseDouble(temp[3]), Double.parseDouble(temp[4]));
                        lockC.updateParkSlot(temp[0], aux.getIdLocation());
                        cont++;
                    } catch (InvalidDataException ide) {
                        //do nothing.
                        ide.printStackTrace();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            // no nothing did not find file.
            e.printStackTrace();
            return 0;
        }
        return cont;
    }

    /**
     * Method that imports data from a text file containing Paths
     *
     * @param pathFile
     */
    public static int readPathFile(File pathFile) {
        CreatePathController createPathC = new CreatePathController();
        int cont = 0;
        try (Scanner ler1 = new Scanner(pathFile)) {
            while (ler1.hasNext()) {
                String linhaTxt = ler1.nextLine();
                if (!linhaTxt.startsWith("#")) {
                    String[] temp = linhaTxt.split(";");
                    try {
                        if ("bi".equals(temp[4])) {
                            createPathC.createPath(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]), Double.parseDouble(temp[3]), Double.parseDouble(temp[7]), Double.parseDouble(temp[6]), Double.parseDouble(temp[5]));
                            createPathC.createPath(Double.parseDouble(temp[2]), Double.parseDouble(temp[3]), Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[7]), Double.parseDouble(temp[6]), Double.parseDouble(temp[5]));
                        } else if ("uni".equals(temp[4])) {
                            createPathC.createPath(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]), Double.parseDouble(temp[3]), Double.parseDouble(temp[7]), Double.parseDouble(temp[6]), Double.parseDouble(temp[5]));
                        } else {
                            throw new InvalidDataException("Path direction is invalid. Please verify your text file.");
                        }
                        cont++;
                    } catch (InvalidDataException ide) {
                        //do nothign did not find file.
                    }
                }
            }

        } catch (FileNotFoundException e) {
            //do nothing did not find file
        }

        return cont;
    }


}