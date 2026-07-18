package PloyWitch.relics;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;


import static PloyWitch.BasicMod.makeID;

public class Did extends BaseRelic {

    private static final String NAME = "Did";
    public static final String ID = makeID(NAME);

    private static final int MANA_GAIN = 3;
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public Did() {
        super(ID, NAME, Alice.Meta.CARD_COLOR, RARITY, SOUND);

    }

    @Override
    public void atBattleStart() {
        addToBot(new GainMana(MANA_GAIN));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MANA_GAIN + DESCRIPTIONS[1];
    }
}