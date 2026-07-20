package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Voice extends AbstractPower {

    public static final String POWER_ID = PloyWitch.BasicMod.makeID("Voice");

    private boolean appliedFrail = false;
    private final String[] DESCRIPTIONS;

    public Voice(AbstractCreature owner) {
        this.ID = POWER_ID;
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        this.name = powerStrings.NAME;
        this.DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        this.owner = owner;
        this.amount = -1;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        //Custom img
        this.img = ImageMaster.loadImage("PloyWitch/images/powers/VoiceOfYumina.png");

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {

        int mana = 0;

        ManaPower manaPower = (ManaPower) AbstractDungeon.player.getPower(ManaPower.POWER_ID);
        if (manaPower != null) {
            mana = manaPower.amount;
        }

        int strengthGain = mana / 2;

        if (strengthGain > 0) {
            addToBot(new ApplyPowerAction(
                    owner,
                    owner,
                    new StrengthPower(owner, strengthGain),
                    strengthGain,
                    true,
                    com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE
            ));
        }

        if (!appliedFrail) {
            appliedFrail = true;

            addToBot(new ApplyPowerAction(
                    owner,
                    owner,
                    new FrailPower(owner, 99, false),
                    99,
                    true,
                    com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE
            ));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
