package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Boulder extends BaseCard {

    public static final String ID = makeID("Boulder");
    private static final int MANA_COST = 1;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    public Boulder() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
    }

    public  boolean canUse(AbstractPlayer p, AbstractMonster m) {

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

            mana.spendMana(MANA_COST);
        addToBot(new GainBlockAction(p, p, block));



    }
}
