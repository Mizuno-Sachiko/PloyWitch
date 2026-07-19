package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.powers.Tea;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;



public class TeaTime extends BaseCard {

    private static final int MANA_COST = 3;
    public static final String ID = makeID("TeaTime");

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;

    public TeaTime() {
        super(ID, COST, TYPE, TARGET, RARITY,  Alice.Meta.CARD_COLOR);


    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana == null || mana.amount < MANA_COST) {
            this.cantUseMessage = ManaPower.getNotEnoughManaMessage();
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

        int healAmount = this.upgraded ? 10 : 7;

        addToBot(new ApplyPowerAction(
                p,
                p,
                new Tea(p, healAmount),
                healAmount
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
