/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.userinterface;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
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
import simpletaskmanager.statistics.Stats;

import java.util.*;

/**
 * Luokka kayttoliittyman koodille.
 *
 * @author taina
 * <p>
 * tata olisi pitanyt jakaa erillisiin luokkiin, mutta aina nyt vain loppuu kesken.
 * haluaisin mielummin saada tilastokuvakkeet tehtya, kuin jakaa taman luokkiin.
 * ja rumahan tama on kuin mika, mutta kaikki toimii.
 */
public class Desktop extends Application {

    private Stage primaryStage;
    private TabPane tabPaneMain;
    private TaskManager tm;
    private LineChart<String, Number> viivakaavio;
    private Stats stats;
    private NumberAxis yAkseliViivakaavio;
    private BarChart<String, Number> pylvaskaavio;


    /**
     * paaohjelma, joka alustaa kayttoliittyman.
     *
     * @param primaryStageIn - paaikkuna
     */
    @Override
    public void start(Stage primaryStageIn) {

        this.primaryStage = primaryStageIn;

        // sovelluksen otsikko
        this.primaryStage.setTitle("SimpleTaskManager");

        // sovelluksen tiedot tiedostosta
        this.tm = new TaskManager();
        tm.readFile();
        List<TaskGroup> taskGroupList = tm.getTaskGroups();

        this.stats = new Stats(tm);

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

        VBox VbStats = new VBox();

        // VIIVAKAAVIO
        CategoryAxis xAkseli = new CategoryAxis();
        //xAkseli.setLabel("Status");

        this.yAkseliViivakaavio = new NumberAxis();
        //this.yAkseliViivakaavio = new NumberAxis(0, stats.getMaxTaskCountInGroup(), 1);
        //yAkseliViivakaavio.setLabel("Count");

        this.viivakaavio = new LineChart<>(xAkseli, yAkseliViivakaavio);
        viivakaavio.setTitle("Number of Tasks by Group by Status");
        viivakaavio.setAnimated(false);
        viivakaavio.setLegendVisible(false);


        // PYLVASKAAVIO

        CategoryAxis xAkseliPylvaskaavio = new CategoryAxis();
        NumberAxis yAkseliPylvaskaavio = new NumberAxis();
        //xAkseliPylvaskaavio.setLabel("Group");
        //yAkseliPylvaskaavio.setLabel("Task Count");

        this.pylvaskaavio = new BarChart<>(xAkseliPylvaskaavio, yAkseliPylvaskaavio);
        pylvaskaavio.setTitle("Number of Tasks by Group");
        pylvaskaavio.setLegendVisible(false);

        // graafien sisalto
        this.updateStatGraps();

        VbStats.getChildren().addAll(pylvaskaavio, viivakaavio);

        tabStats.setContent(VbStats);
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


    /**
     * apumetodi, joka paivittaa kokonaisuuden statistiikka kuvat.
     */
    private void updateStatGraps() {
        this.viivakaavio.getData().clear();
        // annetaan koneen asettaa naa
        //this.yAkseliViivakaavio.setUpperBound(stats.getMaxTaskCountInGroup());

        // luetaan data
        Map<TaskGroup, Map<String, Integer>> values = this.stats.countTasksByGroups();

        // kaydaan lapi ja lisataan ne kaavioon
        values.keySet().stream().forEach(tg -> {
            XYChart.Series data = new XYChart.Series();
            data.setName(tg.getHeader());

            values.get(tg).entrySet().stream().forEach(row -> {
                data.getData().add(new XYChart.Data(row.getKey(), row.getValue()));
            });

            this.viivakaavio.getData().add(data);
        });

        pylvaskaavio.getData().clear();

        // kaydaan lapi ja lisataan ne kaavioon
        values.keySet().stream().forEach(tg -> {
            XYChart.Series chartData = new XYChart.Series();

            chartData.getData().add(new XYChart.Data(tg.getHeader(),
                    tg.getTaskList().size()));

            pylvaskaavio.getData().add(chartData);
        });


    }

    /**
     * apumetodi, jolla luodaan ryhmalle workflow statusten mukaiset valilehdet.
     *
     * @param status    - kasittelyssa oleva workflon kohta
     * @param taskGroup - ryhma, jota kasitellaan.
     * @param picName   - kuvake, joka tulee valilehden otsikkotietoihin,
     * @return - palautetaan Tab, jossa on ryhman tehtavien tiedot annetussa statuksessa.
     */
    private Tab createTabForTaskList(WorkFlow status, TaskGroup taskGroup, String picName) {

        if (status == null || taskGroup == null || picName == null) {
            return new Tab();
        }

        Tab tabTasks = new Tab();
        tabTasks.setClosable(false);
        // otsikko
        tabTasks.setGraphic(this.tabInfo(status.toString(), picName));
        // sisalto
        BorderPane bpGroup = new BorderPane();
        Label lbGroupHeader = new Label(status.toString().toUpperCase());
        // css asetukset
        lbGroupHeader.setId("groupHeader");
        bpGroup.setTop(lbGroupHeader);
        // avoinna olevien taskien tiedot
        bpGroup.setCenter(this.createGPForTask(taskGroup, taskGroup.getTaskListOrderedByPrio(status)));

        tabTasks.setContent(bpGroup);
        return tabTasks;

    }

    /**
     * apumetodi, jolla luodaan GripPane ryhmalle sen tehtavien tiedoista.
     *
     * @param taskGroup        - kasiteltava ryhma
     * @param taskListByStatus - listaan laitettavien tehtavien tiedot.
     * @return palautetaan GripPane, johon on aseteltu tehtavien tiedot.
     */
    private GridPane createGPForTask(TaskGroup taskGroup, List<Task> taskListByStatus) {

        GridPane gpTaskList = new GridPane();
        gpTaskList.setHgap(3);
        gpTaskList.setVgap(3);
        Label lbTaskNroH = new Label("Nro \t");
        lbTaskNroH.setId("rowHeader");
        gpTaskList.add(lbTaskNroH, 0, 0);
        Label lbTaskH = new Label("Header \t\t\t");
        lbTaskH.setId("rowHeader");
        gpTaskList.add(lbTaskH, 1, 0);
        Label lbPrioH = new Label("Priority \t\t");
        lbPrioH.setId("rowHeader");
        gpTaskList.add(lbPrioH, 2, 0);
        Label lbButtonH = new Label("Workflow \t");
        lbButtonH.setId("rowHeader");
        gpTaskList.add(lbButtonH, 3, 0);

        for (int i = 0; i < taskListByStatus.size(); i++) {
            Task currentTask = taskListByStatus.get(i);
            Label lbTaskNro = new Label("" + (i + 1));
            gpTaskList.add(lbTaskNro, 0, i + 1);
            Label lbTask = new Label(currentTask.getHeader());
            gpTaskList.add(lbTask, 1, i + 1);
            Label lbPrio = new Label(currentTask.getPrio().toString());
            gpTaskList.add(lbPrio, 2, i + 1);
            Button btTask = new Button("Workflow");

            Set<Node> oneRow = new HashSet<>();
            oneRow.add(lbTaskNro);
            oneRow.add(lbTask);
            oneRow.add(lbPrio);
            oneRow.add(btTask);
            btTask.setOnAction((event) -> {
                primaryStage.setScene(CreateNewTaskSubMethodForStatus(taskGroup, currentTask, primaryStage.getScene()));
            });
            gpTaskList.add(btTask, 3, i + 1);

        }
        return gpTaskList;
    }

    /**
     * paivitetaan ryhman valilehti poistamalla ja luomalla se uudestaan.
     * paivitetaan myos kokonaisuuden graafit
     * yhman valilehdista ensimmainen asetataan oletuksena nakyville
     *
     * @param taskGroup paivitettava ryhma
     */

    private void refreshTaskGroupsTaskTabs(TaskGroup taskGroup) {
        refreshTaskGroupsTaskTabs(taskGroup, 0);
    }

    /**
     * paivitetaan ryhman valilehti poistamalla ja luomalla se uudestaan.
     * paivitetaan myos kokonaisuuden graafit
     *
     * @param taskGroup - ryhma, jonka valilehti paivitetaan
     * @param focus     - mika ryhman valilehdista asetataan oletuksena nakyville
     */
    private void refreshTaskGroupsTaskTabs(TaskGroup taskGroup, int focus) {

        // ei nyt ehka fiksuin tapa, mutta toimii
        // poistetaan nykyinen
        Iterator<Tab> iteratorForTabs = tabPaneMain.getTabs().iterator();

        while (iteratorForTabs.hasNext()) {
            Tab nextInLine = iteratorForTabs.next();

            if (nextInLine.getId() == taskGroup.getId()) {
                iteratorForTabs.remove();
            }
        }

        // luodaan uusi
        addNewTaskGroupTab(taskGroup, focus);
        // uusi menee aina vikaksi, focus sinne.
        tabPaneMain.getSelectionModel().select(tabPaneMain.getTabs().size() - 1);

        // paivitettaan graafit
        this.updateStatGraps();

    }


    /**
     * pikku ikkuna uuden tehtavan luomiseen.
     *
     * @param taskGroup ryhma, johon tehtava luodaan
     * @param parent    nakyma, josta tanne tultiin
     * @return palautetaan nakyma (Scene), joka naytetaan
     */
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
        cbPrio.setValue("Neutral");

        Button btnNewTask = new Button("Create Task");
        btnNewTask.setOnAction((event) -> {
            Task newTask = new Task(tfHeader.getText(), Priority.valueOf(cbPrio.getValue().toString()));
            taskGroup.addNewTask(newTask);
            this.refreshTaskGroupsTaskTabs(taskGroup);
            primaryStage.setScene(parent);
        });

        gpNewTask.add(lbHeader, 0, 0);
        gpNewTask.add(tfHeader, 1, 0);
        gpNewTask.add(lbPrio, 0, 1);
        gpNewTask.add(cbPrio, 1, 1);
        gpNewTask.add(btnNewTask, 0, 2);


        Scene scene = new Scene(gpNewTask, 350, 550);
        return scene;
    }

