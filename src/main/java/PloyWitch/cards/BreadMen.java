package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BreadMen extends BaseCard {

    public static final String ID = makeID("BreadMen");
    private static final int MANA_COST = 1;
    private boolean extraHealonUpgrade = false;


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 0;



    public BreadMen() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);

    }


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

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (extraHealonUpgrade) {
            addToBot(new HealAction(p, p, 5));
        } else {
            addToBot(new HealAction(p, p, 2));

        }
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            extraHealonUpgrade = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
