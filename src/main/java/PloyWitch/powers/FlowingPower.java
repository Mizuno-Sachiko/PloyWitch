package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static PloyWitch.BasicMod.makeID;

public class FlowingPower extends BasePower {

    public static final String POWER_ID = makeID("Flowing");

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FlowingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {

        ManaPower mana = (ManaPower) owner.getPower(ManaPower.POWER_ID);

        // Check if player has mana
        if (mana != null && mana.amount >= 1) {

            // Consume 1 mana
            mana.amount -= 1;
            mana.updateDescription();

            // Draw 2 cards
            addToBot(new DrawCardAction(owner, 2));
        }
    }

    @Override
    public void updateDescription() {
        this.description =
                "At the start of your turn, consume 1 Mana and draw 2 cards";
    }
}