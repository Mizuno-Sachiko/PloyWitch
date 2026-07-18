package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class MagicalCrest extends BaseCard {

    public static final String ID = makeID("MagicalCrest");
    private boolean strengthUpgrade = false;


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public MagicalCrest() {
        super(ID, info);
        this.exhaust = true;
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana.amount >= 6) {
            if(strengthUpgrade) {

            addToBot(new ApplyPowerAction(
                        p, p,
                        new StrengthPower(p, 3),
                        3
                ));
            } else {
            addToBot(new ApplyPowerAction(
                    p, p,
                    new StrengthPower(p, 2),
                    2
            ));
        }}}

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            strengthUpgrade = true;
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
        }
