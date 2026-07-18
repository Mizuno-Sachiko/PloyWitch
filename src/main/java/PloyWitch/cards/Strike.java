package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike extends BaseCard{
    //Adds ID to the card
 public static final String ID = makeID("Strike");

 private static final CardStats info = new CardStats(
         Alice.Meta.CARD_COLOR,   //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
         CardType.ATTACK, //ATTACK/SKILL/POWER/CURSE/STATUS
         CardRarity.BASIC, //BASIC/COMMON/UNCOMMON/RARE/SPECIAL/CURSE.SPECIAL is for cards you only get from events.
         CardTarget.ENEMY, //ENEMY/ALL_ENEMY.
         1 //-1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
        );

    private static final int DAMAGE = 6; //Base Damage
    private static final int UPG_DAMAGE = 3;// Damage Added when upgraded

    public Strike() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.

        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
}
