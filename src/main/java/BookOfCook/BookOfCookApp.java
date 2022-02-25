package BookOfCook;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookOfCookApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Book of Cook");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("BookOfCook.fxml"))));
        primaryStage.show();        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
