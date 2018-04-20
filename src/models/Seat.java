package models;

public class Seat {

    private boolean free;
    private boolean forTheAllergic;
    private boolean wheelChairAccess;
    private boolean petAllowed;
    private boolean familyCluster;

    public Seat(boolean free, boolean forTheAllergic, boolean wheelChairAccess, boolean petAllowed, boolean familyCluster) {
        this.free = free;
        this.forTheAllergic = forTheAllergic;
        this.wheelChairAccess = wheelChairAccess;
        this.petAllowed = petAllowed;
        this.familyCluster = familyCluster;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void normalizeSeat(){
        forTheAllergic = false;
        familyCluster = false;
        wheelChairAccess = false;
        petAllowed = false;
    }

    public boolean isForTheAllergic() {
        return forTheAllergic;
    }

    public void setForTheAllergic(boolean forTheAllergic) {
        this.forTheAllergic = forTheAllergic;
    }

    public boolean isWheelChairAccess() {
        return wheelChairAccess;
    }

    public void setWheelChairAccess(boolean wheelChairAccess) {
        this.wheelChairAccess = wheelChairAccess;
    }

    public boolean isPetAllowed() {
        return petAllowed;
    }

    public void setPetAllowed(boolean petAllowed) {
        this.petAllowed = petAllowed;
    }

    public boolean isFamilyCluster() {
        return familyCluster;
    }

    public void setFamilyCluster(boolean familyCluster) {
        this.familyCluster = familyCluster;
    }
}
