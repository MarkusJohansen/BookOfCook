package BookOfCook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
    
public class BookOfCookApp extends Application{
    
    @Override   
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Book of Cook");                                                  //setter tittel på vinduet
        Parent root = FXMLLoader.load(getClass().getResource("BookOfCook.fxml"));               //Laster inn fxml-filen
        Scene scene = new Scene(root);                                                          //Lager en scene med root som innhold                  
        scene.getStylesheets().add(getClass().getResource("BookOfCook.css").toExternalForm());  //Laster inn css-filen
        primaryStage.setScene(scene);                                                           //Setter inn scene i vinduet
        primaryStage.show();                                                                    //Viser vinduet
    }

    public static void main(String[] args) {
        launch(args);
    }
}
