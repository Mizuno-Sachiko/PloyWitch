package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Circuts extends BaseCard {

    public static final String ID = makeID("Circuts");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    private static final int MANA_GAIN = 2;
    private static final int UPG_MANA_GAIN = 1;

    public Circuts() {
        super(ID, info);


        this.baseMagicNumber = this.magicNumber = MANA_GAIN;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        addToBot(new GainMana(this.magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeMagicNumber(UPG_MANA_GAIN);
    }
}