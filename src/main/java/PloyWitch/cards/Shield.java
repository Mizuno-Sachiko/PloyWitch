package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Shield extends BaseCard {

    public static final String ID = makeID("Shield");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;

    public Shield() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Always gain block
        addToBot(new GainBlockAction(p, p, block));

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        // Only condition check
        if (mana != null && mana.amount >= 5) {
            addToBot(new DrawCardAction(p, 1));
        }
    }
}