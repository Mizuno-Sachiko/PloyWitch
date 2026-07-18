package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PloyWitch.util.AliceCardPool;


import static PloyWitch.BasicMod.makeID;

public class ShinyStarPower extends BasePower {

    public static final String POWER_ID = makeID("ShinyStar");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private AbstractCard storedCard;

    public ShinyStarPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        openSelection();
    }

    private void openSelection() {

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {

            private boolean opened = false;

            @Override
            public void update() {

                if (!opened) {

                    CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                    for (AbstractCard c : AliceCardPool.CARDS) {
                        group.addToTop(c.makeStatEquivalentCopy());
                    }

                    if (group.isEmpty()) {
                        isDone = true;
                        return;
                    }

                    AbstractDungeon.gridSelectScreen.open(
                            group,
                            1,
                            "Choose an Alice card",
                            false,
                            false,
                            false,
                            false
                    );

                    opened = true;
                    return;
                }

                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                    storedCard = AbstractDungeon.gridSelectScreen.selectedCards
                            .get(0)
                            .makeStatEquivalentCopy();

                    AbstractDungeon.gridSelectScreen.selectedCards.clear();

                    isDone = true;
                }
            }
        });
    }

    @Override
    public void atStartOfTurnPostDraw() {

        if (storedCard == null) return;

        flash();

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                AbstractMonster target =
                        AbstractDungeon.getCurrRoom().monsters.getRandomMonster(
                                null,
                                true,
                                AbstractDungeon.cardRandomRng
                        );

                if (target == null) {
                    this.isDone = true;
                    return;
                }

                AbstractCard copy = storedCard.makeStatEquivalentCopy();
                copy.calculateCardDamage(target);

                ManaPower.SHINY_STAR_FREE = true;

                copy.exhaustOnUseOnce = true;
                copy.purgeOnUse = true;
                copy.freeToPlayOnce = true;

                copy.use(AbstractDungeon.player, target);

                ManaPower.SHINY_STAR_FREE = false;

                this.isDone = true;
            }
        });
    }
}