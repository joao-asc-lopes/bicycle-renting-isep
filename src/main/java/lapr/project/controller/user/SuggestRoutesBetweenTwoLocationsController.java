package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bikenetwork.BicycleNetwork;
import lapr.project.model.bikenetwork.Route;
import lapr.project.model.park.Location;
import lapr.project.model.user.User;

import java.util.List;

public class SuggestRoutesBetweenTwoLocationsController {

    private BicycleNetwork bn;

    public SuggestRoutesBetweenTwoLocationsController(){
        this.bn = new BicycleNetwork();
    }

    public List<Route> suggestRouteBetweenTwoLocations(Location from, Location to, Bicycle usedBike, User user, int maxNumberOfSuggestions, boolean ascendingOrder, String orderCriteria, List<Location> lstPOIs){
        return this.bn.suggestRouteBetweenTwoLocations(from, to, usedBike,  user,  maxNumberOfSuggestions,  ascendingOrder, orderCriteria, lstPOIs);
    }
}
