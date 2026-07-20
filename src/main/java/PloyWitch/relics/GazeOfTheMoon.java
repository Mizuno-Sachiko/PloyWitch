package PloyWitch.relics;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static PloyWitch.BasicMod.makeID;

public class GazeOfTheMoon extends BaseRelic implements OnApplyPowerRelic {

    private static final String NAME = "GazeOfTheMoon";
    public static final String ID = makeID(NAME);

    private static final int MANA_GAIN = 1;
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public GazeOfTheMoon() {
        super(ID, NAME, Alice.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

        if (power instanceof WeakPower && target != null) {
            AbstractCreature powerTarget = target;
            AbstractPower currentWeak = powerTarget.getPower(WeakPower.POWER_ID);
            int weakAmount = currentWeak == null ? 0 : currentWeak.amount;

            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractPower appliedWeak = powerTarget.getPower(WeakPower.POWER_ID);

                    if (appliedWeak != null && appliedWeak.amount > weakAmount) {
                        GazeOfTheMoon.this.flash();
                        addToTop(new GainMana(MANA_GAIN));
                    }

                    isDone = true;
                }
            });
        }

        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MANA_GAIN + DESCRIPTIONS[1];
    }
}
