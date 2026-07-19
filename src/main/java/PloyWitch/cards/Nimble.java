package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Nimble extends BaseCard {

    public static final String ID = makeID("Nimble");
    private int MANA_COST = 1;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );



    public Nimble() {
        super(ID, info);
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
        addToBot(new DrawCardAction(p, 2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            MANA_COST = 0;

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
