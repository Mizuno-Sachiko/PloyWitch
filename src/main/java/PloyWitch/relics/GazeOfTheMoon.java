package PloyWitch.relics;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
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

        if (power instanceof WeakPower) {
            addToBot(new GainMana(MANA_GAIN));
        }

        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MANA_GAIN + DESCRIPTIONS[1];
    }
}