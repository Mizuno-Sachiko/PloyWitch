package PloyWitch.cards;

import PloyWitch.character.Alice;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Gandr extends BaseCard{
    //Adds ID to the card
    public static final String ID = makeID("Gandr");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,   //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //BASIC/COMMON/UNCOMMON/RARE/SPECIAL/CURSE.SPECIAL is for cards you only get from events.
            CardTarget.ENEMY, //ENEMY/ALL_ENEMY.
            0 //-1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 3; //Base Damage
    private static final int UPG_DAMAGE = 3;// Damage Added when upgraded


    public Gandr() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        addToBot(new ApplyPowerAction(
                m,
                p,
                new VulnerablePower(m, 1, false),
                1
        ));
        // Draw only when upgraded
        if (this.upgraded) {
            addToBot(new DrawCardAction(p, 1));
        }

    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
