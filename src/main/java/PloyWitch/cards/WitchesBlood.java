package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.WitchBlood;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WitchesBlood extends BaseCard {

    public static final String ID = makeID("WitchesBlood");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public WitchesBlood() {
        super(ID, info);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        addToBot(new ApplyPowerAction(
                p,
                p,
                new WitchBlood(p, 1),
                1
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }









}
