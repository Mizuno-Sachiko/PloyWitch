package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import PloyWitch.powers.ManaPower;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class ScratchDumpty extends BaseCard {

    public static final String ID = makeID("ScratchDumpty");

    private static final int MANA_COST = 2;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 5;

    public ScratchDumpty() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;

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

        if (mana != null && mana.spendMana(MANA_COST)) {

            addToBot(new DamageAction(
                    m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.FIRE
            ));
        }
    }
}