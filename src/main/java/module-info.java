module com.tucn.pt2022_30424_trasculescu_tudor_assignment_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.tucn.pt2022_30424_trasculescu_tudor_assignment_2 to javafx.fxml;
    exports com.tucn.pt2022_30424_trasculescu_tudor_assignment_2;
    exports com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Controller;
    opens com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Controller to javafx.fxml;
    exports com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.BussinessLogic;
    opens com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.BussinessLogic to javafx.fxml;
    exports com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model;
    opens com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model to javafx.fxml;
}