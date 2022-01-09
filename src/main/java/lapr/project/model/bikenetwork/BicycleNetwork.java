package lapr.project.model.bikenetwork;


import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.bicycle.BicycleService;
import lapr.project.model.bicycle.ElectricBicycle;
import lapr.project.model.park.InterestPoint;
import lapr.project.model.park.Location;
import lapr.project.model.park.LocationService;
import lapr.project.model.park.Park;
import lapr.project.model.user.User;
import lapr.project.utils.InvalidDataException;
import lapr.project.utils.PhysicsAlgorithms;
import lapr.project.utils.graph.Edge;
import lapr.project.utils.graph.Graph;
import lapr.project.utils.graph.GraphAlgorithms;

import java.io.IOException;
import java.util.*;

public class BicycleNetwork {

    /**
     * Service for bicycle related methods.
     */
    private BicycleService bikeServ;

    /**
     * Service for location related methods.
     */
    private LocationService locationServ;

    /**
     * Service for wind information related methods.
     */
    private PathService pathServ;
    /**
     * Represents a directed graph containing all the parks stored and the paths between them.
     */
    private Graph<Location, Path> bikeGraph;

    /**
     * The number of parks recommended to the user.
     */
    private static final int NUMBER_OF_PARKS_RECOMMENDED = 5;

    private static final String GRAPH_ERROR_MESSAGE = "Graph not initialized.";
    /**
     * Delta for comparisons between distance and energy.
     */
    private static final int HIGHEST_ELEVATION_DIFF = 50;
    private static final int LOWEST_ELEVATION_DIFF = 25;
    private static final int POINTS_HIGHEST_ELEVATION_DIFF = 15;
    private static final int POINTS_LOWEST_ELEVATION_DIFF = 5;


    /**
     * Constructor of the class Bicycle Network.
     */
    public BicycleNetwork() {
        bikeServ = new BicycleService();
        locationServ = new LocationService();
        pathServ = new PathService();
        bikeGraph = new Graph<>(true);
    }

    /**
     * Initializes the graph with the various locations and paths.
     */
    public void loadData() {
        this.bikeGraph = new Graph<>(true);
        for (Park p : this.locationServ.getParkList()) {
            addLocation(p);
        }
        for (InterestPoint ip : this.locationServ.getAllInterestPoints()) {
            addLocation(ip);
        }
        int size = bikeGraph.numVertices() - 1;
        Location[] locations = bikeGraph.allkeyVerts();
        for (int i = 0; i < size; i++) {
            Location from = locations[i];
            Location to = locations[i + 1];
            Path temp = pathServ.getPath(from, to);
            if (temp != null) {
                addPath(from, to, temp);
            }
            Path temp2 = pathServ.getPath(to, from);
            if (temp != null) {
                addPath(to, from, temp2);
            }
        }
    }

    /**
     * Adds a location to the graph. Can be a park or an interest point.
     *
     * @param local Location to be inserted
     */
    public void addLocation(Location local) {
        this.bikeGraph.insertVertex(local);
    }

    /**
     * Adds a path to the graph.
     *
     * @param from The initial location.
     * @param to   The final location.
     * @param path The path to be inserted.
     */
    public void addPath(Location from, Location to, Path path) {
        double distance = PhysicsAlgorithms.distance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
        long distanceRounded = Math.round(distance);
        this.bikeGraph.insertEdge(from, to, path, distanceRounded);
    }


    /**
     * Generates a route list based on the data of a Dijkstra Algorithm.
     *
     * @param energies    Array with information of the minimal energy expense of each location to the initial one.
     * @param dist        Array with information of the minimal distance of each location to the initial one.
     * @param pathKeys    The predecessor(s) of each Location in the route.
     * @param initial     The initial Location in the route.
     * @param destination The destination Location in the route.
     * @return A List with all the shortest routes.
     */
    private List<Route> generateRouteList(double[] energies, double[] dist, ArrayList<LinkedList<Integer>> pathKeys, Location initial, Location destination) {
        LinkedList<Route> routeList = new LinkedList<>();
        getRoutes(bikeGraph, initial, destination, bikeGraph.allkeyVerts(), pathKeys, new LinkedList<>(), routeList);
        double energy = energies[bikeGraph.getKey(destination)];
        double distance = dist[bikeGraph.getKey(destination)];
        double elevation = initial.getAltitude() - destination.getAltitude();
        for (Route r : routeList) {
            r.setTotalEnergy(energy);
            r.setTotalDistance(distance);
            r.setElevation(elevation);
        }
        return routeList;
    }

