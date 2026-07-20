package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Surge extends BaseCard {

    public static final String ID = makeID("Surge");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public Surge() {
        super(ID, info);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        //Get Mana power amount
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana != null) {

            int currentMana = mana.amount;

            addToBot(new GainMana(currentMana));
        }

        if (this.upgraded) {
            addToBot(new DrawCardAction(p, 1));
        }
    }
}
