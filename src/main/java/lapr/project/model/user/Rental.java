/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lapr.project.model.user;

import lapr.project.model.bicycle.Bicycle;
import lapr.project.model.park.Park;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;


public class Rental {

    /**
     * Represents the possible states of a Rental.
     * Active (0), Finished (1).
     */
    public enum RentalStatus {
        ACTIVE(0), FINISHED(1);
        private final int statusCode;

        /**
         * @param statusCode
         */
        RentalStatus(final int statusCode) {
            this.statusCode = statusCode;
        }

        /**
         * Returns the associated code with the enum.
         *
         * @return 1 if ACTIVE, 2 if FINISHED.
         */
        public int statusCode() {
            return statusCode;
        }
    }

    public enum RentalPayment{
        NOT_PAID(0), PAID(1);
        private final int paymentCode;

        RentalPayment(int paymentCode) {
            this.paymentCode = paymentCode;
        }

        public int paymentCode(){
            return paymentCode;
        }
    }


    /**
     * Variável que armazena o id da Rental
     */
    private int id;
    /**
     * Variável que armazena a bicicleta associada à Rental
     */
    private Bicycle bike;
    /**
     * Variável que armazena o user associado à Rental
     */
    private User user;
    /**
     *
     */
    private Park pickUpPark;
    /**
     *
     */
    private Park leftAtPark;
    /**
     * Variável que indica quando o user começou a usar a bicicleta
     */
    private LocalDateTime unlockDate;
    /**
     * Variável que indica quando o user acabou de user a bicicleta
     * (null se ainda estiver a usa-la)
     */
    private LocalDateTime lockDate;
    /**
     * Variável que indica o estado da Rental
     */
    private RentalStatus state;
    /**
     * Variável que indica o tempo que o user usou a bicicleta
     * (null se ainda estiver a usa-la)
     */
    private long length;

    private RentalPayment rentalPayment;

    /**
     * Método construtor para uma Rental que ainda não foi terminada
     *
     * @param id          Id da rental
     * @param bike        Bicicleta
     * @param user        User
     * @param unlockDate Data e Hora em que o user começou a usar a bicicleta
     */
    public Rental(int id, Bicycle bike, User user, Park pickUpPark, LocalDateTime unlockDate) {
        this.id = id;
        this.bike = bike;
        this.user = user;
        this.pickUpPark=pickUpPark;
        this.unlockDate = unlockDate;
        this.state = RentalStatus.ACTIVE;

        this.leftAtPark=null;
        this.lockDate = null;
        this.length = 0;
        this.rentalPayment=RentalPayment.NOT_PAID;
    }

    /**
     * Método construtor para uma Rental já terminada
     *
     * @param id          Id da rental
     * @param bike        Bicicleta
     * @param user        User
     * @param unlockDate Data e Hora em que o user começou a usar a bicicleta
     * @param lockDate   Data e Hora em que o user parou de usar a bicicleta
     */
    public Rental(int id, Bicycle bike, User user, Park pickUpPark, Park leftAtPark, LocalDateTime unlockDate, LocalDateTime lockDate) {
        this.id = id;
        this.bike = bike;
        this.user = user;
        this.pickUpPark=pickUpPark;
        this.leftAtPark=leftAtPark;
        this.unlockDate = unlockDate;
        this.lockDate = lockDate;
        this.state = RentalStatus.FINISHED;
        this.rentalPayment=RentalPayment.NOT_PAID;
        this.setLength();
    }

    public Rental(int id, Bicycle bike, User user, Park pickUpPark, Park leftAtPark, LocalDateTime unlockDate, LocalDateTime lockDate, RentalStatus status) {
        this.id = id;
        this.bike = bike;
        this.user = user;
        this.pickUpPark=pickUpPark;
        this.leftAtPark=leftAtPark;
        this.unlockDate = unlockDate;
        this.lockDate = lockDate;
        this.state = status;
        this.rentalPayment=RentalPayment.NOT_PAID;
        this.setLength();
    }

