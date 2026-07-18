package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Stab extends BaseCard{
    //Adds ID to the card
    public static final String ID = makeID("Stab");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MANA_GAIN = 1;
    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 0;


    public Stab() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.baseMagicNumber = this.magicNumber = MANA_GAIN;


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new GainMana(this.magicNumber));
        addToBot(new ApplyPowerAction(
                m,
                p,
                new WeakPower(m, 1, false),
                1
        ));



    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
