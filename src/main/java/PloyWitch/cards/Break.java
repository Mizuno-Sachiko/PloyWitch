package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Break extends BaseCard {

    public static final String ID = makeID("Break");


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,    // This is an Attack card (deals damage)
            CardRarity.COMMON,   // Basic card (starter / common pool)
            CardTarget.ENEMY,   // Targets a single enemy
            0             // Energy cost (NOT used for Mana system here)
    );


    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;
    private static final int BONUS_DAMAGE = 6;
    private static final int UPG_BONUS_DAMAGE = 1;

    public Break() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(BONUS_DAMAGE, UPG_BONUS_DAMAGE);
    }






    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DamageAction(
                m,
                new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        ));

        if (ManaPower.manaGeneratedThisTurn == 0) {
            addToBot(new DamageAction(
                    m,
                    new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT
            ));
        }
    }





}