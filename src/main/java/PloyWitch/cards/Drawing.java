package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class Drawing extends BaseCard {

    public static final String ID = makeID("Drawing");
    private static final int MANA_COST = 2;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public Drawing() {
        super(ID, info);
        this.baseMagicNumber = this.magicNumber = 4;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        if (!super.canUse(p, m))
            return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana == null || mana.amount < MANA_COST) {
            this.cantUseMessage = ManaPower.getNotEnoughManaMessage();
            return false;
        }

        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);
        if (mana != null) {
            mana.spendMana(MANA_COST);
        }
        addToBot(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