    /**
     * Generates a List of routes with only one route. Used when the initial path is equal to the destination.
     *
     * @param onlyLocation The only location in the path.
     * @return A list with the only possible route.
     */
    private static List<Route> emptyRoute(Location onlyLocation) {
        LinkedList<Route> routeList = new LinkedList<>();
        Route result = new Route();
        result.addLocation(onlyLocation);
        result.setTotalEnergy(0);
        result.setTotalDistance(0);
        result.setElevation(onlyLocation.getAltitude());
        routeList.add(result);
        return routeList;
    }

    /**
     * Validates if a pair of parks(initial and destination are valid in the system
     *
     * @param initial     The initial park.
     * @param destination The destination park.
     */
    private void validateLocations(Location initial, Location destination) {
        if (initial == null || destination == null) {
            throw new IllegalArgumentException("Locations are null.");
        }
        if (!bikeGraph.validVertex(initial)) {
            throw new IllegalArgumentException("Initial location is not in the system.");
        }
        if (!bikeGraph.validVertex(destination)) {
            throw new IllegalArgumentException("Destination location is not in the system.");
        }
    }

    /**
     * @param initial     Initial park.
     * @param destination Destination Park.
     * @return The shortest route from the initial park to the destination park, by distance.
     */
    public List<Route> shortestPathByDistance(Location initial, Location destination) {
        if (bikeGraph == null) {
            throw new InvalidDataException(GRAPH_ERROR_MESSAGE);
        }
        validateLocations(initial, destination);
        if (initial.equals(destination)) {
            return emptyRoute(initial);
        }

        int nverts = bikeGraph.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        ArrayList<LinkedList<Integer>> pathKeys = new ArrayList<>();
        double[] dist = new double[nverts];
        double[] energies = new double[nverts];
        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            energies[i] = Double.MAX_VALUE;
            LinkedList<Integer> predecessors = new LinkedList<>();
            predecessors.add(-1);
            pathKeys.add(i, predecessors);
        }
        shortestPathByDistance(bikeGraph, initial, bikeGraph.allkeyVerts(), visited, pathKeys, dist, energies);
        validateIfPathExists(destination, pathKeys);
        return generateRouteList(energies, dist, pathKeys, initial, destination);
    }

    /**
     * Calculates and returns the shortest path by electric expense between two parks, for a given bicycle and a given user.
     *
     * @param loggedUser  The user who wants to know the information.
     * @param chosenBike  The chosen bicycle.
     * @param initial     The initial park to be iterated.
     * @param destination The final park to be iterated.
     * @return A list of the shortest path by electric expanse.
     * @throws IOException
     */
    public List<Route> shortestPathByEnergeticEfficiency(User loggedUser, Bicycle chosenBike, Location initial, Location destination) {
        if (bikeGraph == null) {
            throw new InvalidDataException(GRAPH_ERROR_MESSAGE);
        }
        validateLocations(initial, destination);
        if (loggedUser == null) {
            throw new IllegalArgumentException("No user logged in.");
        }
        if (chosenBike == null) {
            throw new IllegalArgumentException("No bicycle chosen.");
        }
        if (initial.equals(destination)) {
            return emptyRoute(initial);
        }
        int nverts = bikeGraph.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        ArrayList<LinkedList<Integer>> pathKeys = new ArrayList<>();
        double[] dist = new double[nverts];
        double[] energies = new double[nverts];
        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            energies[i] = Double.MAX_VALUE;
            LinkedList<Integer> predecessors = new LinkedList<>();
            predecessors.add(-1);
            pathKeys.add(i, predecessors);
        }
        shortestPathByEnergeticEfficiency(bikeGraph, initial, bikeGraph.allkeyVerts(), visited, pathKeys, dist, energies);
        validateIfPathExists(destination, pathKeys);
        return generateRouteList(energies, dist, pathKeys, initial, destination);
    }

    /**
     * Validates if a path
     *
     * @param destination The destination location.
     * @param pathKeys    A list containing the predecessors of each location in the path(s).
     * @throws InvalidDataException In the case the path does not exist.
     */
    private void validateIfPathExists(Location destination, ArrayList<LinkedList<Integer>> pathKeys) {
        int auxKey = -1;
        for (int key : pathKeys.get(bikeGraph.getKey(destination))) {
            if (key != -1) {
                auxKey = key;
            }
        }
        if (auxKey == -1) {
            throw new InvalidDataException("There is no path between the two locations");
        }
    }

    /**
     * Calculates the shortest path between two parks taking their electrical expense into consideration.
     *
     * @param graph     Graph of parks.
     * @param orig      Initial Park
     * @param locations Parks of the graph.
     * @param visited   Auxiliary array containing the visited parks with true.
     * @param pathKeys  Auxiliary array containing the keys in order of the path.
     * @param dist      Auxiliary array containing the minimum distances between the parks.
     * @param energy    Auxiliary array containing the minimum electric expenses between the parks.
     */
    private static void shortestPathByEnergeticEfficiency(Graph<Location, Path> graph, Location orig, Location[] locations,
                                                          boolean[] visited, ArrayList<LinkedList<Integer>> pathKeys, double[] dist, double[] energy) {
        int vOrigKey = graph.getKey(orig);
        dist[vOrigKey] = 0;
        energy[vOrigKey] = 0;
        while (vOrigKey != -1) {
            orig = locations[vOrigKey];
            visited[vOrigKey] = true;
            for (Location lAdj : graph.adjVertices(orig)) {
                Edge<Location, Path> edge = graph.getEdge(orig, lAdj);
                double electricExpense = edge.getElement().getElectricExpense();
                double distance = edge.getWeight();
                int vAdjKey = graph.getKey(lAdj);
                if (!visited[vAdjKey] && energy[vAdjKey] > energy[vOrigKey] + electricExpense) {
                    energy[vAdjKey] = energy[vOrigKey] + electricExpense;
                    dist[vAdjKey] = dist[vOrigKey] + distance;
                    pathKeys.get(vAdjKey).clear();
                    pathKeys.get(vAdjKey).add(vOrigKey);
                } else if (!visited[vAdjKey] && Double.compare(energy[vAdjKey], energy[vOrigKey] + electricExpense) == 0) {
                    pathKeys.get(vAdjKey).add(vOrigKey);
                }
            }
            vOrigKey = GraphAlgorithms.getVertMinDist(energy, visited);
        }
    }

    /**
     * Calculates the shortest path between two parks taking their electrical expense into consideration.
     *
     * @param graph     Graph of parks.
     * @param orig      Initial Park
     * @param locations Parks of the graph.
     * @param visited   Auxiliary array containing the visited parks with true.
     * @param pathKeys  Auxiliary array containing the keys in order of the path.
     * @param dist      Auxiliary array containing the minimum distances between the parks.
     * @param energy    Auxiliary array containing the minimum electric expenses between the parks.
     */
    private static void shortestPathByDistance(Graph<Location, Path> graph, Location orig, Location[] locations,
                                               boolean[] visited, ArrayList<LinkedList<Integer>> pathKeys, double[] dist, double[] energy) {
        int vOrigKey = graph.getKey(orig);
        dist[vOrigKey] = 0;
        energy[vOrigKey] = 0;
        while (vOrigKey != -1) {
            orig = locations[vOrigKey];
            visited[vOrigKey] = true;
            for (Location lAdj : graph.adjVertices(orig)) {
                Edge<Location, Path> edge = graph.getEdge(orig, lAdj);
                double electricExpense = edge.getElement().getElectricExpense();
                double distance = edge.getWeight();
                int vAdjKey = graph.getKey(lAdj);
                if (!visited[vAdjKey] && dist[vAdjKey] > dist[vOrigKey] + distance) {
                    energy[vAdjKey] = energy[vOrigKey] + electricExpense;
                    dist[vAdjKey] = dist[vOrigKey] + distance;
                    pathKeys.get(vAdjKey).clear();
                    pathKeys.get(vAdjKey).add(vOrigKey);
                } else if (!visited[vAdjKey] && Double.compare(dist[vAdjKey], dist[vOrigKey] + distance) == 0) {
                    pathKeys.get(vAdjKey).add(vOrigKey);
                }
            }
            vOrigKey = GraphAlgorithms.getVertMinDist(dist, visited);
        }
    }

    /**
     * Extracts from pathKeys the minimum path between vOrig and vDest
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    Initial location.
     * @param vDest    Destination location
     * @param pathKeys minimum path location keys
     * @param path     stack with the minimum path (correct order)
     */
    private static void getRoutes(Graph<Location, Path> g, Location vOrig, Location vDest, Location[] verts, ArrayList<LinkedList<Integer>> pathKeys, LinkedList<Location> path, LinkedList<Route> paths) {
        path.addFirst(vDest);
        if (!vOrig.equals(vDest)) {
            int vDestKey = g.getKey(vDest);
            for (int key : pathKeys.get(vDestKey)) {
                vDest = verts[key];
                getRoutes(g, vOrig, vDest, verts, pathKeys, path, paths);
            }

            //pathKeys.get(vDestKey).removeFirst();
        } else {
            paths.add(new Route(new LinkedList<>(path)));
        }
        path.removeFirst();
    }

    /**
     * @param initial           Initial park.
     * @param destination       Destination park.
     * @param lstInterestPoints List of interest points that the route must pass through.
     * @return The route containing the shortest distance.
     */
    public List<Route> shortestPathByDistancePassingByInterestPoints(Park initial, Park destination, List<Location> lstInterestPoints) {
        if (bikeGraph == null) {
            throw new InvalidDataException(GRAPH_ERROR_MESSAGE);
        }
        validateLocations(initial, destination);
        if (lstInterestPoints == null) {
            throw new IllegalArgumentException("List of interest points is null.");
        }
        if (lstInterestPoints.isEmpty()) {
            return shortestPathByDistance(initial, destination);
        }
        for (Location l : lstInterestPoints) {
            if (!bikeGraph.validVertex(l)) {
                throw new InvalidDataException("Interest Point not registered in system.");
            }
        }
        List<Route> possibleRoutes = generateAllPossibleRoutesForGivenPointsOfInterest(initial, destination, lstInterestPoints);
        Collections.sort(possibleRoutes);
        List<Route> result = new ArrayList<>();
        double minDist = possibleRoutes.get(0).getTotalDistance();
        boolean flag = true;
        for (int i = 0; i < possibleRoutes.size() && flag; i++) {
            if (Double.compare(minDist, possibleRoutes.get(i).getTotalDistance()) == 0) {
                result.add(possibleRoutes.get(i));
            } else {
                flag = false;
            }
        }
        return result;
    }

    /**
     * Generates and returns all the possible routes for a given list of points of interest.
     *
     * @param from              The initial Location.
     * @param to                The final location.
     * @param lstInterestPoints A list with all the interest points the route must go through.
     * @return A list with all the possible routes.
     */
    private List<Route> generateAllPossibleRoutesForGivenPointsOfInterest(Location from, Location to, List<Location> lstInterestPoints) {
        List<List<Location>> permutations = new ArrayList<>();
        permutations(lstInterestPoints.toArray(new Location[0]), lstInterestPoints.size(), permutations);
        List<Route> possibleRoutes = new ArrayList<>();
        for (List<Location> perm : permutations) {
            Route tempRoute = generateRouteFromPermutation(from, to, perm);
            possibleRoutes.add(tempRoute);
        }
        return possibleRoutes;
    }

    /**
     * Given a list of interest points, returns the total distance between them.
     *
     * @param lstIPs List of interest points.
     * @return The total distance between them.
     */
    private Route generateRouteFromPermutation(Location initial, Location last, List<Location> lstIPs) {
        double distance = 0;
        double elevation = initial.getAltitude() - last.getAltitude();
        Route route = new Route();
        LinkedList<Location> path = new LinkedList<>();
        distance += GraphAlgorithms.shortestPath(bikeGraph, initial, lstIPs.get(0), path);
        path.removeLast();
        route.addLocations(path);
        for (int i = 0; i < lstIPs.size() - 1; i++) {
            path = new LinkedList<>();
            distance += GraphAlgorithms.shortestPath(bikeGraph, lstIPs.get(i), lstIPs.get(i + 1), path);
            path.removeLast();
            route.addLocations(path);
        }
        path = new LinkedList<>();
        distance += GraphAlgorithms.shortestPath(bikeGraph, lstIPs.get(lstIPs.size() - 1), last, path);
        route.addLocations(path);
        route.setTotalDistance(distance);
        route.setElevation(elevation);
        return route;
    }

    /**
     * Returns a list with all the permutations of a given sequence of interest points.
     *
     * @param list   List of interest points.
     * @param n      Number of interest points in the list.
     * @param result A list with all the permutations.
     */
    private static void permutations(Location[] list, int n, List<List<Location>> result) {
        if (n == 1) {
            ArrayList<Location> temp = new ArrayList<>(Arrays.asList(list));
            result.add(temp);
        } else {
            for (int i = 0; i < n; i++) {
                permutations(list, n - 1, result);
                int j = (n % 2 == 0) ? i : 0;
                Location t = list[n - 1];
                list[n - 1] = list[j];
                list[j] = t;
            }
        }
    }


    /**
     * This method is the public method that returns the 5 closest parks from an User location.
     *
     * @param latitude  The latitude of the User location.
     * @param longitude The longitude of the User location.
     * @return ArrayList with the 5 nearest park given a location.
     */
    public ArrayList<Park> shortestParksFromUser(double latitude, double longitude) {

        ArrayList<Park> shortestParks = new ArrayList<>();
        int size = this.bikeGraph.numVertices();
        if (this.bikeGraph.numVertices() > NUMBER_OF_PARKS_RECOMMENDED) {
            size = NUMBER_OF_PARKS_RECOMMENDED;
        }
        ArrayList<Park> parkList = new ArrayList<>();
        for (Location local : this.bikeGraph.vertices()) {
            if (local instanceof Park) {
                parkList.add((Park) local);
            }
        }
        shortestParksFromUser(parkList, latitude, longitude, shortestParks, size);

        return shortestParks;

    }

    /**
     * Method that adds the NUMBER_OF_PARKS_RECOMMENDED nearest Parks given the latitude and longitude in
     * decimal degrees.
     *
     * @param latitude      The latitude passed as a parameter.
     * @param longitude     The longitude passed as a parameter.
     * @param shortestParks The shortest path that is going to be filled with
     *                      the 5 closest parks given coordinates as parameters.
     * @param size          number of times the method should be executed.
     */
    private static void shortestParksFromUser(List<Park> parkList, double latitude, double longitude, ArrayList<Park> shortestParks, int size) {
        for (int i = 0; i < size; i++) {
            double minAuxiliary = Double.MAX_VALUE;
            Park min = null;
            for (Park p : parkList) {
                if (!shortestParks.contains(p)) {
                    double tempPark = PhysicsAlgorithms.distance(latitude, longitude, p.getLatitude(), p.getLongitude());
                    if (tempPark < minAuxiliary) {
                        minAuxiliary = tempPark;
                        min = p;
                    }
                }
            }
            if (min != null) {
                shortestParks.add(min);
            }
        }
    }

    /**
     * Returns the distance of a user to a specific park.
     *
     * @param latitude  The latitude of the user passed as a parameter.
     * @param longitude The longitude of an user passed as a parameter.
     * @param p         The park that is user is destined to go passed as a parameter.
     * @return The distance of the user to the chosen park introduced as a
     * parameter.
     */
    public double distanceUserPark(double latitude, double longitude, Location p) {
        return PhysicsAlgorithms.distance(latitude, longitude, p.getLatitude(), p.getLongitude());
    }

    /**
     * Suggest a given number of routes between two locations, for a given criteria, ascending or descending.
     *
     * @param from                   The park from which the user will part.
     * @param to                     The park to which the user will arrive last.
     * @param usedBike               The bicycle used in the route.
     * @param user                   The user to ride the bicycle.
     * @param maxNumberOfSuggestions The number of route suggestions, should be lower than the number of interest points factorial.
     * @param ascendingOrder         True if ascending, false if descending.
     * @param orderCriteria          should be "energy", "shortest_path" or "number_of_points"
     * @param lstPOIs                List of points of interest the user should go through.
     * @return A list of routes that respects all the given criteria.
     */
    public List<Route> suggestRouteBetweenTwoLocations(Location from, Location to, Bicycle usedBike, User user, int maxNumberOfSuggestions, boolean ascendingOrder, String orderCriteria, List<Location> lstPOIs) {

        if (bikeGraph == null) {
            throw new InvalidDataException(GRAPH_ERROR_MESSAGE);
        }
        validateLocations(from, to);
        if (lstPOIs == null) {
            throw new IllegalArgumentException("List of interest points is null.");
        }
        if (!orderCriteria.equals("energy") && !orderCriteria.equals("shortest_path") && !orderCriteria.equals("number_of_points")) {
            throw new IllegalArgumentException("Order criteria not supported.");
        }
        if (lstPOIs.isEmpty()) {
            List<Route> result = shortestPathByEnergeticEfficiency(user, usedBike, from, to);
            if (result.size() > 1) {
                orderListOfRoutes(orderCriteria, ascendingOrder, result);
            }
            return result;
        }
        for (Location l : lstPOIs) {
            if (!bikeGraph.validVertex(l)) {
                throw new InvalidDataException("Interest Point not registered in system.");
            }
        }
        List<Route> result = generateAllPossibleRoutesForGivenPointsOfInterest(from, to, lstPOIs);
        if (result.size() < maxNumberOfSuggestions) {
            maxNumberOfSuggestions = result.size();
        }
        orderListOfRoutes(orderCriteria, ascendingOrder, result);
        List<Route> croppedResult = new ArrayList<>();
        for (int i = 0; i < maxNumberOfSuggestions; i++) {
            croppedResult.add(result.get(0));
        }
        return croppedResult;
    }

    /**
     * Orders a list of routes based on a given order criteria and order (ascending, descending).
     *
     * @param orderCriteria  should be "energy", "shortest_path" or "number_of_points"
     * @param ascendingOrder True if ascending, false if descending.
     * @param result         The list of routes to be ordered.
     */
    private void orderListOfRoutes(String orderCriteria, boolean ascendingOrder, List<Route> result) {
        switch (orderCriteria) {
            case "shortest_path":
                Collections.sort(result);
                break;

            case "energy":
                Comparator<Route> sortByEnergeticEfficiency = Route::compareEnergy;
                result.sort(sortByEnergeticEfficiency);
                break;

            case "number_of_paths":
                Comparator<Route> sortByNumberOfPoints = Comparator.comparingInt(Route::getNumberLocations);
                result.sort(sortByNumberOfPoints);
                break;
        }
        if (!ascendingOrder) {
            Collections.reverse(result);
        }
    }

    /**
     * Method that suggests a Bicycle, considering two different Parks
     *
     * @param p1 Initial park.
     * @param p2 Destination park.
     * @return The suggested electric bicycle.
     */
    public ElectricBicycle suggestBike(Park p1, Park p2, User user) throws IOException {
        ElectricBicycle eBike = null;
        double uncertaintyPerc = 1.10;
        double minimalCharge = Double.MAX_VALUE;
        for (ElectricBicycle electric : this.bikeServ.getElectricBicyclesList()) {
            double range;
            try {
                Path wind = this.pathServ.getPath(p1, p2);
                range = PhysicsAlgorithms.calculateDistanceCoveredByElectricalBicycle(electric.getFrontalArea(), electric.getWeight(),
                        electric.getBattery().getWeight(), wind.getWindSpeed(), wind.getWindDirection(), user.getWeight(), user.getHeight(),
                        electric.getBattery().getVoltage(), electric.getBattery().getCurrentCharge(),
                        p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude(),
                        electric.getBicycleAerodynamicalCoefficient(), p1.getAltitude(), p2.getAltitude());

            } //In case some problem calculating energy happens because of the
            //ElevationUtils method, throws InvalidDataException
            catch (Exception e) {
                throw new IOException("The inserted data is invalid.");
            }
            range = range * uncertaintyPerc;
            double charge = electric.getBattery().getCurrentCharge();
            double distance = PhysicsAlgorithms.distance(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
            if (distance <= range && charge < minimalCharge) {
                eBike = electric;
                minimalCharge = charge;
            }
        }

        if (eBike != null) {
            return eBike;
        } else {
            throw new IOException("There are no suitable bicycles for this scenario.");
        }
    }

    /**
     * Method that calculates points earned by a User, considering the Park its
     * trip started and the Park where it ended
     *
     * @param thisPark The ending park, the one where the bicycle was locked
     * @param park     The park where the bicycle was unlocked
     * @return
     */
    public int calculatePoints(Park thisPark, Park park) {
        if (thisPark.getAltitude() > park.getAltitude()) {
            if (thisPark.getAltitude() - park.getAltitude() >= HIGHEST_ELEVATION_DIFF) {
                return POINTS_HIGHEST_ELEVATION_DIFF;
            }
            if (thisPark.getAltitude() - park.getAltitude() >= LOWEST_ELEVATION_DIFF) {
                return POINTS_LOWEST_ELEVATION_DIFF;
            }
        }
        return 0;
    }

}
