package PloyWitch.cards;

import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Penguin extends BaseCard {

    public static final String ID = makeID("Penguin");

    private static final int MANA_COST = 2;
    private boolean runTwice = false;


    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );


    public Penguin() {
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
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            runTwice = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana != null && mana.spendMana(MANA_COST)) {

            int repeats = runTwice ? 2 : 1;

            for (int i = 0; i < repeats; i++) {

                addToBot(new ApplyPowerAction(
                        m,
                        p,
                        new StrengthPower(m, -1),
                        -1
                ));

                addToBot(new ApplyPowerAction(
                        m,
                        p,
                        new WeakPower(m, 1, false),
                        1
                ));
            }
        }
    }
}
