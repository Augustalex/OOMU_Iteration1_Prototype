package views.systemLogin;

import arena.session.Session;
import arena.users.IUser;
import arena.users.Player;
import arena.users.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.FXMLViewController;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Johan on 2016-12-02.
 */
public class SystemLoginViewController extends FXMLViewController implements Initializable {

    @FXML
    private VBox loginWindow;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text forgotPasswordText;

    @FXML
    private Button guestButton;

    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginWindow.setPadding(new Insets(10,15,0,15));
        forgotPasswordText.setUnderline(true);
        setForgotPasswordEvents();
        setLoginEvents();
    }

    private void setForgotPasswordEvents() {
        forgotPasswordText.setOnMouseEntered(e->{
            forgotPasswordText.setStyle("-fx-font-weight: bold;");});
        forgotPasswordText.setOnMouseExited(e->{
            forgotPasswordText.setStyle("-fx-font-weight: normal");});
        forgotPasswordText.setOnMouseClicked(e-> setForgotPasswordDialog());
    }

    private void setForgotPasswordDialog() {
        TextInputDialog passwordDialog = new TextInputDialog("E-mail or username");
        passwordDialog.setTitle("Change password");
        passwordDialog.setHeaderText("Enter registered E-mail or username\n" +
                "and a new password will be sent to you.");
        Optional<String> result = passwordDialog.showAndWait();
    }

    private void setLoginEvents(){
        loginButton.setOnMouseClicked(e -> {
            User.getUserOnline(usernameField.getText(), passwordField.getText())
                    .onDelivery(user -> {
                        System.out.println("Logged in as " + user.getName());
                        Session.getSession().setUser(user);
                    })
                    .onCancel(() -> System.out.println("Wrong password or username."));
        });

        guestButton.setOnMouseClicked(e -> {
            //Session.getSession().setPlayer(Player.newMockPlayerFromUser(User.getGuestUser()));
            Session.getSession().setUser(User.getGuestUser());
            System.out.println("Logged in as guest user.");
        });
    }

    @Override
    public void closeView() {

    }
}
