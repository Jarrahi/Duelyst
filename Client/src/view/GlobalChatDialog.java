package view;

import controller.Client;
import controller.MainMenuController;
import controller.SoundEffectPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.gui.*;
import models.message.ChatMessage;

import static models.gui.UIConstants.SCALE;


public class GlobalChatDialog {
    private static GlobalChatDialog ourInstance = new GlobalChatDialog();

    private VBox chatMessages = new VBox();
    private DialogBox dialogBox = new DialogBox();
    private NormalField normalField = new NormalField("Message");

    private GlobalChatDialog() {
        ScrollPane scrollPane = new ScrollPane(chatMessages);
        OrangeButton sendButton = new OrangeButton("send",
                event -> {
                    MainMenuController.getInstance().sendChatMessage(normalField.getText());
                    normalField.setText("");
                },
                SoundEffectPlayer.SoundName.select);
        dialogBox.getChildren().addAll(scrollPane, new HBox(normalField, sendButton));
        sendButton.setAlignment(Pos.CENTER_LEFT);
        scrollPane.setMinHeight(700);
        dialogBox.getChildren().stream().filter(node -> node instanceof ScrollPane).forEach(node -> ((ScrollPane) node).setMinHeight(300 * SCALE));
    }

    public void show() {
        normalField.setText("");
        DialogContainer dialogContainer = new DialogContainer(Client.getInstance().getCurrentShow().root, dialogBox);

        dialogContainer.show();
        dialogBox.makeClosable(dialogContainer);
    }

    public static GlobalChatDialog getInstance() {
        return ourInstance;
    }

    public void addMessage(ChatMessage chatMessage) {
        chatMessages.getChildren().add(new DialogText(chatMessage.getSenderUsername() + ": " + chatMessage.getText()));
    }
}
