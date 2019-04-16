package view.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RequestType {
    CREATE_ACCOUNT("^create account (\\w+)$"),
    LOGIN("^login (\\w+)$"),
    SHOW_LEADER_BOARD("^show leaderboard$"),
    SAVE("^save$"),
    LOGOUT("^logout$"),
    HELP("^help$"),
    START_GAME("^start game (\\w+) (\\w+) (\\w+)$"),
    SELECT_USER("^select user (\\w+)$"),
    START_MULTIPLAYER_GMAE("^start multiplayer game (\\w+)"),
    ENTER_MENU("^Enter (\\w+)$"),
    EXIT("^exit$"),
    SHOW("^show$"),
    CREATE_DECK("^create deck (\\w+)$"),
    DELETE_DECK("^delete deck (\\w+)$"),
    SEARCH("^search (\\w+)$"),
    ADD_TO_DECK("^add (\\w+) to deck (\\w+)$"),
    REMOVE_FROM_DECK("^remove (\\w+) from deck (\\w+)$"),
    VALIDATE_DECK("^validate deck (\\w+)$"),
    SELECT_MAIN_DECK("^select deck (\\w+)$"),
    SHOW_ALL_DECKS("^show all decks$"),
    SHOW_DECK("^show deck (\\w+)"),
    
    //shop:
    
    ;
    private Pattern pattern;
    private Matcher matcher;

    public Matcher setMatcher(String command) {
        this.matcher = pattern.matcher(command);
        return matcher;
    }

    public Matcher getMatcher() {
        return matcher;
    }


    RequestType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }
}