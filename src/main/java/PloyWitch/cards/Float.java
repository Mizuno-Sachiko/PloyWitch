package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Float extends BaseCard {

    public static final String ID = makeID("Float");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );


    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;
    private static final int MANA_GAIN = 1;

    public Float() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        this.baseMagicNumber = this.magicNumber = MANA_GAIN;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Always gain block
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new GainMana(this.magicNumber));

    }
}