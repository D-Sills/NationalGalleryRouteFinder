package Utilities;

import javafx.scene.control.Alert;

/**
 * @author Darren Sills
 * Class containing a number of generic alerts
 */
public class Alerts {
		/**
		 * Reusable warning popup
		 * @param desc content of the alert
		 */
		public static void genericWarning(String desc) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText(desc);
				alert.showAndWait();
		}

		/**
		 * Reusable information popup
		 * @param desc content of the alert
		 */
		public static void genericInfo(String desc) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(desc);
				alert.showAndWait();
		}
}
