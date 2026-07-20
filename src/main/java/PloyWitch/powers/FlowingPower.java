package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static PloyWitch.BasicMod.makeID;

public class FlowingPower extends BasePower {

    public static final String POWER_ID = makeID("Flowing");

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FlowingPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void atStartOfTurnPostDraw() {

        ManaPower mana = (ManaPower) owner.getPower(ManaPower.POWER_ID);

        if (mana != null && mana.spendMana(1)) {
            addToBot(new DrawCardAction(owner, 2));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
