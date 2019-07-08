package server.clientPortal.models.message;

import server.clientPortal.models.JsonConverter;
import server.dataCenter.models.account.Account;
import server.dataCenter.models.account.Collection;
import server.dataCenter.models.card.Card;
import server.dataCenter.models.card.ExportedDeck;
import server.gameCenter.models.game.*;

import java.util.List;

public class Message {
    private MessageType messageType;
    //serverName || clientName
    private String sender;
    private String receiver;
    private int messageId;

    //SENDER:SERVER
    private GameCopyMessage gameCopyMessage;
    private CardsCopyMessage cardsCopyMessage;
    private AccountCopyMessage accountCopyMessage;
    private LeaderBoardCopyMessage leaderBoardCopyMessage;
    private StoriesCopyMessage storiesCopyMessage;
    private CardPositionMessage cardPositionMessage;
    private TroopUpdateMessage troopUpdateMessage;
    private GameUpdateMessage gameUpdateMessage;
    private ExceptionMessage exceptionMessage;
    private OpponentInfoMessage opponentInfoMessage;
    private GameFinishMessage gameFinishMessage;
    private GameAnimations gameAnimations;
    //SENDER:CLIENT
    private String cardName;
    private GetDataMessage getDataMessage;
    private OtherFields otherFields;
    private ExportedDeck exportedDeck;
    private AccountFields accountFields;
    //SENDER:DUAL
    private Card card;
    private ChatMessage chatMessage;
    private NewGameFields newGameFields;
    private ChangeCardNumber changeCardNumber;
    private ChangeAccountType changeAccountType;


    private Message(String sender, String receiver, int messageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageId = messageId;
    }

    public static Message convertJsonToMessage(String messageJson) {
        return JsonConverter.fromJson(messageJson, Message.class);
    }

