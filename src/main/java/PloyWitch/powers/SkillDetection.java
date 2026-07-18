package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static PloyWitch.BasicMod.makeID;

public class SkillDetection extends BasePower {

    public static final String POWER_ID = makeID("SkillDetection");

    private int remainingTriggers;

    public SkillDetection(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);

        this.remainingTriggers = amount;


        this.loadRegion("SkillDetection");
    }

    @Override
    public void onCardDraw(AbstractCard card) {

        if (remainingTriggers <= 0) return;

        if (card.type == AbstractCard.CardType.SKILL) {
            flash();

            addToBot(new GainEnergyAction(1));
        }

        remainingTriggers--;

        if (remainingTriggers <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}