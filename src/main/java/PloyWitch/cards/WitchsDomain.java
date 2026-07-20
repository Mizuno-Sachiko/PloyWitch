package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.powers.ManaPower;

// Card metadata helper (your framework utility)
import PloyWitch.util.CardStats;

// Slay the Spire action system (used to queue effects)
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;

// Damage calculation container (who deals damage, how much, what type)
import com.megacrit.cardcrawl.cards.DamageInfo;

// Core game classes (player + enemy)
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class WitchsDomain extends BaseCard {

    // Unique ID used internally by Slay the Spire to identify this card
    public static final String ID = makeID("WitchsDomain");


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );//


    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 4;


    public WitchsDomain() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.baseMagicNumber = this.magicNumber = 5;
    }


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

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana != null) {
            mana.spendMana(this.magicNumber);
        }

        int hits = 1;

        for (AbstractPower power : m.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF) {
                hits++;
            }
        }

        for (int j = 0; j < hits; j++) {

            addToBot(new DamageAction(
                    m,
                    new DamageInfo(
                            p,
                            damage,
                            DamageInfo.DamageType.NORMAL
                    ),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT
            ));
        }
    }
}
