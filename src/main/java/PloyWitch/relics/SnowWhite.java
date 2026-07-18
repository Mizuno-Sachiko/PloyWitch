package PloyWitch.relics;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static PloyWitch.BasicMod.makeID;

public class SnowWhite extends BaseRelic {

    private static final String NAME = "SnowWhite";
    public static final String ID = makeID(NAME);

    private boolean firstTurn = true;
    private static final int MANA_GAIN = 5;
    private static final int Extra_Mana = 1;
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public SnowWhite() {
        super(ID, NAME, Alice.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Did.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                AbstractRelic relic = AbstractDungeon.player.relics.get(i);

                if (relic.relicId.equals(Did.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    return;
                }
            }
        }

        super.obtain();
    }

    @Override
    public void atBattleStart() {
        addToBot(new GainMana(MANA_GAIN));
    }

    @Override
    public void atTurnStart() {
        if (firstTurn) {
            firstTurn = false;
            return;
        }

        addToBot(new GainMana(Extra_Mana));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MANA_GAIN + DESCRIPTIONS[1];
    }
}