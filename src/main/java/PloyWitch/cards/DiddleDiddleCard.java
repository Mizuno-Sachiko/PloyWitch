package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.util.CardStats;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DiddleDiddleCard extends BaseCard {

    public static final String ID = makeID("DiddleDiddleCard");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            2

    );

    private static final int MANA_GAIN = 1;
    private static final int UPG_MANA_GAIN = 0;

    public DiddleDiddleCard() {
        super(ID, info);

        this.baseMagicNumber = this.magicNumber = MANA_GAIN;

        this.isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        // Apply Weak (enemy)

        addToBot(new ApplyPowerAction(
                m,
                p,
                new WeakPower(m, 1, false),
                1
        ));


        //Apply Vulnerable (enemy)

        addToBot(new ApplyPowerAction(
                m,
                p,
                new VulnerablePower(m, 1, false),
                1
        ));


        // Gain Mana (player)

        addToBot(new GainMana(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MANA_GAIN);

            // cost 2 → 1
            upgradeBaseCost(1);

            initializeDescription();
        }
    }
}