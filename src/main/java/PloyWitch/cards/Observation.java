package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import PloyWitch.powers.GainMana;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Observation extends BaseCard {

    public static final String ID = makeID("Observation");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            -1
    );
    private static final int UPG_BONUS = 1;
    private int bonus = 0;
    public Observation() {
        super(ID, info);
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int x = energyOnUse;
        p.energy.use(x);
        addToBot(new GainMana(x + bonus));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            bonus += UPG_BONUS;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
