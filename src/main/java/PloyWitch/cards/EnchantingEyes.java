package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class EnchantingEyes extends BaseCard {

    public static final String ID = makeID("EnchantingEyes");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int WEAK = 1;
    private static final int UPG_WEAK = 2;

    public EnchantingEyes() {
        super(ID, info);

        this.baseMagicNumber = WEAK;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(
                m,
                p,
                new WeakPower(m, this.magicNumber, false),
                this.magicNumber
        ));

        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();


            upgradeMagicNumber(UPG_WEAK - WEAK);

            initializeDescription();
        }
    }
}