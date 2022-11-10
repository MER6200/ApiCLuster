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

    // Je retransforme les ids en adresse car le json fourni uniquement les ids
    public void IdToAdr(ArrayList<Customer> customers){

            ArrayList<Address> tmp = new ArrayList<>();
            for(int j=0;j<ids.size();j++){


                for(int k = 0;k<customers.size();k++){
                    if(ids.get(j).equals(customers.get(k).getId())){
                        tmp.add(customers.get(k).getAddress());
                    }
                }
            this.adr= tmp;
            }
    }


}
