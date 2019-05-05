package client;

import client.models.account.Account;
import client.models.account.AccountInfo;
import client.models.card.Card;
import client.models.card.DeckInfo;
import client.models.map.Position;
import client.models.menus.*;
import client.models.message.CardPosition;
import client.models.message.DataName;
import client.models.message.GameUpdateMessage;
import client.models.message.Message;
import server.Server;

import java.util.ArrayList;

public class Client {
    private Server server;
    private String clientName;
    private Account account;
    private ArrayList<Message> sendingMessages = new ArrayList<>();
    private ArrayList<Message> receivingMessages = new ArrayList<>();
    private DeckInfo[] customDecks;
    private AccountInfo[] leaderBoard;
    private Menu currentMenu;
    private Card selected;
    private Position[] positions;
    private boolean validation = true;
    private String errorMessage;

    public Client(String clientName, Server server) {
        this.clientName = clientName;
        this.server = server;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void updateLeaderBoard(String serverName) {
        this.addToSendingMessages(Message.makeGetDataMessage(clientName, serverName, DataName.LEADERBOARD, 0));
        this.sendMessages();
    }

    public AccountInfo[] getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(AccountInfo[] leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean getValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public String getClientName() {
        return clientName;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
        currentMenu.showHelp();
    }

    public void addToSendingMessages(Message message) {
        sendingMessages.add(message);
    }

    public void addToReceivingMessages(String messageJson) {
        receivingMessages.add(Message.convertJsonToMessage(messageJson));
    }

    public void receiveMessages() {
        validation = true;
        for (Message message : receivingMessages) {
            switch (message.getMessageType()) {
                case SEND_EXCEPTION:
                    validation = false;
                    errorMessage = message.getExceptionMessage().getExceptionString();
                    break;
                case ACCOUNT_COPY:
                    account = new Account(message.getAccountCopyMessage().getAccount());
                    break;
                case GAME_COPY:
                    GameCommands.getInstance().setCurrentGame(message.getGameCopyMessage().getCompressedGame());
                    currentMenu = GameCommands.getInstance();
                    break;
                case ORIGINAL_CARDS_COPY:
                    Shop.getInstance().setOriginalCards(message.getOriginalCardsCopyMessage().getOriginalCards());
                    break;
                case LEADERBOARD_COPY:
                    leaderBoard = message.getLeaderBoardCopyMessage().getLeaderBoard();
                    break;
                case STORIES_COPY:
                    StoryMenu.getInstance().setStories(message.getStoriesCopyMessage().getStories());
                    break;
                case OPPONENT_INFO:
                    MultiPlayerMenu.getInstance().setSecondAccount(message.getOpponentInfoMessage().getOpponentInfo());
                    break;
                case CARD_POSITION://TODO:CHANGE
                    CardPosition cardPosition = message.getCardPositionMessage().getCardPosition();
                    GameCommands gameCommands = GameCommands.getInstance();
                    switch (cardPosition) {
                        case MAP:
                            gameCommands.getCurrentGame().getCurrentTurnPlayer().removeCardFromHand(message.getCardPositionMessage().getCompressedCard().getCardId());
                            break;
                        case HAND:
                            gameCommands.getCurrentGame().moveCardToHand(message.getCardPositionMessage().getCompressedCard());
                            break;
                        case NEXT:
                            gameCommands.getCurrentGame().moveCardToNext(message.getCardPositionMessage().getCompressedCard());
                            break;
                        case GRAVE_YARD:
                            gameCommands.getCurrentGame().moveCardToGraveYard(message.getCardPositionMessage().getCompressedCard());
                            break;
                        case COLLECTED:
                            gameCommands.getCurrentGame().moveCardToCollecteds(message.getCardPositionMessage().getCompressedCard());
                            break;
                    }
                case TROOP_UPDATE:
                    gameCommands = GameCommands.getInstance();
                    gameCommands.getCurrentGame().troopUpdate(message.getTroopUpdateMessage().getCompressedTroop());
                    break;
                case GAME_UPDATE:
                     gameCommands = GameCommands.getInstance();
                    GameUpdateMessage gameUpdateMessage= message.getGameUpdateMessage();
                     gameCommands.getCurrentGame().gameUpdate(
                             gameUpdateMessage.getTurnNumber(),
                             gameUpdateMessage.getPlayer1CurrentMP(),
                             gameUpdateMessage.getPlayer1NumberOfCollectedFlags(),
                             gameUpdateMessage.getPlayer2CurrentMP(),
                             gameUpdateMessage.getPlayer2NumberOfCollectedFlags());

                    break;
                case Game_FINISH:

                    break;
            }
        }
        receivingMessages.clear();
    }

    public void sendMessages() {
        for (Message message : sendingMessages) {
            server.addToReceivingMessages(message.toJson());
        }
        sendingMessages.clear();
        server.receiveMessages();
        receiveMessages();
    }

    public void setCustomDecks(DeckInfo[] customDecks) {
        this.customDecks = customDecks;
    }

    public void setSelected(Card selected) {
        this.selected = selected;
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }
}