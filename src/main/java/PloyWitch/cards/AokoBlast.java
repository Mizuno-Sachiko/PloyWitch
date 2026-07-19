package PloyWitch.cards;


import PloyWitch.character.Alice;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AokoBlast extends BaseCard {

    public static final String ID = makeID("AokoBlast");

    private static final int MANA_COST = 2;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 11;
    private static final int UPG_DAMAGE = 8;

    public AokoBlast() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.isMultiDamage = true;

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
        addToBot(new DamageAllEnemiesAction(
                p,
                multiDamage,
                DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        ));

        }

}
