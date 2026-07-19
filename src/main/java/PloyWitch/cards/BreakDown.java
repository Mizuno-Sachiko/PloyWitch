package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.BreakDownPower;
import PloyWitch.powers.ManaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PloyWitch.util.GeneralUtils.removePrefix;
import static PloyWitch.util.TextureLoader.getCardTextureString;

public class BreakDown extends BaseCard {

    public static final String ID = makeID("BreakDown");

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    public BreakDown() {
        super(ID,
                COST,
                TYPE,
                TARGET,
                RARITY,
                Alice.Meta.CARD_COLOR,
                getCardTextureString(removePrefix(ID), TYPE)
        );

        // Mana cost = 5
        this.baseMagicNumber = this.magicNumber = 5;
    }

    //Mana check (same system as Dee)
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana == null || mana.amount < this.magicNumber) {
            this.cantUseMessage = ManaPower.getNotEnoughManaMessage();
            return false;
        }

        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Spend Mana first
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);
        if (mana != null) {
            mana.spendMana(this.magicNumber);
        }

        // Apply power
        addToBot(new ApplyPowerAction(
                p,
                p,
                new BreakDownPower(p, 1),
                1
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0); // stays 0 energy forever
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
