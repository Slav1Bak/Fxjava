package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import model.Participant;

public class DetailCourseController {

    @FXML
    private Label lbl_cours_name;

    @FXML
    private Label lbl_course_category;

    @FXML
    private Label lbl_course_date;

    @FXML
    private Label lbl_course_trainer;

    @FXML
    private Label lbl_course_participans;

    @FXML
    private TextField tf_particip_name;

    @FXML
    private TextField tf_particip_lastname;

    @FXML
    private TableView<Participant> tbl_particip;

    @FXML
    private TableColumn<Participant, String> col_particip_name;

    @FXML
    private TableColumn<Participant, String> col_particip_lastname;

    @FXML
    void deleteParticipant(ActionEvent event) {
        Participant p_deleted = tbl_particip.getSelectionModel().getSelectedItem();
        CourseController.c_selected.deleteParticipant(p_deleted);
        participants.remove(p_deleted);
        lbl_course_participans.setText(String.valueOf(participants.size()));
        insertParticipantsIntoTableView();
    }
    //--------------------
    ObservableList<Participant> participants = FXCollections.observableArrayList();

    //----------------------------------------------
    @FXML
    void saveParticipant(ActionEvent event) {
        String particip_name = tf_particip_name.getText();
        String particip_lastname = tf_particip_lastname.getText();
        Participant p = new Participant(particip_name, particip_lastname);
        CourseController.c_selected.addParticipant(p);

        participants.add(p);
            insertParticipantsIntoTableView();
        lbl_course_participans.setText(String.valueOf(CourseController.c_selected.getNoParticipants()));
    }

    public void initialize() {
        //ustawienie danych kursu---------------------------------
            insertParticipantsIntoTableView();
        lbl_cours_name.setText(CourseController.c_selected.getCourse_name());
        lbl_course_category.setText(CourseController.c_selected.getCourse_category());
        lbl_course_date.setText(CourseController.c_selected.getCourse_date().toString());
        lbl_course_trainer.setText(CourseController.c_selected.getCourse_trainer());
        lbl_course_participans.setText(String.valueOf(CourseController.c_selected.getNoParticipants()));

    }

    private void insertParticipantsIntoTableView() {
        //konfiguracja zmiennych do kolumn=======================================================
        col_particip_name.setCellValueFactory(new PropertyValueFactory<Participant, String>("particip_name"));
        col_particip_lastname.setCellValueFactory(new PropertyValueFactory<Participant, String>("particip_lastname"));
        //----------------------wprowadzenie danych di tabeli z listy
        tbl_particip.setItems(participants);

    }
}
