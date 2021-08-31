package project.controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import project.model.Airport;
import project.model.Covid;

import java.util.ArrayList;



/**
 * A class that contains methods that display dialog boxes
 * depending on the situation
 */
public class DialogBoxes {

    /**
     * A dialog box that displays when the loaded Airports class has countries that don't have any information
     * available regarding COVID cases and informs the user that they will be set to 0 risk.
     */
    public static void missingCovidInfoBox() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        int numMissing = Airport.getNumMissingCovid();
        alert.setTitle("MISSING COUNTRIES");

        alert.setHeaderText(String.format("%d airports are in countries that do not have information available on COVID cases." +
                " Airports in these countries will show a risk of 0 when selected:", numMissing));
        String missingCountries = Covid.missingCountries.get(0);
        for (int i = 1; i < Covid.missingCountries.size(); i++) {
            missingCountries += ", " + Covid.missingCountries.get(i);
        }
        alert.setContentText(missingCountries);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow(); //make aler to stage so icon can be changed
        stage.getIcons().add(new Image("primaryStageIcon.png"));

        alert.showAndWait();
    }

    /**
     * A dialog box that displays when a file is loaded and it's format is good
     * else it displays that the data is laid out correct or it is not the right type of data
     * @param check A boolean that error checks the file path
     * @param displayGoodLoad A boolean that checks whether the load is good
     * @param dataType A String that represents the type of data e.g. Airports, Flights etc.
     */
    public static void fileFormatInfo(boolean check, boolean displayGoodLoad, String dataType) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("FILE STATUS");
        alert.setHeaderText(null);
        String body;
        if (check && displayGoodLoad) {
            body = "File loaded successfully";
            alert.setAlertType(Alert.AlertType.INFORMATION);
        } else if (check) {
            return;
        } else {
            body = String.format("Data laid out incorrectly, or is not %s data", dataType);
        }

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow(); //make aler to stage so icon can be changed
        stage.getIcons().add(new Image("primaryStageIcon.png"));

        alert.setContentText(body);
        alert.showAndWait();
    }

    /**
     * A dialog box that displays when the multipleBoxes aren't selected
     * and it is left as a null/blank box
     * @param multipleBoxes A boolean to check whether the multiBoxes are selected
     */
    public static void blankTextField(boolean multipleBoxes) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("BLANK TEXT FIELD");
        alert.setHeaderText(null);
        String body;
        if (multipleBoxes) {
            body = "Please provide a value for each of the text boxes.";
        } else {
            body = "Please provide some search criteria.";
        }
        alert.setContentText(body);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow(); //make aler to stage so icon can be changed
        stage.getIcons().add(new Image("primaryStageIcon.png"));

        alert.showAndWait();
    }

    /**
     * A dialog box that displays when the searched/filtered criteria does not match
     * anything in the record
     * @param dataType A String that represents the type of data that is being searched e.g. IATA or Airport Name etc.
     */
    public static void noneReturned(String dataType) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        String title = String.format("NO MATCHING %s", dataType.toUpperCase());
        String body = String.format("Sorry, but there are no %s that match your search criteria.", dataType.toLowerCase());
        alert.setTitle(title);
        alert.setContentText(body);
        alert.setHeaderText(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow(); //make aler to stage so icon can be changed
        stage.getIcons().add(new Image("primaryStageIcon.png"));

        alert.showAndWait();
    }

    /**
     * A dialog box that displays when there is an error when adding new types of data
     * or modifying data doesn't fit the data type
     * @param errors An ArrayList of Strings that represents the errors found when modifying or adding new data.
     */
    public static void newDataError(ArrayList<String> errors) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String errorString = "";
        for (int i = 0; i < errors.size(); i++) {
            errorString += errors.get(i) + '\n';
        }
        alert.setContentText(errorString);
        alert.setHeaderText(null);
        alert.setTitle("ERRORS WITH DATA");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow(); //make aler to stage so icon can be changed
        stage.getIcons().add(new Image("primaryStageIcon.png"));

        alert.showAndWait();
    }

    // Unused because we don't currently have the means to add routes to airports. Will be added in phase 3.
    /**
    public static void noRoutes() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        String bodyText = "The airport you have selected does not have any flights either in or out of it.\nHave you considered adding some?";
        alert.setContentText(bodyText);
        alert.setHeaderText(null);
        alert.setTitle("AIRPORT MISSING ROUTES");
        alert.showAndWait();
    }
     */

    /**
     * A dialog box that displays on GUI bootup, to inform users that our calculations are approximate only.
     */
    public static void welcomeBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        String headerText = "Welcome to PlaneSonar25! Thank you for using our app.";
        String bodyText = "Our app handles some sensitive information surrounding COVID infection rates, and the risks that" +
                " a country's airport poses to your health. As such, we see it as our duty to notify you that these risks are calculated " +
                "using the WHO's COVID case data for each country, and that these risks are purely approximations, and should not " +
                "be used to fully inform your chances of infection in a country. Our risk matrix for countries is as follows:\n" +
                "Extremely Low: Less than 0.1% of the population have cases\nLow: Less than 0.5% of the population have cases\n" +
                "Medium: Less than 1% of the population have cases\nHigh: Less than 5% of the population have cases\n" +
                "Extreme: More than 5% of the population have cases\nRemember to social distance when you can, and stay safe.";
        alert.setTitle("WELCOME");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow(); //make alert to stage so icon can be changed
        stage.getIcons().add(new Image("primaryStageIcon.png"));

        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);
        alert.showAndWait();
    }

    /**
     * A dialog box that notifies the user when an error occurs while loading COVID data
     */
    public static void noCovidFileLoaded() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        String title = String.format("ERROR LOADING COVID");
        String body = String.format("Sorry, an error occurred while loading the COVID data. All airports added before a COVID data file is" +
                " uploaded manually will have a reported risk of zero. We apologise for the inconvenience.");
        alert.setTitle(title);
        alert.setContentText(body);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
}
