package lapr.project.controller.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.user.Rental;
import lapr.project.model.user.RentalService;
import lapr.project.model.user.User;
import lapr.project.model.user.UserFacade;
import lapr.project.utils.PaymentAPI;

import java.util.List;

public class RentalPaymentController {
    /**
     * Variable with a instance of UserFacade
     */
    private UserFacade userFacade;

    private RentalService rs;
    /**
     * Builder method
     */
    public RentalPaymentController(){
        this.userFacade=new UserFacade();
        this.rs = new RentalService();
    }


    /**
     * Method that creates a receipt associated with the user received
     * @param username username of the user
     * @return True if operation works with success, false otherwise
     */
    public boolean generateReceipt(String username){
        User aux = null;
        //Check if user exists
        try{
            aux = this.getUser(username);
        }
        catch(IllegalArgumentException e){
            return false;
        }
        //Check the list of rentals that the user didn't paid yet
        List<Rental> lista = this.returnUserUnpaidRentalsTotal(username);
        if(lista.isEmpty()){
            return false;
        }
        //Calculate the value of the receipt
        double value = calculateValue(lista, aux);
        //Debits from the user account the value calculated
        PaymentAPI.payReceipt(username,value);
        //Creates the receipt and associates with the rentals in the DB
        int idReceipt = this.userFacade.createReceipt(value,23);
        for(Rental r: lista){
            if(!this.userFacade.insertReceiptRental(r.getId(),idReceipt)){
                return false;
            }
        }
        return true;
    }

    /**
     * Method that calculates the value from the sum of the length of the rentals received
     * @param lista List of rentals
     * @return The value user must pay
     */
    public double calculateValue(List<Rental> lista, User u){
        return this.userFacade.calculateValue(lista, u);
    }

    public List<Rental> returnUserUnpaidRentalsOfMonth(String username, int month){
        return this.userFacade.getUnpaidRentalsOfMonth(username, month);
    }

    public List<Rental> returnUserUnpaidRentalsTotal(String username){
        return this.userFacade.getUnpaidRentalsTotal(username);
    }

    public User getUser(String username){
        return this.userFacade.getUser(username);
    }

    /**
     * Method that gets all rentals
     *
     * @return List with the rentals
     */
    public List<Rental> getAllRentals(){
        return this.userFacade.getAllRentals();
    }


    public Bicycle getUserActiveRentalBicycle(String username){
        return this.userFacade.getUserActiveRentalBicycle(username);
    }

    /**
     * Returns the
     * @param idBike
     * @return
     */
    public long getTimeUnlckedBicycle(String idBike){
        return this.rs.getTimeBicycleUnlocked(idBike);
    }


}
