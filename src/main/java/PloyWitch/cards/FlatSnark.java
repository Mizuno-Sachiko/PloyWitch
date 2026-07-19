package PloyWitch.cards;

import PloyWitch.character.Alice;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;


public class FlatSnark extends BaseCard {

    public static final String ID = makeID("FlatSnark");

    private static final int MANA_COST = 6;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public boolean playedBySnarkInABottle = false;
    private static final int DAMAGE = 25;
    private static final int UPG_DAMAGE = 5;
    private static final int WEAK = 2;
    private static final int UPG_WEAK = 1;

    public FlatSnark() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(WEAK, UPG_WEAK);
        this.exhaust = true;

    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        // allow bypass if spawned by SnarkInABottle
        if (playedBySnarkInABottle) {
            return super.canUse(p, m);
        }

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

        // ONLY spend mana if NOT spawned by SnarkInABottle
        if (!playedBySnarkInABottle) {
            if (mana != null) {
                mana.spendMana(MANA_COST);
            }
        }

        int finalDamage = damage;

        if (m.currentHealth <= m.maxHealth / 2) {
            finalDamage *= 2;
        }

        addToBot(new ApplyPowerAction(
                m,
                p,
                new WeakPower(m, this.magicNumber, false),
                this.magicNumber
        ));

        addToBot(new DamageAction(
                m,
                new DamageInfo(p, finalDamage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_WEAK);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
