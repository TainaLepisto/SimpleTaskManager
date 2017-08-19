/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.userinterface;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import simpletaskmanager.domain.TaskGroup;
import simpletaskmanager.logic.TaskManager;

import java.util.List;

/**
 * @author taina
 */
public class Desktop extends Application {


    public static void main(String[] args) {

        launch(Desktop.class);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("SimpleTaskManager");

        TabPane tabPane = new TabPane();

        /*

         Home TAB

         */
        Tab tabHome = new Tab();
        tabHome.setClosable(false);

        HBox content = new HBox();
        // create a HBox to act like a graphic content for the Tab
        Label label = new Label("Home");
        label.setAlignment(Pos.CENTER_RIGHT);
        ImageView icon = buildImage("file:../../icons/home.png");
        content.getChildren().addAll(icon, label);
        content.setAlignment(Pos.CENTER);
        tabHome.setGraphic(content); // TODO: tää ei vaan toimi!!

        BorderPane bpHome = new BorderPane();
        Label lbHome = new Label("This is home screen");
        Button btnHome = new Button("First Button");
        btnHome.setOnAction((event) -> {
            // tähän esim. uuden ryhmän luonti
        });

        bpHome.setTop(lbHome);
        bpHome.setCenter(btnHome);
        // TODO: tutki miten se CSS ulkoasu näille laitetaan

        tabHome.setContent(bpHome);

        tabPane.getTabs().add(tabHome);


        /*

         Stats TAB

        */
        Tab tabStats = new Tab();
        tabStats.setClosable(false);

        HBox contentStats = new HBox();
        // create a HBox to act like a graphic content for the Tab
        Label labelStats = new Label("Stats");
        label.setAlignment(Pos.CENTER_RIGHT);
        ImageView iconStats = buildImage("file:../../icons/stats.png");
        contentStats.getChildren().addAll(iconStats, labelStats);
        contentStats.setAlignment(Pos.CENTER);
        tabStats.setGraphic(contentStats); // TODO: tää ei vaan toimi!!

        BorderPane bpStats = new BorderPane();
        Label lbStats = new Label("This is Stats screen");
        Button btnStats = new Button("First Button");


        bpStats.setTop(lbStats);
        bpStats.setCenter(btnStats);

        tabStats.setContent(bpStats);
        tabPane.getTabs().add(tabStats);


        /*

         All groups for TABs

         */

        TaskManager tm = new TaskManager();
        tm.readFile();
        List<TaskGroup> taskGroupList = tm.getTaskGroups();

        if (!taskGroupList.isEmpty()) {
            for (TaskGroup aTaskGroupList : taskGroupList) {
                Tab taskGroup = new Tab();

                BorderPane bpGroup = new BorderPane();
                Label lbGroup = new Label(aTaskGroupList.getHeader());
                Button btnGroup = new Button("Button");

                bpStats.setTop(lbGroup);
                bpStats.setCenter(btnGroup);

                taskGroup.setContent(bpGroup);

                tabPane.getTabs().add(taskGroup);
            }
        }


        /*
        tabPane.setTabMinHeight(50);
        tabPane.setTabMaxHeight(50);
        tabPane.setTabMinWidth(100);
        tabPane.setTabMaxWidth(100);
        */

        // bout Iphone7 koossa
        Scene scene = new Scene(tabPane, 250, 444);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    // Helper method to create image from image patch
    private static ImageView buildImage(String imgPatch) {
        try {
            Image i = new Image(imgPatch);
            ImageView imageView = new ImageView(i);
            //You can set width and height
            imageView.setFitHeight(15);
            imageView.setFitWidth(15);
            return imageView;
        } catch (Exception e) {
            // jotain meni pieleen kuvankäsittelyssä. laitetaan vain tyhjä.
            System.out.println("ikonia ei löytynyt");
            return new ImageView();
        }
    }


}




