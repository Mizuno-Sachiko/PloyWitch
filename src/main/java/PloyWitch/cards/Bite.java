package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class Bite extends BaseCard{


    final static String ID = makeID("Bite");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private static final int MANA_GAIN = 1;
    private static final int UPG_MANA_GAIN = 1;

    public Bite() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.baseMagicNumber = this.magicNumber = MANA_GAIN;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new GainMana(this.magicNumber));
        }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeMagicNumber(UPG_MANA_GAIN);
    }


    }






