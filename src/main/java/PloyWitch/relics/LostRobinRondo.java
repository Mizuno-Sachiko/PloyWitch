package PloyWitch.relics;

import PloyWitch.powers.ManaPower;
import PloyWitch.character.Alice;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


import static PloyWitch.BasicMod.makeID;

public class LostRobinRondo extends BaseRelic {

    private static final String NAME = "LostRobinRondo";
    public static final String ID = makeID(NAME);


    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public LostRobinRondo() {
        super(ID, NAME, Alice.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onPlayerEndTurn() {
        ManaPower mana = (ManaPower) AbstractDungeon.player.getPower(ManaPower.POWER_ID);
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, mana.amount));
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }
}