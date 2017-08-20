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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import simpletaskmanager.domain.Task;
import simpletaskmanager.domain.TaskGroup;
import simpletaskmanager.domain.WorkFlow;
import simpletaskmanager.logic.TaskManager;

import java.nio.file.Paths;
import java.util.List;

import static javafx.scene.paint.Color.HONEYDEW;

/**
 * @author taina
 *
 * tama on viela ihan liikaa yhdessa potkossa ja copy-pasteakin on.
 * siistitaan tyon edetessa
 *
 */
public class Desktop extends Application {

    @Override
    public void start(Stage primaryStage) {

        // sovelluksen otsikko
        primaryStage.setTitle("SimpleTaskManager");

        TabPane tabPane = new TabPane();

        /*

         Home TAB
         - aina ensimmainen
         - ei voi sulkea
         - uusien luontinappi
         - ohjelman tiedot

         */
        Tab tabHome = new Tab();
        tabHome.setClosable(false);
        // valilehden otsikkotiedot
        tabHome.setGraphic(this.tabInfo("Home","home"));

        BorderPane bpHome = new BorderPane();
        Label lbHome = new Label("Simple Task Manager");
        // css asetukset
        lbHome.setId("groupHeader");

        Button btnHome = new Button("Create New Group");
        btnHome.setOnAction((event) -> {
            this.CreateNewGroup();
        });

        bpHome.setTop(lbHome);
        bpHome.setCenter(btnHome);
        tabHome.setContent(bpHome);
        tabPane.getTabs().add(tabHome);


        /*

         Stats TAB
         - tulossa
         - aina tokana
         - ei voi sulkea

        */
        Tab tabStats = new Tab();
        tabStats.setClosable(false);
        // valilehden otsikkotiedot
        tabStats.setGraphic(this.tabInfo("Stats","stats"));

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
                // uusi valilehti
                Tab tabTaskGroup = new Tab();
                // valilehden otsikko
                tabTaskGroup.setGraphic(this.tabInfo(aTaskGroupList.getHeader(),aTaskGroupList.getIconName()));

                // valilehden asettelutyyppi ja sisalto
                BorderPane bpGroup = new BorderPane();
                Label lbGroup = new Label(aTaskGroupList.getHeader());
                // css asetukset
                lbGroup.setId("groupHeader");
                bpGroup.setTop(lbGroup);

                // ryhmaan kuuluvat alilehdet - ei kovin natti nain, mutta toimii
                TabPane tabPaneForGroup = new TabPane();
                // KUVAUS
                Tab tabTasksDesc = new Tab();
                // otsikko
                tabTasksDesc.setGraphic(this.tabInfo("Desc","home"));
                // sisalto
                BorderPane bpGroupDesc = new BorderPane();
                Label lbGroupDescHeader = new Label("Description:");
                // css asetukset
                lbGroupDescHeader.setId("groupHeader");
                bpGroupDesc.setTop(lbGroupDescHeader);
                Label lbGroupDesc = new Label(aTaskGroupList.getDesc());
                bpGroupDesc.setCenter(lbGroupDesc);
                tabTasksDesc.setContent(bpGroupDesc);
                tabPaneForGroup.getTabs().add(tabTasksDesc);
                // STATS
                Tab tabTasksStat = new Tab();
                // otsikko
                tabTasksStat.setGraphic(this.tabInfo("Stat","stats"));
                // sisalto
                BorderPane bpGroupStat = new BorderPane();
                Label lbGroupStatHeader = new Label("Stats:");
                // css asetukset
                bpGroupStat.setId("groupHeader");
                bpGroupStat.setTop(lbGroupStatHeader);
                Label lbGroupStat = new Label("KESKEN STATS");
                bpGroupStat.setCenter(lbGroupStat);
                tabTasksStat.setContent(bpGroupStat);
                tabPaneForGroup.getTabs().add(tabTasksStat);
                // ryhma-avoinna olevat
                Tab tabTasksTodo = new Tab();
                // otsikko
                tabTasksTodo.setGraphic(this.tabInfo("Todo","ideas"));
                // sisalto
                BorderPane bpGroupTodo = new BorderPane();
                Label lbGroupTodoHeader = new Label("TODO:");
                // css asetukset
                lbGroupTodoHeader.setId("groupHeader");
                bpGroupTodo.setTop(lbGroupTodoHeader);
                // avoinna olevien taskien tiedot
                Label lbGroupTodo = new Label("KESKEN TODO");

                List<Task> taskListTodo =  aTaskGroupList.getTaskListByStatus(WorkFlow.Todo);


                bpGroupTodo.setCenter(lbGroupTodo);

                tabTasksTodo.setContent(bpGroupTodo);
                tabPaneForGroup.getTabs().add(tabTasksTodo);
                // INPROGRESS
                Tab tabTasksInProgress = new Tab();
                // otsikko
                tabTasksInProgress.setGraphic(this.tabInfo("In Progress","hands"));
                // sisalto
                BorderPane bpGroupInProgress = new BorderPane();
                Label lbGroupInProgressHeader = new Label("In Progress:");
                // css asetukset
                lbGroupInProgressHeader.setId("groupHeader");
                bpGroupInProgress.setTop(lbGroupInProgressHeader);
                Label lbGroupInProgress = new Label("KESKEN InProgress ");
                bpGroupInProgress.setCenter(lbGroupInProgress);
                tabTasksInProgress.setContent(bpGroupInProgress);
                tabPaneForGroup.getTabs().add(tabTasksInProgress);
                // DONE
                Tab tabTasksDone = new Tab();
                // otsikko
                tabTasksDone.setGraphic(this.tabInfo("Done","rocket"));
                // sisalto
                BorderPane bpGroupDone = new BorderPane();
                Label lbGroupDoneHeader = new Label("Done:");
                // css asetukset
                lbGroupDoneHeader.setId("groupHeader");
                bpGroupDone.setTop(lbGroupDoneHeader);
                Label lbGroupDone = new Label("KESKEN Done");
                bpGroupDone.setCenter(lbGroupDone);
                tabTasksDone.setContent(bpGroupDone);
                tabPaneForGroup.getTabs().add(tabTasksDone);

                // kaikki ryhman asetteluun ja valilehdelle
                bpGroup.setCenter(tabPaneForGroup);
                tabTaskGroup.setContent(bpGroup);

                // lisataan valilehtien listaukseen mukaan
                tabPane.getTabs().add(tabTaskGroup);
            }
        }


        // bout puhelin koossa
        Scene scene = new Scene(tabPane, 250, 444);
        try {
            scene.getStylesheets().add
                    (Desktop.class.getResource("../../css/custom.css").toExternalForm());
        }
        catch (Exception e){
            // ei saatu ulkoasuasetuksia
        }

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private HBox tabInfo(String header, String imgName) {
        HBox content = new HBox();
        Label label = new Label(header.substring(0,4));
        //label.setAlignment(Pos.CENTER_RIGHT);
        try {
            ImageView icon = buildImage(Desktop.class.getResource("../../icons/" + imgName + ".png").toExternalForm());
            content.getChildren().addAll(icon, label);
        }
        catch (Exception e) {
            content.getChildren().addAll(label);
        }
        content.setAlignment(Pos.CENTER);
        content.setSpacing(3);
        return content ;
    }

    private void CreateNewGroup() {


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
            // jotain meni pieleen kuvankasittelyssa. laitetaan vain tyhja.
            System.out.println("ikonia ei loytynyt");
            System.out.println(imgPatch);
            System.out.println(e);
            return new ImageView();
        }
    }


}




