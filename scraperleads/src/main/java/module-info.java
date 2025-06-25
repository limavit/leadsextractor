module br.com.ephesos.scraperleads {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.jsoup;
    requires org.json;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.sql;
    requires java.sql.rowset;


    opens br.com.ephesos.scraperleads to javafx.fxml;
    exports br.com.ephesos.scraperleads;
    exports br.com.ephesos.scraperleads.controller;
    opens br.com.ephesos.scraperleads.controller to javafx.fxml;
}