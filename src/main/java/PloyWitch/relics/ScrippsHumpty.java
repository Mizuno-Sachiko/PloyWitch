package PloyWitch.relics;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PloyWitch.BasicMod.makeID;

public class ScrippsHumpty extends BaseRelic {

    private static final String NAME = "ScrippsHumpty";
    public static final String ID = makeID(NAME);

    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public ScrippsHumpty() {
        super(ID, NAME, Alice.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atBattleStart() {
        flash();
    }


    public void onManaSpent() {

        AbstractMonster target = AbstractDungeon.getRandomMonster();

        if (target != null) {
            flash();

            addToBot(new DamageAction(
                    target,
                    new DamageInfo(AbstractDungeon.player, 5, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
            ));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }
}