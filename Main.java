import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        String apikey = "0dd5a728-adef-4078-8572-372111841b81";

        Routing routing = new Routing();
        routing.setCost_per_meter(1);
        routing.setCost_per_second(0);
        routing.setProfile("car");

        Clustering clustering = new Clustering();
        clustering.setNum_clusters(2);
        clustering.setMax_quantity(15);
        clustering.setMin_quantity(3);

        Configuration config = new Configuration();
        config.setResponse_type("json");
        config.setClustering(clustering);
        config.setRouting(routing);

        Address adress = new Address();
        adress.setLat(48.1399858);
        adress.setLon(11.5623796);

        Address adress1 = new Address();
        adress1.setLat(48.1815568);
        adress1.setLon(11.5845746);

        Address adress2 = new Address();
        adress2.setLat(48.0642917);
        adress2.setLon(11.6629469);

        Address adress3 = new Address();
        adress3.setLat(48.0657455);
        adress3.setLon(11.6608571);

        Address adress4 = new Address();
        adress4.setLat(48.2110597);
        adress4.setLon(11.3339193);

        Address adress5 = new Address();
        adress5.setLat(48.0992554);
        adress5.setLon(11.5198437);

        Address adress6 = new Address();
        adress6.setLat(48.2245948);
        adress6.setLon(11.6686808);

        Address adress7 = new Address();
        adress7.setLat(48.2252155);
        adress7.setLon(11.6711978);

        Address adress8 = new Address();
        adress8.setLat(48.2265436);
        adress8.setLon(11.6711835);

        Address adress9 = new Address();
        adress9.setLat(48.0907059);
        adress9.setLon(11.5562737);

        Address adress10 = new Address();
        adress10.setLat(48.1282873);
        adress10.setLon(11.3755046);

        Address adress11 = new Address();
        adress11.setLat(48.0669554);
        adress11.setLon(11.3798203);

        Address adress12 = new Address();
        adress12.setLat(48.0721732);
        adress12.setLon(11.373854);

        Address adress13 = new Address();
        adress13.setLat(48.06192);
        adress13.setLon(11.6738617);

        Address adress14 = new Address();
        adress14.setLat(48.1548502);
        adress14.setLon(11.5668069);



        Customer customer = new Customer();
        customer.setAddress(adress);
        customer.setId("1");
        customer.setQuantity(1);

        Customer customer1 = new Customer();
        customer1.setAddress(adress1);
        customer1.setId("2");
        customer1.setQuantity(1);

        Customer customer2 = new Customer();
        customer2.setAddress(adress2);
        customer2.setId("3");
        customer2.setQuantity(1);

        Customer customer3 = new Customer();
        customer3.setAddress(adress3);
        customer3.setId("4");
        customer3.setQuantity(1);

        Customer customer4 = new Customer();
        customer4.setAddress(adress4);
        customer4.setId("5");
        customer4.setQuantity(1);

        Customer customer5 = new Customer();
        customer5.setAddress(adress5);
        customer5.setId("6");
        customer5.setQuantity(1);

        Customer customer6 = new Customer();
        customer6.setAddress(adress6);
        customer6.setId("7");
        customer6.setQuantity(1);

        Customer customer7 = new Customer();
        customer7.setAddress(adress7);
        customer7.setId("8");
        customer7.setQuantity(1);

        Customer customer8 = new Customer();
        customer8.setAddress(adress8);
        customer8.setId("9");
        customer8.setQuantity(1);

        Customer customer9 = new Customer();
        customer9.setAddress(adress9);
        customer9.setId("10");
        customer9.setQuantity(1);

        Customer customer10 = new Customer();
        customer10.setAddress(adress10);
        customer10.setId("11");
        customer10.setQuantity(1);

        Customer customer11 = new Customer();
        customer11.setAddress(adress11);
        customer11.setId("12");
        customer11.setQuantity(1);

        Customer customer12 = new Customer();
        customer12.setAddress(adress12);
        customer12.setId("13");
        customer12.setQuantity(1);

        Customer customer13 = new Customer();
        customer13.setAddress(adress13);
        customer13.setId("14");
        customer13.setQuantity(1);

        Customer customer14 = new Customer();
        customer14.setAddress(adress14);
        customer14.setId("15");
        customer14.setQuantity(1);

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        customers.add(customer6);
        customers.add(customer7);
        customers.add(customer8);
        customers.add(customer9);
        customers.add(customer10);
        customers.add(customer11);
        customers.add(customer12);
        customers.add(customer13);
        customers.add(customer14);





        Root root= new Root();
        root.setCustomers(customers);
        root.setConfiguration(config);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(root);

        System.out.println(jsonRequest);


        HttpRequest postrequest = HttpRequest.newBuilder()
                .uri(new URI("https://graphhopper.com/api/1/cluster?key="+apikey))
                .header("Content-type","application/json")
                .POST(BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();


        while(true){

            HttpResponse<String> postResponse = httpClient.send(postrequest, HttpResponse.BodyHandlers.ofString());
            root = gson.fromJson(postResponse.body(),Root.class);

            if("finished".equals(root.getStatus())){
                break;
            }

            Thread.sleep(500);
        }

        // Cas de + de 10 secondes donc on utilise l'autre url de l'API
        if("Bad Request".equals(root.getMessage())) {

            HttpRequest postrequest2 = HttpRequest.newBuilder()
                    .uri(new URI("https://graphhopper.com/api/1/cluster/calculate?key="+apikey))
                    .header("Content-type","application/json")
                    .POST(BodyPublishers.ofString(jsonRequest))
                    .build();


            HttpResponse<String> postResponse2 = httpClient.send(postrequest2, HttpResponse.BodyHandlers.ofString());
            root = gson.fromJson(postResponse2.body(),Root.class);


            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://graphhopper.com/api/1/cluster/solution/"+root.getJob_id()+apikey))
                    .header("Content-type","application/json")
                    .build();

            while(true){
                HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                root = gson.fromJson(getResponse.body(),Root.class);

                System.out.println(root.getStatus());


                if("finished".equals(root.getStatus())){
                    break;
                }

                Thread.sleep(500);
            }
        }

        ArrayList<Cluster> clusters= root.getClusters();
        System.out.println(clusters);
        System.out.println(clusters.get(1).getIds());
        System.out.println(clusters.get(0).getIds());
//      System.out.println(clusters.get(1).getCenter().getLat());
//      System.out.println(clusters.get(1).getCenter().getLon());


    }
}