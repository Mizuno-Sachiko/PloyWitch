package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainMana extends AbstractGameAction {

    private int amount;

    public GainMana(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {

        int gain = amount;

        ManaPower mana = (ManaPower) AbstractDungeon.player.getPower(ManaPower.POWER_ID);

        if (mana != null && mana.isManaGainDoubled()) {
            gain *= 2;
        }

        ManaPower.manaGeneratedThisCombat += gain;
        ManaPower.manaGeneratedThisTurn += gain;

        addToTop(new ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                new ManaPower(AbstractDungeon.player, gain),
                gain
        ));

        isDone = true;
    }
}