    public Rental(int id, Bicycle bike, User user, Park pickUpPark, Park leftAtPark, LocalDateTime unlockDate, LocalDateTime lockDate, RentalStatus state, RentalPayment rentalPayment) {
        this.id = id;
        this.bike = bike;
        this.user = user;
        this.pickUpPark = pickUpPark;
        this.leftAtPark = leftAtPark;
        this.unlockDate = unlockDate;
        this.lockDate = lockDate;
        this.state = state;
        this.rentalPayment = rentalPayment;
        this.setLength();
    }

    /**
     * Método que devolve o id da Rental
     *
     * @return Id da Rental
     */
    public int getId() {
        return id;
    }

    /**
     * Método que define um novo id da Rental
     *
     * @param id Novo id da Rental
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método que devolve a bicicleta usada
     *
     * @return Id da bicicleta
     */
    public Bicycle getBike() {
        return bike;
    }

    /**
     * Método que define um novo id da bicicleta
     *
     * @param bike Novo id da bicicleta
     */
    public void setBike(Bicycle bike) {
        this.bike = bike;
    }

    /**
     * Método que devolve o user
     *
     * @return user
     */
    public User getUser() {
        return user;
    }


    /**
     * Método que define um novo user
     *
     * @param user Novo user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public Park getPickUpPark() {
        return pickUpPark;
    }

    public void setPickUpPark(Park pickUpPark) {
        this.pickUpPark = pickUpPark;
    }

    public Park getLeftAtPark() {
        return leftAtPark;
    }

    public void setLeftAtPark(Park leftAtPark) {
        this.leftAtPark = leftAtPark;
    }

    /**
     * Método que devolve a data em que o utilizador começou a usar a bicicleta
     *
     * @return Data e Hora em que o utilizador começou a usar a bicicleta
     */
    public LocalDateTime getUnlockDate() {
        return unlockDate;
    }

    /**
     * Método que define uma nova data em que o utilizador começou a usar a bicicleta
     *
     * @param unlockDate Nova Data e Hora em que o utilizador começou a usar a bicicleta
     */
    public void setUnlockDate(LocalDateTime unlockDate) {
        this.unlockDate = unlockDate;
    }

    /**
     * Método que devolve a data em que o utilizador parou a usar a bicicleta
     *
     * @return Data e Hora em que o utilizador começou a usar a bicicleta
     */
    public LocalDateTime getLockDate() {
        return lockDate;
    }

    /**
     * Método que define uma nova data em que o utilizador parou a usar a bicicleta
     *
     * @param lockDate Nova Data e Hora em que o utilizador começou a usar a bicicleta
     */
    public void setLockDate(LocalDateTime lockDate) {
        this.lockDate = lockDate;
    }

    /**
     * Método que devolve o estado da Rental
     *
     * @return Estado da Rental
     */
    public RentalStatus getState() {
        return state;
    }

    /**
     * Método que define um novo estado da Rental
     *
     * @param state Novo estado da Rental
     */
    public void setState(RentalStatus state) {
        this.state = state;
    }

    /**
     * Método que devolve a duração do tempo que o user esteve com a bicicleta
     *
     * @return Tempo em que o user esteve com a bicicleta
     */
    public long getLength() {
        return length;
    }

    /**
     * Método que define uma nova duração do tempo em que o user esteve com a bicicleta
     */
    public void setLength() {
        if(this.unlockDate != null && this.lockDate != null) {
            this.length = Duration.between(this.unlockDate, this.lockDate).getSeconds();
        }
    }

    public RentalPayment getRentalPayment() {
        return rentalPayment;
    }

    public void setRentalPayment(RentalPayment rentalPayment) {
        this.rentalPayment = rentalPayment;
    }

    /**
     * Equals method.
     * @param obj The object we are trying to compare.
     * @return True if both objects are the same. False if not.
     */


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Rental rental = (Rental) obj;
        return id == rental.id;
    }

    /**
     * Method representative of the hashcode.
     * @return int representative of the hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
