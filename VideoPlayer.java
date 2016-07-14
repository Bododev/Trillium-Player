/**
 * Created by bogda on 7/12/2016.
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;

public class VideoPlayer extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // TODO Auto-generated method stub
        Group root = new Group();//comment for resolution fit v2
        final Stage stage = new Stage();

        //get screen resolution
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double w = screenSize.getWidth();
        double h = screenSize.getHeight();*/

        //File file = new File("trailer.mp4");
        //String MEDIA_URL = file.toURI().toString();
        //check the path
        //Media media = new Media("trailer.mp4");
        Media media = new Media(getClass().getResource("trailer.mp4").toExternalForm());

        final MediaPlayer player = new MediaPlayer(media);
        MediaView view = new MediaView(player);

        //Proper resolution fit v2
        /*final DoubleProperty width = view.fitWidthProperty();
        final DoubleProperty height = view.fitHeightProperty();
        width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
        view.setPreserveRatio(true);
        StackPane root = new StackPane();*/


        final VBox vbox = new VBox();
        final Slider slider = new Slider();
        vbox.getChildren().add(slider);

        root.getChildren().add(view);
        root.getChildren().add(vbox);

        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        stage.setTitle("Trillium Player");

        //works
        /*view.fitWidthProperty().bind(scene.widthProperty());
        view.fitHeightProperty().bind(scene.heightProperty());*/


        stage.setScene(scene);

        stage.show();

        player.play();

        //fit resolution tutorial version
        player.setOnReady(new Runnable()
        {
            @Override
            public void run()
            {
                int w = player.getMedia().getWidth();
                int h = player.getMedia().getHeight();

                stage.setMinWidth(w);
                stage.setMinHeight(h);

                vbox.setMinSize(w, 100);
                vbox.setTranslateY(h-100);

                slider.setMin(0.0);
                slider.setValue(0.0);
                slider.setMax(player.getTotalDuration().toSeconds());
            }
        });
        //end fit
        player.currentTimeProperty().addListener(new ChangeListener<Duration>()
        {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current)
            {
                slider.setValue(current.toSeconds());
            }
        });

        //create controller

    }

}
