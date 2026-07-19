package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.powers.ManaPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Snarkification extends BaseCard {

    public static final String ID = makeID("Snarkification");



    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 5;
    private static final int MANA_COST = 2;
    private static final int MANA_GAIN = 2;
    private static final int UPG_MANA_GAIN = 3;



    public Snarkification() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.baseMagicNumber = this.magicNumber = MANA_GAIN;
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
        if (m.hasPower(WeakPower.POWER_ID)) {
            addToBot(new GainMana(this.magicNumber));
        }



    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MANA_GAIN);
            upgradeDamage(UPG_DAMAGE);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
