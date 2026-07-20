package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.FlowingPower;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Flowing extends BaseCard {

    public static final String ID = makeID("Flowing");
    private static final int MANA_COST = 5;
    private static final int UPG_MANA_COST = 4;
    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public Flowing() {
        super(ID, info);
        this.baseMagicNumber = this.magicNumber = MANA_COST;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        if (!super.canUse(p, m))
            return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana == null || mana.amount < this.magicNumber) {
            this.cantUseMessage = ManaPower.getNotEnoughManaMessage();
            return false;
        }

        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);
        if (mana == null || !mana.spendMana(this.magicNumber)) return;

        if (!p.hasPower(FlowingPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(
                    p,
                    p,
                    new FlowingPower(p)
            ));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MANA_COST - MANA_COST);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }









}
