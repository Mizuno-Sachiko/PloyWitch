package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static PloyWitch.BasicMod.makeID;

public class WitchPower extends BasePower {

    public static final String POWER_ID = makeID("Witch");

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public WitchPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {

        if (card == null) return;

        if (card.purgeOnUse) return;


        if (card.type == AbstractCard.CardType.POWER) return;

        ManaPower mana = (ManaPower) owner.getPower(ManaPower.POWER_ID);
        if (mana == null) return;

        mana.spendMana(1);

        AbstractMonster m = AbstractDungeon.getMonsters()
                .getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        if (m == null) return;

        AbstractCard copy = card.makeStatEquivalentCopy();
        copy.purgeOnUse = true;

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {

                AbstractDungeon.actionManager.addToBottom(
                        new NewQueueCardAction(copy, m, false, true)
                );

                isDone = true;
            }
        });
    }

    @Override
    public void updateDescription() {
        this.description =
                "Whenever you play a non-Power card, it costs +1 Mana and is played a second time.";
    }
}