module pingpong {

    requires javafx.controls;
    requires javafx.fxml;

    opens pingpong to javafx.fxml;
    exports pingpong;

}
