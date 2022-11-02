import java.util.ArrayList;


public class Root{
    public Configuration configuration;
    public ArrayList<Customer> customers;

    private String status;
    private String job_id;
    private String message;
    private ArrayList<Cluster> clusters;



    public Configuration getConfiguration() {
        return configuration;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(ArrayList<Cluster> clusters) {
        this.clusters = clusters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


