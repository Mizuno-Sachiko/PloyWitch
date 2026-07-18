package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TheGreatThree extends BaseCard {

    public static final String ID = makeID("TheGreatThree");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            1
    );

    public TheGreatThree() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        FlatSnark flatSnark = new FlatSnark();
        ThamesTroll thamesTroll = new ThamesTroll();
        WanderSnatch wanderSnatch = new WanderSnatch();

        if (upgraded) {
            flatSnark.upgrade();
            thamesTroll.upgrade();
            wanderSnatch.upgrade();
        }

        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.addToTop(flatSnark);
        group.addToTop(thamesTroll);
        group.addToTop(wanderSnatch);

        AbstractDungeon.cardRewardScreen.customCombatOpen(
                group.group,
                "Choose a card to add to your hand",
                false
        );

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!AbstractDungeon.isScreenUp) {
                    AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard;
                    if (card != null) {
                        addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 1));
                        AbstractDungeon.cardRewardScreen.discoveryCard = null;
                    }
                    isDone = true;
                }
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
}