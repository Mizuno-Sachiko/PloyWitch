package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Devour extends BaseCard {

    public static final String ID = makeID("Devour");


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,    // This is an Attack card (deals damage)
            CardRarity.RARE,   // Basic card (starter / common pool)
            CardTarget.ENEMY,   // Targets a single enemy
            2             // Energy cost (NOT used for Mana system here)
    );


    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 0;


    public Devour() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int manaCount = ManaPower.manaGeneratedThisCombat;

        int totalDamage = manaCount * 3;

        addToBot(new DamageAction(
                m,
                new DamageInfo(
                        p,
                        totalDamage,
                        DamageInfo.DamageType.NORMAL
                ),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        ));
    }
    @Override
    public void applyPowers() {
        super.applyPowers();

        int mana = ManaPower.manaGeneratedThisCombat;

        this.rawDescription =
                "Deal !D! damage for each mana generated this combat. (Currently " + mana + ")";

        initializeDescription();
    }
    @Override
    public void onMoveToDiscard() {
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}