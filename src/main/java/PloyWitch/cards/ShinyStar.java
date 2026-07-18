package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.BreakDownPower;
import PloyWitch.powers.ShinyStarPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;



public class ShinyStar extends BaseCard {

    public static final String ID = makeID("ShinyStar");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public ShinyStar() {
        super(ID, info);
        this.selfRetain = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        addToBot(new ApplyPowerAction(
                p,
                p,
                new ShinyStarPower(p, 1),
                1
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}