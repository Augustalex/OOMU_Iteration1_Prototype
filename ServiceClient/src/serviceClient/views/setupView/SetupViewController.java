package serviceClient.views.setupView;

import hostProviderService.Host;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import rest.Delivery;
import rest.PropertyDelivery;
import serviceClient.localServiceDirectory.LocalServiceDirectory;
import serviceClient.portAvailibilityChecker.PortAvailabilityChecker;
import serviceClient.utilityServices.UtilityServiceFactory;
import serviceClient.utilityServices.UtilityServicesDirectoryProxy;

import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by August on 2016-12-13.
 */
public class SetupViewController implements Initializable{

    private final UtilityServicesDirectoryProxy utilityServices;
    private LocalServiceDirectory directory;

    @FXML
    private TextField directoryPortInput;

    @FXML
    private TextField directoryIpInput;

    @FXML
    private Button submitDirectory;

    @FXML
    private TextField providerPortInput;

    @FXML
    private TextField providerIpInput;

    @FXML
    private Button submitProvider;

    @FXML
    private TextField initiatorPortInput;

    @FXML
    private Button checkInitiatorPort;

    @FXML
    private Button submitInitiator;

    public SetupViewController(UtilityServicesDirectoryProxy utilityServices, LocalServiceDirectory directory){
        this.utilityServices = utilityServices;
        this.directory = directory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        captureDirectorySubmit();
        captureProviderSubmit();
        captureInitiatorSubmit();
    }

    private void captureDirectorySubmit() {
        submitDirectory.setOnMouseClicked(e -> {
            int port = Integer.parseInt(directoryPortInput.getText());
            String ip = directoryIpInput.getText();

            directory.setOnlineDirectoryDetails(new Host(ip, port));
        });
    }

    private void captureProviderSubmit(){
        submitProvider.setOnMouseClicked(e -> {
            try {
                utilityServices.setHostProviderDetails(
                        new Host(
                                providerIpInput.getText(),
                                Integer.parseInt(providerPortInput.getText())
                        ));
            }
            catch(NumberFormatException ex){
                System.out.println("Port must be only integers.");
            }
        });
    }

    private void captureInitiatorSubmit(){
        submitInitiator.setOnMouseClicked(e -> {
            System.out.println("Caught submit press.");
            stopInitiatorService()
                    .onDelivery(c -> {
                        try {
                            startNewInitiatorService(Integer.parseInt(initiatorPortInput.getText()));
                        } catch (IOException e1) {
                            System.out.println("Could not start server.");
                            e1.printStackTrace();
                        }
                        catch(NumberFormatException ex){
                            System.out.println("Port number was not an integer! [SetupViewController - captureInitiatorSubmit]");
                        }
                        finally{
                            System.out.println("Started new initiator service.");
                        }
                    });

        });
    }

    private Delivery<Boolean> stopInitiatorService(){
        Delivery<Boolean> stopPromise = new PropertyDelivery<>();

        new Thread(() -> {
            System.out.print("\nStarted shutdown of server.");
            utilityServices.getServiceInitiator()
                    .getContainer().stop(1);

            System.out.print("\tDone.\n");
            stopPromise.deliver(true);
        }).start();

        return stopPromise;
    }

    private void startNewInitiatorService(int port) throws IOException {
        utilityServices.setServiceInitiator(UtilityServiceFactory.newServiceInitiator(port, directory));
    }
}