    /**
     * apuikkuna, jossa kysytaan mihin tilaan kasiteltava tehtava siirretaan.
     *
     * @param taskGroup ryhma, johon tehtava kuuluu
     * @param task      kasiteltava tehtava
     * @param parent    ikkuna josta tanne tultiin
     * @return palautetaan naytettava Scene
     */
    private Scene CreateNewTaskSubMethodForStatus(TaskGroup taskGroup, Task task, Scene parent) {

        GridPane gpNewTask = new GridPane();

        Label lbHeader = new Label(task.getHeader());

        final ComboBox cbStatus = new ComboBox();
        cbStatus.getItems().addAll(
                "Todo",
                "InProgress",
                "Done"
        );
        cbStatus.setValue(task.getStatus().toString());

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction((event) -> {
            primaryStage.setScene(parent);
        });

        Button btnNewTask = new Button("Set Status");
        btnNewTask.setOnAction((event) -> {
            task.setStatus(WorkFlow.valueOf(cbStatus.getValue().toString()));
            int focus = 0;
            if (cbStatus.getValue().toString() == "Todo") {
                focus = 2;
            } else if (cbStatus.getValue().toString() == "InProgress") {
                focus = 3;
            } else if (cbStatus.getValue().toString() == "Done") {
                focus = 4;
            }

            refreshTaskGroupsTaskTabs(taskGroup, focus);
            primaryStage.setScene(parent);
        });

        gpNewTask.add(lbHeader, 0, 0);
        gpNewTask.add(cbStatus, 0, 1);
        gpNewTask.add(btnCancel, 0, 2);
        gpNewTask.add(btnNewTask, 0, 3);

        Scene scene = new Scene(gpNewTask, 350, 550);
        return scene;
    }


