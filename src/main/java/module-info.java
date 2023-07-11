module com.example.labb3wassimtajeddin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labb3wassimtajeddin to javafx.fxml;
    exports com.example.labb3wassimtajeddin;
}