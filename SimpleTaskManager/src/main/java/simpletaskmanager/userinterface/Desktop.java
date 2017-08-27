/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.userinterface;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import simpletaskmanager.domain.Priority;
import simpletaskmanager.domain.Task;
import simpletaskmanager.domain.TaskGroup;
import simpletaskmanager.domain.WorkFlow;
import simpletaskmanager.logic.TaskManager;

import java.util.List;

/**
 * Luokka kayttoliittyman koodille.
 *
 * @author taina
 * <p>
 * tama on viela ihan liikaa yhdessa potkossa ja copy-pasteakin on.
 * siistitaan tyon edetessa
 */
public class Desktop extends Application {

    private Stage primaryStage;
    private TabPane tabPaneMain;


    @Override
    public void start(Stage primaryStageIn) {

        this.primaryStage = primaryStageIn;

        // sovelluksen otsikko
        primaryStage.setTitle("SimpleTaskManager");

        // sovelluksen tiedot tiedostosta
        TaskManager tm = new TaskManager();
        tm.readFile();
        List<TaskGroup> taskGroupList = tm.getTaskGroups();

        // valilehdet
        this.tabPaneMain = new TabPane();

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
        tabHome.setGraphic(this.tabInfo("Home", "home"));

        BorderPane bpHome = new BorderPane();
        Label lbHome = new Label("Simple Task Manager");
        // css asetukset
        lbHome.setId("groupHeader");

        Button btnHome = new Button("Create New Group");
        btnHome.setOnAction((event) -> {
            primaryStage.setScene(this.CreateNewGroup(tm, primaryStage.getScene()));
            primaryStage.show();
        });

        bpHome.setTop(lbHome);
        bpHome.setCenter(btnHome);
        tabHome.setContent(bpHome);
        tabPaneMain.getTabs().add(tabHome);


        /*

         Stats TAB
         - tulossa
         - aina tokana
         - ei voi sulkea

        */
        Tab tabStats = new Tab();
        tabStats.setClosable(false);
        // valilehden otsikkotiedot
        tabStats.setGraphic(this.tabInfo("Stats", "stats"));

        BorderPane bpStats = new BorderPane();
        Label lbStats = new Label("This is Stats screen");
        Button btnStats = new Button("First Button");

        bpStats.setTop(lbStats);
        bpStats.setCenter(btnStats);
        tabStats.setContent(bpStats);
        tabPaneMain.getTabs().add(tabStats);


        /*

         All groups for TABs

         */

        if (!taskGroupList.isEmpty()) {
            for (TaskGroup aTaskGroup : taskGroupList) {
                this.addNewTaskGroupTab(aTaskGroup);
            }
        }


        // bout puhelin koossa
        Scene scene = new Scene(tabPaneMain, 350, 550);
        try {
            scene.getStylesheets().add
                    (Desktop.class.getResource("../../css/custom.css").toExternalForm());
        } catch (Exception e) {
            // ei saatu ulkoasuasetuksia
        }

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private Tab createTabForTaskList(WorkFlow status, TaskGroup taskGroup, String picName) {

        if (status == null || taskGroup == null || picName == null ) {
            return new Tab();
        }

        Tab tabTasks = new Tab();
        // otsikko
        tabTasks.setGraphic(this.tabInfo(status.toString(), picName));
        // sisalto
        BorderPane bpGroup = new BorderPane();
        Label lbGroupHeader = new Label(status.toString().toUpperCase());
        // css asetukset
        lbGroupHeader.setId("groupHeader");
        bpGroup.setTop(lbGroupHeader);
        // avoinna olevien taskien tiedot
        bpGroup.setCenter(this.createGPForTask(taskGroup.getTaskListByStatus(status)));

        tabTasks.setContent(bpGroup);
        return tabTasks;

    }

    private GridPane createGPForTask(List<Task> taskListByStatus) {

        GridPane gpTaskList = new GridPane();
        gpTaskList.setHgap(3);
        gpTaskList.setVgap(3);
        Label lbTaskH = new Label("Header \t\t\t");
        lbTaskH.setId("rowHeader");
        gpTaskList.add(lbTaskH, 0, 0);
        Label lbPrioH = new Label("Priority \t");
        lbPrioH.setId("rowHeader");
        gpTaskList.add(lbPrioH, 1, 0);

        for (int i = 0; i < taskListByStatus.size(); i++) {
            Label lbTask = new Label(taskListByStatus.get(i).getHeader());
            gpTaskList.add(lbTask, 0, i + 1);
            Label lbPrio = new Label(taskListByStatus.get(i).getPrio().toString());
            gpTaskList.add(lbPrio, 1, i + 1);
        }
        return gpTaskList;
    }

    private Scene CreateNewTask(TaskGroup taskGroup, Scene parent) {

        GridPane gpNewTask = new GridPane();

        Label lbHeader = new Label("Header");
        TextField tfHeader = new TextField();
        Label lbPrio = new Label("Priority");

        final ComboBox cbPrio = new ComboBox();
        cbPrio.getItems().addAll(
                "Blocker",
                "Critical",
                "Major",
                "Neutral",
                "Minor",
                "Trivial"
        );

        Button btnNewTask = new Button("Create Task");
        btnNewTask.setOnAction((event) -> {
            Task newTask = new Task(tfHeader.getText(), Priority.valueOf(cbPrio.getValue().toString()));
            taskGroup.addNewTask(newTask);
            // TODO: taski pitaisi saada viela nakyville
            primaryStage.setScene(parent);
            primaryStage.show();
        });

        gpNewTask.add(lbHeader, 0, 0);
        gpNewTask.add(tfHeader, 1, 0);
        gpNewTask.add(lbPrio, 0, 1);
        gpNewTask.add(cbPrio, 1, 1);
        gpNewTask.add(btnNewTask, 0, 2);


        Scene scene = new Scene(gpNewTask, 350, 550);
        return scene;
    }

    private HBox tabInfo(String header, String imgName) {
        if (header == null) {
            header = "";
        }
        if (imgName == null ) {
            imgName = "default";
        }
        HBox content = new HBox();
        Label label = new Label("");

        if (header.length() > 4) {
            label.setText(header.substring(0, 4));
        } else {
            label.setText(header);
        }
        //label.setAlignment(Pos.CENTER_RIGHT);
        try {
            ImageView icon = buildImage(Desktop.class.getResource("../../icons/" + imgName + ".png").toExternalForm());
            content.getChildren().addAll(icon, label);
        } catch (Exception e) {
            content.getChildren().addAll(label);
        }
        content.setAlignment(Pos.CENTER);
        content.setSpacing(3);
        return content;
    }

    private Scene CreateNewGroup(TaskManager tm, Scene parent) {

        GridPane gpNewGroup = new GridPane();

        Label lbHeader = new Label("Header");
        TextField tfHeader = new TextField();

        Label lbTemplate = new Label("Is Group a template");
        // TODO: mika tahan ois hyva?

        Label lbIcon = new Label("Icon Name");
        final ComboBox cbIcon = new ComboBox();
        cbIcon.getItems().addAll(
                "Blocker",
                "Critical",
                "Major",
                "Neutral",
                "Minor",
                "Trivial"
        );

        Label lbDesc = new Label("Description");
        TextArea taDesc = new TextArea();

        Button btnNewGroup = new Button("Create Group");
        btnNewGroup.setOnAction((event) -> {
            TaskGroup newTaskGroup = new TaskGroup(tfHeader.getText(), false, cbIcon.getValue().toString(), taDesc.getText());
            tm.addNewTaskGroup(newTaskGroup);
            this.addNewTaskGroupTab(newTaskGroup);
            primaryStage.setScene(parent);
            primaryStage.show();
        });

        gpNewGroup.add(lbHeader, 0, 0);
        gpNewGroup.add(tfHeader, 1, 0);
        gpNewGroup.add(lbTemplate, 0, 1);
       // gpNewGroup.add(lbTemplate, 1, 1);
        gpNewGroup.add(lbIcon, 0, 2);
        gpNewGroup.add(cbIcon, 1, 2);
        gpNewGroup.add(lbDesc, 0, 3);
        gpNewGroup.add(taDesc, 1, 3);
        gpNewGroup.add(btnNewGroup, 0, 4);

        Scene scene = new Scene(gpNewGroup, 350, 550);
        return scene;


    }

    private void addNewTaskGroupTab(TaskGroup newTaskGroup) {

        if (newTaskGroup == null){
            return ;
        }

        // uusi valilehti
        Tab tabTaskGroup = new Tab();
        // valilehden otsikko
        tabTaskGroup.setGraphic(this.tabInfo(newTaskGroup.getHeader(), newTaskGroup.getIconName()));

        // valilehden asettelutyyppi ja sisalto
        BorderPane bpGroup = new BorderPane();
        VBox contentGroup = new VBox();
        Label lbGroup = new Label(newTaskGroup.getHeader());
        // css asetukset
        lbGroup.setId("groupHeader");

        Button btnGroupNewTask = new Button("Create New Task");
        btnGroupNewTask.setOnAction((event) -> {
            primaryStage.setScene(this.CreateNewTask(newTaskGroup, primaryStage.getScene()));
            primaryStage.show();
        });

        contentGroup.getChildren().addAll(lbGroup, btnGroupNewTask);
        contentGroup.setAlignment(Pos.CENTER);
        contentGroup.setSpacing(3);
        bpGroup.setTop(contentGroup);

        // ryhmaan kuuluvat alilehdet - ei kovin natti nain, mutta toimii
        TabPane tabPaneForGroup = new TabPane();
        // KUVAUS
        Tab tabTasksDesc = new Tab();
        // otsikko
        tabTasksDesc.setGraphic(this.tabInfo("Desc", "home"));
        // sisalto
        BorderPane bpGroupDesc = new BorderPane();
        Label lbGroupDescHeader = new Label("Description:");
        // css asetukset
        lbGroupDescHeader.setId("groupHeader");
        bpGroupDesc.setTop(lbGroupDescHeader);
        Label lbGroupDesc = new Label(newTaskGroup.getDesc());
        bpGroupDesc.setCenter(lbGroupDesc);
        tabTasksDesc.setContent(bpGroupDesc);
        tabPaneForGroup.getTabs().add(tabTasksDesc);
        // STATS
        Tab tabTasksStat = new Tab();
        // otsikko
        tabTasksStat.setGraphic(this.tabInfo("Stat", "stats"));
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
        tabPaneForGroup.getTabs().add(this.createTabForTaskList(WorkFlow.Todo, newTaskGroup, "ideas"));
        // INPROGRESS
        tabPaneForGroup.getTabs().add(this.createTabForTaskList(WorkFlow.InProgress, newTaskGroup, "hands"));
        // DONE
        tabPaneForGroup.getTabs().add(this.createTabForTaskList(WorkFlow.Done, newTaskGroup, "rocket"));

        // kaikki ryhman asetteluun ja valilehdelle
        bpGroup.setCenter(tabPaneForGroup);
        tabTaskGroup.setContent(bpGroup);

        // lisataan valilehtien listaukseen mukaan
        tabPaneMain.getTabs().add(tabTaskGroup);

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




