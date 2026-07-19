package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import PloyWitch.powers.ManaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class ThamesTroll extends BaseCard {

    public static final String ID = makeID("ThamesTroll");

    private static final int MANA_COST = 8;
    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 15;
    private static final int UPG_BLOCK = 10;

    public ThamesTroll() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        this.exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {


        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;


        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);


        if (mana == null || mana.amount < MANA_COST) {


            this.cantUseMessage = ManaPower.getNotEnoughManaMessage();

            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if (mana != null) {
            mana.spendMana(MANA_COST);
        }

        addToBot(new GainBlockAction(p, p, block));

        addToBot(new ApplyPowerAction(
                p, p,
                new PlatedArmorPower(p, 10),
                10
        ));
        addToBot(new ApplyPowerAction(
                p, p,
                new DexterityPower(p, 2),
                2
        ));
    }




    }

