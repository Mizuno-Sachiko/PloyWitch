package PloyWitch.cards;


import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SnarkInABottle extends BaseCard {

    public static final String ID = makeID("SnarkInABottle");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public SnarkInABottle() {
        super(ID, info);
        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        FlatSnark snark = (FlatSnark) new FlatSnark().makeStatEquivalentCopy();

        snark.playedBySnarkInABottle = true;

        snark.calculateCardDamage(m);

        AbstractDungeon.actionManager.cardQueue.add(
                new CardQueueItem(snark, m)
        );

        addToBot(new DamageAction(
                p,
                new DamageInfo(p, 8, DamageInfo.DamageType.THORNS)
        ));

        AbstractDungeon.player.masterDeck.removeCard(this);



    }
    @Override
    public boolean canUpgrade() {
        return false;
    }

}