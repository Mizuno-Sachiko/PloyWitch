package PloyWitch.cards;


import PloyWitch.character.Alice;
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

public class Graze extends BaseCard {

    // Unique ID used internally by Slay the Spire to identify this card
    public static final String ID = makeID("Graze");


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private boolean extrahits = false;
    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 0;


    public Graze() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.baseMagicNumber = this.magicNumber = 1;
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);


        if (mana == null || mana.amount < this.magicNumber) {


            this.cantUseMessage = "Not enough Mana.";

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

        int hits = extrahits ? 10 : 7;

        for (int i = 0; i < hits; i++) {
            addToBot(new DamageAction(
                    m,
                    new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT
            ));
        }}

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            extrahits = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}