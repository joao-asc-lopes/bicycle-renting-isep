package lapr.project.controller.admin;

import lapr.project.model.bikenetwork.PathFacade;


public class CreatePathController {

    /**
     * Instance of PathFacade
     */
    private PathFacade pf;

    public CreatePathController(){
        this.pf = new PathFacade();
    }

    /**
     * Method that adds a Path to the database

     * @param windSpeed
     * @param windDir
     * @param kineticFriction
     * @return
     */
    public boolean createPath(double lat1, double long1, double lat2, double long2, double windSpeed, double windDir, double kineticFriction){
        return this.pf.addPath( lat1,  long1,  lat2,  long2,windSpeed,windDir,kineticFriction);
    }



}
