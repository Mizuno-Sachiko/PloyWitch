package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import PloyWitch.powers.ManaPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SixSingingChocolates extends BaseCard {

    public static final String ID = makeID("SixSingingChocolates");


    private boolean healOnUpgrade = false;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );



    public SixSingingChocolates() {
        super(ID, info);
        this.exhaust = true;
    }







    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (healOnUpgrade) {
            addToBot(new HealAction(p, p, 2));
        }
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);
        for (int i = mana.amount; i > 0; i--) {

            addToBot(new HealAction(
                    p,
                    p,
                    2
            ));
        }}

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            healOnUpgrade = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
    }
