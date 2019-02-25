package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Course;

import java.io.IOException;
import java.time.LocalDate;

public class CourseController {
    // obiekt listy kursow----------------------------------------------------------------- + id
    public static int id;
    ObservableList<Course> courses = FXCollections.observableArrayList();

    @FXML
    private TableView<Course> tbl_course;

    @FXML
    private TableColumn<Course, String> col_course_name;

    @FXML
    private TableColumn<Course, String> col_course_category;

    @FXML
    private TextField tf_course_name;

    @FXML
    private TextField tf_course_category;

    @FXML
    private TextField tf_course_trainer;

    @FXML
    private DatePicker dp_course_date;

    @FXML
    void addCourse(ActionEvent event) {
        //walidacja danych
        if (!tf_course_name.getText().equals("") && !tf_course_category.getText().equals("") && dp_course_date.getValue() != null) {

            //pobranie z pola tekst field----------------------------------------------------
            String course_name = tf_course_name.getText();
            String course_category = tf_course_category.getText();
            LocalDate course_date = dp_course_date.getValue();
            String course_trainer = tf_course_trainer.getText();
            //utworzenie obiektu klasy course
            Course c = new Course(++id, course_name, course_category, course_date, course_trainer);
            //wprowadzenie obiektu klasyCourse do listy kursow---------------------------------------
            courses.add(c);
            //odswiezenie tableview----------------------------
            insertCourserIntoTableView();
            //czyszczenie pol tylko po dodaniu kursu
            tf_course_name.clear();
            tf_course_category.clear();
            dp_course_date.setValue(null);
            tf_course_trainer.clear();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Błąd");
            a.setHeaderText("Błąd dowawania nowego kursu");
            a.setContentText("Uzupełnij wszystkie pola w celu dodania nowego kursu");
            a.show();
        }
    }

    @FXML
    void deleteCourse(ActionEvent event) {
        //odczyt zaznaczonego rekordu z tabeli i przypisanie jego id do zmiennej
        Course c_deleted = tbl_course.getSelectionModel().getSelectedItem();
        courses.remove(c_deleted);
        //odswiezenie--------------------------------
        insertCourserIntoTableView();


    }

    //przekazanie do innego widoku-----------------------------------
    static Course c_selected;

    @FXML
    void getCourse(ActionEvent event) throws IOException {
        //odczyt zaznaczonego rekordu z tabeli i przypisanie jego id do zmiennej
        c_selected = tbl_course.getSelectionModel().getSelectedItem();
        //wywolaanie nowego widoku
        Stage courseStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/detailCourseView.fxml"));
        courseStage.setTitle("Wybrany kurs");
        courseStage.setScene(new Scene(root));
        courseStage.show();


    }

    //------------------------------------metoda do wprowadzania zawartosci listy kursow do tabelki-----------
    private void insertCourserIntoTableView() {
        //konfiguracja zmiennych do kolumn=======================================================
        col_course_name.setCellValueFactory(new PropertyValueFactory<Course, String>("course_name"));
        col_course_category.setCellValueFactory(new PropertyValueFactory<Course, String>("course_category"));
        //----------------------wprowadzenie danych di tabeli z listy
        tbl_course.setItems(courses);
    }

    //metoda ktora pojawia sie jako pierwsza po zaladowaniu widoku------------------------
    public void initialize() {
        insertCourserIntoTableView();
    }

}
