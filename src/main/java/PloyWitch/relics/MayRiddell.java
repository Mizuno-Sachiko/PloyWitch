package PloyWitch.relics;

import PloyWitch.powers.ManaPower;
import PloyWitch.character.Alice;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


import static PloyWitch.BasicMod.makeID;

public class MayRiddell extends BaseRelic {

    private boolean active = false;
    private static final String NAME = "MayRiddell";
    public static final String ID = makeID(NAME);


    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public MayRiddell() {
        super(ID, NAME, Alice.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        ManaPower mana = (ManaPower) AbstractDungeon.player.getPower(ManaPower.POWER_ID);

        if (mana != null && mana.amount >= 5 && !active) {
            active = true;

            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, 2),
                    2
            ));
            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, 2),
                    2
            ));
        } else if ((mana == null || mana.amount < 5) && active) {
            active = false;

            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, -2),
                    -2
            ));
            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, -2),
                    -2
            ));
        }
    }

    @Override
    public void atBattleStart() {
        active = false;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }
}