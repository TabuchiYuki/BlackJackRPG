package programs.blackJack;

import programs.data.master.CardSuit;

public class Card {
    private CardSuit suit;
    private int rank;

    public Card(CardSuit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }
}