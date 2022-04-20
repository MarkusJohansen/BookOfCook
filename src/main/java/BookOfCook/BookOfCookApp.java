package BookOfCook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
    
public class BookOfCookApp extends Application{
    
    @Override   
    public void start(Stage primaryStage) throws Exception {
        //Styler vinduet littr
        primaryStage.setTitle("Book of Cook");
        //primaryStage.getIcons().add(new Image("../../resources/BookOfCook/icon.png")); //!Skal setet funker ikke :()

        //Laster inn fxml-filen
        Parent root = FXMLLoader.load(getClass().getResource("BookOfCook.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("BookOfCook.css").toExternalForm());

        //Setter inn scene i vinduet og viser vinduet
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
