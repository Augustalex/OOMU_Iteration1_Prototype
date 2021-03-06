package serviceInitiatorService;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import hostProviderService.HostService;
import rest.ReST;

import java.net.HttpURLConnection;

/**
 * Created by August on 2016-11-29.
 */
public class ServiceInitiatorAPI extends ReST {

    private final ServiceInitiatorContainer container;
    private Gson gson = new Gson();

    private final IServiceInitiator initiator;

    public ServiceInitiatorAPI(IServiceInitiator initiatorService, ServiceInitiatorContainer container){
        this.initiator = initiatorService;
        this.container = container;
    }
    
    @Override
    public void onGet(HttpExchange httpExchange) throws Exception {
        sendEmptyResponse(HttpURLConnection.HTTP_BAD_METHOD, httpExchange);
    }

    @Override
    public void onPost(HttpExchange httpExchange) throws Exception {
        System.out.println("\nPOST REQUEST IN SERVICE INITIATOR ");
        String body = getStringBodyFromHttpExchange(httpExchange);
        //HostService hostService = getObjectFromJsonInHttpExchange(httpExchange, HostService.class);

        initiator.initiateServiceContainer(gson.fromJson(body, HostService.class));
        System.out.println("Service initiated.");
        try{
            sendStringContentResponse(HttpURLConnection.HTTP_OK, "", httpExchange);
            System.out.println("Sent response.");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Could not send response.");
        }
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
