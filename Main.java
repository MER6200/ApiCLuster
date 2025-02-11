import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) throws Exception {

        String apikey = "71ac5191-1331-4dab-9d4c-224dc03fa037";

        //liste d'adresse que l'on recupere d'un ficher
        ArrayList<Address> adr = new ArrayList<>();
        ExcelReader fichier = new ExcelReader();
        fichier.read("inputClustering.xlsx");
        adr = fichier.getAdr();

        Routing routing = new Routing();
        routing.setCost_per_meter(4);
        routing.setCost_per_second(0);
        routing.setProfile("car");

        Clustering clustering = new Clustering();
        clustering.setNum_clusters(4);
        clustering.setMax_quantity(30);
        clustering.setMin_quantity(10);

        Configuration config = new Configuration();
        config.setResponse_type("json");
        config.setClustering(clustering);
        config.setRouting(routing);


        ArrayList<Customer> customers = new ArrayList<>();

        //creation de la liste client
        for (int i = 0; i < adr.size(); i++) {
            Customer customer = new Customer();
            customer.setAddress(adr.get(i));
            String id = String.valueOf(i);
            customer.setId(id);
            customer.setQuantity(0);
            customers.add(customer);
        }


        Root root = new Root();
        root.setCustomers(customers);
        root.setConfiguration(config);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(root);

        System.out.println(jsonRequest);


        HttpRequest postrequest = HttpRequest.newBuilder()
                .uri(new URI("https://graphhopper.com/api/1/cluster?key=" + apikey))
                .header("Content-type", "application/json")
                .POST(BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();


        String json1;
        while (true) {

            HttpResponse<String> postResponse = httpClient.send(postrequest, HttpResponse.BodyHandlers.ofString());
            json1 = postResponse.body();
            System.out.println(postResponse.body());
            root = gson.fromJson(postResponse.body(), Root.class);

            if ("finished".equals(root.getStatus())) {
                break;
            }

            Thread.sleep(500);
        }
        // Cas de + de 10 secondes donc on utilise l'autre url de l'API
        if ("Bad Request".equals(root.getMessage())) {

            HttpRequest postrequest2 = HttpRequest.newBuilder()
                    .uri(new URI("https://graphhopper.com/api/1/cluster/calculate?key=" + apikey))
                    .header("Content-type", "application/json")
                    .POST(BodyPublishers.ofString(jsonRequest))
                    .build();


            HttpResponse<String> postResponse2 = httpClient.send(postrequest2, HttpResponse.BodyHandlers.ofString());
            root = gson.fromJson(postResponse2.body(),
                    Root.class);


            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://graphhopper.com/api/1/cluster/solution/" + root.getJob_id() + apikey))
                    .header("Content-type", "application/json")
                    .build();

            while (true) {
                HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                root = gson.fromJson(getResponse.body(),
                        Root.class);

                System.out.println(root.getStatus());


                if ("finished".equals(root.getStatus())) {
                    break;
                }

                Thread.sleep(500);
            }
        }

        //Récuperation des résultats
        ArrayList<Cluster> clusters = root.getClusters();

        for (int i = 0; i < clusters.size(); i++) {
            clusters.get(i).IdToAdr(customers);
        }
        //Affichage des clusters
        System.out.println("Il y'a " + clusters.size() + " clusters");

        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster : " + (i +1));
            for (int j = 0; j < clusters.get(i).getAdr().size(); j++) {
                String col = String.valueOf(i);

                System.out.println(clusters.get(i).getAdr().get(j).getLat() + " " + clusters.get(i)
                        .getAdr().get(j).getLon());
            }
        }
        ArrayList<ClusterJson> cluJ = new ArrayList<>();
        for (int i = 0; i < clusters.size(); i++) {
            ClusterJson cluJson = new ClusterJson();
            cluJson.setAdr(clusters.get(i).getAdr());

            cluJ.add(cluJson);
        }

        try (Writer writer = new FileWriter("CluJson.json")) {
            Gson gsonW = new GsonBuilder().create();
            gsonW.toJson(cluJ, writer);
        }


    }


}