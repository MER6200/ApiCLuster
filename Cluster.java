import java.util.ArrayList;

public class Cluster {

    public Center center;
    public double quantity;
    public ArrayList<String> ids;
    public ArrayList<Address> adr;

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public ArrayList<Address> getAdr() {
        return adr;
    }

    public void setAdr(ArrayList<Address> adr) {
        this.adr = adr;
    }
}
