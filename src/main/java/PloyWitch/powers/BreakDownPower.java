package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static PloyWitch.BasicMod.makeID;

public class BreakDownPower extends BasePower {

    public static final String POWER_ID = makeID("BreakDown");

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BreakDownPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {

            private boolean opened = false;

            @Override
            public void update() {

                if (!opened) {

                    if (AbstractDungeon.player.hand.isEmpty()) {
                        addToTop(new GainMana(1)); // ✔ CHANGED
                        isDone = true;
                        return;
                    }

                    AbstractDungeon.gridSelectScreen.open(
                            AbstractDungeon.player.hand,
                            1,
                            "Choose 1 card to shuffle into your draw pile",
                            false,
                            false,
                            false,
                            false
                    );

                    opened = true;
                    return;
                }

                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                    AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

                    AbstractDungeon.player.hand.moveToDeck(card, true);

                    AbstractDungeon.gridSelectScreen.selectedCards.clear();

                    addToTop(new GainMana(1)); // ✔ CHANGED
                    addToTop(new com.megacrit.cardcrawl.actions.common.DrawCardAction(1));

                    isDone = true;
                }
            }
        });
    }

    @Override
    public void updateDescription() {
        this.description =
                "At the start of your turn, choose 1 card from your hand to shuffle into your draw pile. Gain 1 Mana. Draw 1 Card";
    }
}