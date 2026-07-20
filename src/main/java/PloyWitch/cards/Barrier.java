package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import PloyWitch.powers.ManaPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Barrier extends BaseCard {

    public static final String ID = makeID("Barrier");


    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 19;
    private static final int UPG_BLOCK = 4;

    public Barrier() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
      addToBot(new AbstractGameAction() {
        @Override
         public void update() {

                for (AbstractPower power : p.powers) {
                    if (power.type == AbstractPower.PowerType.DEBUFF) {
                        addToTop(new RemoveSpecificPowerAction(p, p, power.ID));
                    }
                }

                isDone = true;
            }
        });


        addToBot(new GainBlockAction(p, p, block));
        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);
        if (mana != null) {
            mana.spendMana(mana.amount);
        }

    }
}
