package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.powers.WitchPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Witch extends BaseCard {

    public static final String ID = makeID("Witch");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Witch() {
        super(ID, info);
        this.baseMagicNumber = this.magicNumber = 3;

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {


        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        // Get player's Mana power
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);


        if (mana == null || mana.amount < this.magicNumber) {


            this.cantUseMessage = "Not enough Mana.";

            return false;
        }


        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);


        if (mana != null) {

            mana.spendMana(this.magicNumber);
        }

        addToBot(new ApplyPowerAction(
                p,
                p,
                new WitchPower(p, 1),
                1
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}