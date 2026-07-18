package PloyWitch.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class AliceCardPool {

    public static final ArrayList<AbstractCard> CARDS = new ArrayList<>();

    public static void register(AbstractCard card) {
        CARDS.add(card);
    }

    public static AbstractCard getRandomCard() {
        if (CARDS.isEmpty()) return null;

        return CARDS.get(
                com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(CARDS.size() - 1)
        ).makeCopy();
    }
}