    /**
     * apumetodi, jolla asetaan valilehden otsikko ja kuvake
     *
     * @param header  - otsikko
     * @param imgName - kuvan nimi
     * @return - palautetaan HBox, joka asetetaan valilehden otsikoksi
     */
    private HBox tabInfo(String header, String imgName) {
        if (header == null) {
            header = "";
        }
        if (imgName == null) {
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

    /**
     * apuikkuna uuden ryhman luomikseksi.
     * kysyy ryhman tiedot.
     *
     * @param tm     - TaskManager, jolla hallinnoidaan kokonaisuuden tietoja
     * @param parent - mista ikkunasta tahan tultiin
     * @return - palautetaan Scene naytettavaksi
     */
    private Scene CreateNewGroup(TaskManager tm, Scene parent) {

        GridPane gpNewGroup = new GridPane();

        Label lbHeader = new Label("Header");
        TextField tfHeader = new TextField();

        Label lbIcon = new Label("Icon Name");
        final ComboBox cbIcon = new ComboBox();
        cbIcon.getItems().addAll(
                "default",
                "diamond",
                "hands",
                "home",
                "ideas",
                "love",
                "money",
                "rocket",
                "stats"
        );
        cbIcon.setValue("default");

        Label lbDesc = new Label("Description");
        TextArea taDesc = new TextArea();

        Button btnNewGroup = new Button("Create Group");
        btnNewGroup.setOnAction((event) -> {
            TaskGroup newTaskGroup = new TaskGroup(tfHeader.getText(), false, cbIcon.getValue().toString(), taDesc.getText());
            tm.addNewTaskGroup(newTaskGroup);
            this.addNewTaskGroupTab(newTaskGroup);
            // paivitettaan graafit
            this.updateStatGraps();
            primaryStage.setScene(parent);
        });

        lbHeader.setMinSize(150, 10);

        gpNewGroup.add(lbHeader, 0, 0);
        gpNewGroup.add(tfHeader, 1, 0);
        gpNewGroup.add(lbIcon, 0, 2);
        gpNewGroup.add(cbIcon, 1, 2);
        gpNewGroup.add(lbDesc, 0, 3);
        gpNewGroup.add(taDesc, 1, 3);
        gpNewGroup.add(btnNewGroup, 0, 4);


        Scene scene = new Scene(gpNewGroup, 350, 550);
        return scene;


    }

    /**
     * Lisataan ryhmalle oma valilehti kokoelmaan.
     * (oletus focus ensimmaiseen valilehteen)
     *
     * @param newTaskGroup = ryhma jolle valilehti tehdaan
     */
    private void addNewTaskGroupTab(TaskGroup newTaskGroup) {
        addNewTaskGroupTab(newTaskGroup, 0);
    }

    /**
     * Lisataan ryhmalle oma valilehti kokoelmaan.
     *
     * @param newTaskGroup = lisattava ryhma
     * @param focus        = mihin ryhman omaan valilehteen laitetaan focus
     */
    private void addNewTaskGroupTab(TaskGroup newTaskGroup, int focus) {

        if (newTaskGroup == null) {
            return;
        }

        // uusi valilehti
        Tab tabTaskGroup = new Tab();
        tabTaskGroup.setClosable(false);
        tabTaskGroup.setId(newTaskGroup.getId());
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
        });

        contentGroup.getChildren().addAll(lbGroup, btnGroupNewTask);
        contentGroup.setAlignment(Pos.CENTER);
        contentGroup.setSpacing(3);
        bpGroup.setTop(contentGroup);

        // ryhmaan kuuluvat alilehdet - ei kovin natti nain, mutta toimii
        TabPane tabPaneForGroup = new TabPane();
        // KUVAUS
        Tab tabTasksDesc = new Tab();
        tabTasksDesc.setClosable(false);
        // otsikko
        tabTasksDesc.setGraphic(this.tabInfo("Desc", "home"));
        // sisalto
        BorderPane bpGroupDesc = new BorderPane();
        Label lbGroupDescHeader = new Label("Description:");
        // css asetukset
        lbGroupDescHeader.setId("groupHeader");
        // ryhman kuvaus
        bpGroupDesc.setTop(lbGroupDescHeader);
        Label lbGroupDesc = new Label(newTaskGroup.getDesc());
        bpGroupDesc.setCenter(lbGroupDesc);

        // ryhman kopiointi ja poistaminen
        VBox vbButtons = new VBox();
        Label lbNewHeader = new Label("New Header");
        TextField tfNewHeader = new TextField();
        Button btCloneGroup = new Button("Copy Group");
        btCloneGroup.setOnAction((event) -> {
            if (!tfNewHeader.getText().isEmpty()) {
                TaskGroup clonedGroup = this.tm.cloneTaskGroup(tfNewHeader.getText(), newTaskGroup);
                addNewTaskGroupTab(clonedGroup);
                // paivitettaan graafit
                this.updateStatGraps();
            }
        });
        Button btRemoveGroup = new Button("Remove Group");
        btRemoveGroup.setOnAction((event) -> {
            tabPaneMain.getTabs().remove(tabTaskGroup);
            // paivitettaan graafit
            this.updateStatGraps();
        });
        vbButtons.getChildren().addAll(lbNewHeader, tfNewHeader, btCloneGroup, new Label("---"), btRemoveGroup);
        vbButtons.setAlignment(Pos.CENTER);
        vbButtons.setSpacing(3);

        bpGroupDesc.setBottom(vbButtons);

        tabTasksDesc.setContent(bpGroupDesc);
        tabPaneForGroup.getTabs().add(tabTasksDesc);

        // STATS
        Tab tabTasksStat = new Tab();
        tabTasksStat.setClosable(false);
        // otsikko
        tabTasksStat.setGraphic(this.tabInfo("Stat", "stats"));
        // sisalto
        BorderPane bpGroupStat = new BorderPane();
        Label lbGroupStatHeader = new Label("Stats:");
        // css asetukset
        bpGroupStat.setId("groupHeader");
        bpGroupStat.setTop(lbGroupStatHeader);

        BarChart<String, Number> groupChart;
        CategoryAxis xAkseliGroupChart = new CategoryAxis();
        NumberAxis yAkseliGroupChart = new NumberAxis();
        groupChart = new BarChart<>(xAkseliGroupChart, yAkseliGroupChart);
        groupChart.setTitle("Number of Tasks by Status");

        Map<String, Integer> countGroupsTasks = stats.countTasksByStatus(newTaskGroup);
        countGroupsTasks.entrySet().stream().forEach(row -> {
            XYChart.Series dataGroupChart = new XYChart.Series();
            dataGroupChart.getData().add(new XYChart.Data(row.getKey().toString(), row.getValue()));
            groupChart.getData().add(dataGroupChart);
        });

        bpGroupStat.setCenter(groupChart);
        tabTasksStat.setContent(bpGroupStat);
        tabPaneForGroup.getTabs().add(tabTasksStat);
        // ryhma-avoinna olevat
        tabPaneForGroup.getTabs().add(this.createTabForTaskList(WorkFlow.Todo, newTaskGroup, "ideas"));
        // INPROGRESS
        tabPaneForGroup.getTabs().add(this.createTabForTaskList(WorkFlow.InProgress, newTaskGroup, "hands"));
        // DONE
        tabPaneForGroup.getTabs().add(this.createTabForTaskList(WorkFlow.Done, newTaskGroup, "rocket"));

        tabPaneForGroup.getSelectionModel().select(focus);

        // kaikki ryhman asetteluun ja valilehdelle
        bpGroup.setCenter(tabPaneForGroup);
        tabTaskGroup.setContent(bpGroup);


        // lisataan valilehtien listaukseen mukaan
        tabPaneMain.getTabs().add(tabTaskGroup);

    }

    /**
     * apumetodi, jolla luodaan kuvan nayttamiseen tarkoitettu ImageView
     *
     * @param imgPatch - kuvakkeen sijainti
     * @return - palautetaan ImageView, johon on asetettu haettu kuva.
     * (jos kuvan haku ei onnistu palautetaan tyhja)
     */
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




