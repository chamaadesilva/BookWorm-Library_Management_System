package lk.ijse.lms.util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;


/**
 * The ValidationUtil class provides utility methods for validating TextField inputs in a JavaFX application.
 * It uses regular expressions (Pattern) to validate the input.
 */
public class ValidationUtil {

    /**
     * Validates a map of TextField and Pattern pairs. If the text in the TextField does not match the Pattern,
     * it adds an error to the TextField and disables the Button.
     *
     * @param map A LinkedHashMap of TextField and Pattern pairs to validate.
     * @param btn The Button to disable if validation fails.
     * @return The first TextField that failed validation, or true if all validations passed.
     */
    public static Object validate(LinkedHashMap<TextField, Pattern> map, Button btn) {
        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()) {
                addError(key, btn);
                return key;
            }
            removeError(key, btn);
        }
        return true;
    }


    /**
     * Removes the error style from the TextField and enables the Button.
     *
     * @param txtField The TextField to remove the error style from.
     * @param btn      The Button to enable.
     */
    private static void removeError(TextField txtField, Button btn) {
        txtField.getParent().setStyle("-fx-border-color: green");
        btn.setDisable(false);
    }


    /**
     * Adds an error style to the TextField and disables the Button.
     *
     * @param txtField The TextField to add the error style to.
     * @param btn      The Button to disable.
     */
    private static void addError(TextField txtField, Button btn) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
        }
        btn.setDisable(true);
    }


    /**
     * Checks the TextField inputs in the provided map. If the ENTER key is pressed and validation fails,
     * it requests focus on the TextField that failed validation.
     *
     * @param checkBox   A LinkedHashMap of TextField and Pattern pairs to validate.
     * @param btnAddItem The Button to disable if validation fails.
     * @param keyEvent   The KeyEvent to check if the ENTER key was pressed.
     */
    public static void textFieldChecker(LinkedHashMap<TextField, Pattern> checkBox, Button btnAddItem, KeyEvent keyEvent) {
        ValidationUtil.validate(checkBox, btnAddItem);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(checkBox, btnAddItem);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }
        }
    }
}