    public static Message makeDoneMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.DONE;
        return message;
    }

    public static Message makeGameCopyMessage(String sender, String receiver, Game game, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.gameCopyMessage = new GameCopyMessage(game);
        message.messageType = MessageType.GAME_COPY;
        return message;
    }

    public static Message makeOriginalCardsCopyMessage(String sender, String receiver, Collection originalCards, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardsCopyMessage = new CardsCopyMessage(originalCards);
        message.messageType = MessageType.ORIGINAL_CARDS_COPY;
        return message;
    }

    public static Message makeAccountCopyMessage(String sender, String receiver, Account account, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.accountCopyMessage = new AccountCopyMessage(account);
        message.messageType = MessageType.ACCOUNT_COPY;
        return message;
    }

    public static Message makeLeaderBoardCopyMessage(String sender, String receiver, Account[] leaderBoard, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.leaderBoardCopyMessage = new LeaderBoardCopyMessage(leaderBoard);
        message.messageType = MessageType.LEADERBOARD_COPY;
        return message;
    }

    public static Message makeStoriesCopyMessage(String sender, String receiver, Story[] stories, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.storiesCopyMessage = new StoriesCopyMessage(stories);
        message.messageType = MessageType.STORIES_COPY;
        return message;
    }

    public static Message makeChangeCardPositionMessage(String sender, String receiver, Card card, CardPosition cardPosition, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardPositionMessage = new CardPositionMessage(card, cardPosition);
        message.messageType = MessageType.CARD_POSITION;
        return message;
    }

    public static Message makeAttackMessage(String sender, String receiver, Troop attacker, Troop defender, boolean counterAttack, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.gameAnimations = new GameAnimations();
        message.gameAnimations.addAttacks(attacker.getCard().getCardId(), defender.getCard().getCardId());
        if (counterAttack)
            message.gameAnimations.addCounterAttacks(defender.getCard().getCardId(), attacker.getCard().getCardId());
        message.messageType = MessageType.ANIMATION;
        return message;
    }


    public static Message makeTroopUpdateMessage(String sender, String receiver, Troop troop, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.troopUpdateMessage = new TroopUpdateMessage(troop);
        message.messageType = MessageType.TROOP_UPDATE;
        return message;
    }

    public static Message makeGameUpdateMessage(String sender, String receiver, int turnNumber, int player1CurrentMP,
                                                int player1NumberOfCollectedFlags, int player2CurrentMP,
                                                int player2NumberOfCollectedFlags, List<CellEffect> cellEffects, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.gameUpdateMessage = new GameUpdateMessage(turnNumber, player1CurrentMP, player1NumberOfCollectedFlags,
                player2CurrentMP, player2NumberOfCollectedFlags, cellEffects);
        message.messageType = MessageType.GAME_UPDATE;
        return message;
    }

    public static Message makeExceptionMessage(String sender, String receiver, String exceptionString, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.exceptionMessage = new ExceptionMessage(exceptionString);
        message.messageType = MessageType.SEND_EXCEPTION;
        return message;
    }

    public static Message makeChatMessage(String sender, String receiver, String messageSender, String messageReceiver,
                                          String textMessage, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.chatMessage = new ChatMessage(messageSender, messageReceiver, textMessage);
        message.messageType = MessageType.CHAT;
        return message;
    }

    public static Message makeAccountInfoMessage(String sender, String receiver, Account opponent, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.opponentInfoMessage = new OpponentInfoMessage(opponent);
        message.messageType = MessageType.OPPONENT_INFO;
        return message;
    }

    public static Message makeGameFinishMessage(String sender, String receiver, boolean youWon, int reward, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.gameFinishMessage = new GameFinishMessage(youWon, reward);
        message.messageType = MessageType.Game_FINISH;
        return message;
    }

    public static Message makeInvitationMessage(String sender, String receiver, String inviterUsername, GameType gameType, int numberOfFlags) {
        Message message = new Message(sender, receiver, 0);
        message.messageType = MessageType.INVITATION;
        message.newGameFields = new NewGameFields(gameType, numberOfFlags, 0, null, inviterUsername);
        return message;
    }

    public static Message makeAcceptRequestMessage(String sender, String receiver) {
        Message message = new Message(sender, receiver, 0);
        message.messageType = MessageType.ACCEPT_REQUEST;
        return message;
    }

    public static Message makeDeclineRequestMessage(String sender, String receiver) {
        Message message = new Message(sender, receiver, 0);
        message.messageType = MessageType.DECLINE_REQUEST;
        return message;
    }

    public static Message makeChangeCardNumberMessage(String sender, String receiver, Card card, int newValue) {
        Message message = new Message(sender, receiver, 0);
        message.changeCardNumber = new ChangeCardNumber(card.getName(), newValue);
        message.messageType = MessageType.CHANGE_CARD_NUMBER;
        return message;
    }

    public static Message makeAddOriginalCardMessage(String serverName, String key, Card card) {
        Message message = new Message(serverName, key, 0);
        message.card = card;
        message.messageType = MessageType.ADD_TO_ORIGINALS;
        return message;
    }

    public static Message makeAddCustomCardMessage(String serverName, String key, Card card) {
        Message message = new Message(serverName, key, 0);
        message.card = card;
        message.messageType = MessageType.ADD_TO_CUSTOM_CARDS;
        return message;
    }

    public static Message makeRemoveCustomCardMessage(String serverName, String key, String cardName) {
        Message message = new Message(serverName, key, 0);
        message.cardName = cardName;
        message.messageType = MessageType.REMOVE_FROM_CUSTOM_CARDS;
        return message;
    }

    public static Message makeCustomCardsCopyMessage(String sender, String receiver, Collection customCards) {
        Message message = new Message(sender, receiver, 0);
        message.cardsCopyMessage = new CardsCopyMessage(customCards);
        message.messageType = MessageType.CUSTOM_CARDS_COPY;
        return message;
    }

    public String toJson() {
        return JsonConverter.toJson(this);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getMessageId() {
        return messageId;
    }

    public GetDataMessage getGetDataMessage() {
        return getDataMessage;
    }

    public OtherFields getOtherFields() {
        return otherFields;
    }

    public AccountFields getAccountFields() {
        return accountFields;
    }

    public NewGameFields getNewGameFields() {
        return newGameFields;
    }

    public Card getCard() {
        return card;
    }

    public ExportedDeck getExportedDeck() {
        return exportedDeck;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public ChangeCardNumber getChangeCardNumber() {
        return changeCardNumber;
    }

    public ChangeAccountType getChangeAccountType() {
        return changeAccountType;
    }

    public String getCardName() {
        return cardName;
    }
}
