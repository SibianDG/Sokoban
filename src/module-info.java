module sokoban {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	
	opens start to javafx.graphics,javafx.fxml;
	opens gui to javafx.graphics,javafx.fxml;
}