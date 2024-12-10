module apcsa.finalproject.fairshareproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires charm.glisten;
    requires org.json;
    requires java.sql;
    requires org.jsoup;
    requires jdk.compiler;
    requires jdk.incubator.vector;


    opens apcsa.finalproject.fairshareproject to javafx.fxml;
    exports apcsa.finalproject.fairshareproject;
}