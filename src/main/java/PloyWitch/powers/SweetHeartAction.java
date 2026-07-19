package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PloyWitch.BasicMod.makeID;

public class SweetHeartAction extends AbstractGameAction {

    private static final String UI_ID = makeID("SweetHeartAction");

    private final boolean upgraded;

    public SweetHeartAction(boolean upgraded) {
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {

        if (duration == Settings.ACTION_DUR_FAST) {

            AbstractDungeon.gridSelectScreen.open(
                    AbstractDungeon.player.masterDeck,
                    1,
                    CardCrawlGame.languagePack.getUIString(UI_ID).TEXT[0],
                    false
            );

            tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard selected =
                    AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            AbstractCard copy = selected.makeStatEquivalentCopy();

            copy.exhaust = true;



            addToTop(new MakeTempCardInHandAction(copy));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isDone = true;
        }

        tickDuration();
    }
}
