package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class MoonDrip extends BaseCard {

    public static final String ID = makeID("MoonDrip");
    private static final int MANA_COST = 1;
    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    public MoonDrip() {
        super(ID, info);
        this.exhaust = true;
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        if (!super.canUse(p, m))
            return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana == null || mana.amount < MANA_COST) {
            this.cantUseMessage = "Not enough Mana.";
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

        int debuffAmount = upgraded ? 3 : 2;

        addToBot(new ApplyPowerAction(
                m,
                p,
                new VulnerablePower(m, debuffAmount, false),
                debuffAmount
        ));
        addToBot(new ApplyPowerAction(
                m,
                p,
                new WeakPower(m, debuffAmount, false),
                debuffAmount
        ));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}