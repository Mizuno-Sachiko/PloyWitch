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

public class Dee extends BaseCard {

    // Unique ID used internally by Slay the Spire to identify this card
    public static final String ID = makeID("Dee");


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,      // Which character this card belongs to
            CardType.ATTACK,    // This is an Attack card (deals damage)
            CardRarity.UNCOMMON,   // Basic card (starter / common pool)
            CardTarget.ENEMY,   // Targets a single enemy
            0                   // Energy cost
    );

    // Base damage before upgrades
    private static final int DAMAGE = 9;

    // How much damage increases when upgraded
    private static final int UPG_DAMAGE = 4;


    public Dee() {
        super(ID, info);

        // Sets base damage and upgrade scaling
        setDamage(DAMAGE, UPG_DAMAGE);

        // Mana cost for this card (3 Mana required to play)
        this.baseMagicNumber = this.magicNumber = 3;
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        // First check: let base game rules run (stunned, no target, etc.)
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        // Get player's Mana power
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        // If player has no Mana OR not enough Mana → block card
        if (mana == null || mana.amount < this.magicNumber) {

            // Message shown when player tries to play card
            this.cantUseMessage = ManaPower.getNotEnoughManaMessage();

            return false;
        }

        // Otherwise card is playable
        return true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Get player's Mana power again
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        // Spend Mana only if it exists (safety check)
        if (mana != null) {
            mana.spendMana(this.magicNumber);
        }

        // Perform attack twice
        for (int i = 0; i < 2; i++) {

            // Queue damage action into game action manager
            addToBot(new DamageAction(

                    // Target enemy
                    m,

                    // Damage calculation object
                    new DamageInfo(
                            p,                  // source of damage (player)
                            damage,             // current damage value
                            DamageInfo.DamageType.NORMAL // normal damage type
                    ),

                    AbstractGameAction.AttackEffect.BLUNT_LIGHT
            ));
        }
    }
}
