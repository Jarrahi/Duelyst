package view;

import controller.Client;
import controller.GraphicalUserInterface;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.gui.UIConstants;
import models.message.Message;

public abstract class Show {
    final AnchorPane root = new AnchorPane();
    private Scene scene = new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);

    public void show() {
        GraphicalUserInterface.getInstance().setScene(scene);
        Client.getInstance().setShow(this);
    }

    public void showError(String message, EventHandler<? super MouseEvent> event) {
        ErrorView errorView = new ErrorView(root);
        errorView.show(message, "OK", event);
    }

    public void showError(String message) {
        ErrorView errorView = new ErrorView(root);
        errorView.show(message, "OK", event -> {});
    }

    public void showError(String message, String buttonText, EventHandler<? super MouseEvent> event) {
        ErrorView errorView = new ErrorView(root);
        errorView.show(message, buttonText, event);
    }

    public void showError(String message, String buttonText) {
        ErrorView errorView = new ErrorView(root);
        errorView.show(message, buttonText, event -> {});
    }
}