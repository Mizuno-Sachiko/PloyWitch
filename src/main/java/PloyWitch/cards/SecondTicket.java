package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class SecondTicket extends BaseCard {

    public static final String ID = makeID("SecondTicket");

    private static final int MANA_COST = 2;
    private static final int UPG_MANA_COST = 1;

    private int manaCostActual;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int INTANGIBLE = 1;

    public SecondTicket() {
        super(ID, info);
        this.exhaust = true;
        setMagic(INTANGIBLE);

        manaCostActual = MANA_COST;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        if (!super.canUse(p, m))
            return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana == null || mana.amount < manaCostActual) {
            this.cantUseMessage = "Not enough Mana.";
            return false;
        }

        return true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            upgradeBaseCost(0);

            manaCostActual = UPG_MANA_COST;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana != null && mana.spendMana(manaCostActual)) {


            addToBot(new DamageAction(
                    p,
                    new DamageInfo(p, 2, DamageInfo.DamageType.THORNS)
                    ));
            addToBot(new ApplyPowerAction(
                    p,
                    p,
                    new IntangiblePlayerPower(p, this.magicNumber),
                    this.magicNumber
            ));
        }
    }
}