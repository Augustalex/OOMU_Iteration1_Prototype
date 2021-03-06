package adService;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import rest.Delivery;
import rest.PropertyDelivery;
import rest.ReST;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by August on 2016-12-01.
 */
public class AdServiceAPI extends ReST {

    private final AdService adService;

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    public AdServiceAPI(AdService service){
        this.adService = service;
    }

    /**
     * Retrieves a list of all AdPreferences.
     * @param httpExchange
     * @throws Exception
     */
    @Override
    public void onGet(HttpExchange httpExchange) throws Exception {
        System.out.println("GET on AdService");
        Delivery<String> responseBody = new PropertyDelivery<>();

        executorService.submit(() -> {
            responseBody.deliver(
                    new Gson().toJson(
                            adService.getAllAdPreferences()
                    )
            );
        });

        responseBody.onDelivery(body -> {
            try {
                sendStringContentResponse(HttpURLConnection.HTTP_OK, body, httpExchange);
            } catch (IOException e) {
                System.out.println("Could not send response. [AdServiceAPI]");
                e.printStackTrace();
            }
        });
    }

    /**
     * Posts a new AdPreference to the repository (AdService).
     * @param httpExchange
     * @throws Exception
     */
    @Override
    public void onPost(HttpExchange httpExchange) throws Exception {
        System.out.println("POST on AdService");
        String body = getStringBodyFromHttpExchange(httpExchange);
        adService.addAdPreference(new Gson().fromJson(body, AdPreference.class));

        sendEmptyResponse(HttpURLConnection.HTTP_OK, httpExchange);
    }

    @Override
    public void onDelete(HttpExchange httpExchange) throws Exception {
        sendEmptyResponse(HttpURLConnection.HTTP_BAD_METHOD, httpExchange);
    }

    @Override
    public void onPut(HttpExchange httpExchange) throws Exception {
        sendEmptyResponse(HttpURLConnection.HTTP_BAD_METHOD, httpExchange);
    }
}
