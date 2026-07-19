package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static PloyWitch.BasicMod.makeID;

public class WitchBlood extends BasePower {

    public static final String POWER_ID = makeID("WitchesBlood");

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public WitchBlood(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner) {
            addToBot(new HealAction(
                    this.owner,
                    this.owner,
                    amount
            ));
        }
    }



    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
