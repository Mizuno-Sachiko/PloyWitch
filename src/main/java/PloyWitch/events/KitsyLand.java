package PloyWitch.events;

import PloyWitch.cards.SnarkInABottle;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import static PloyWitch.BasicMod.makeID;

public class KitsyLand extends PhasedEvent {

    public static final String ID = makeID("KitsyLand");

    private static final EventStrings eventStrings =
            CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = "PloyWitch/images/events/KitsyLand.png";

    public KitsyLand() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[0])
                                .setOptionResult(i -> {

                                    SnarkInABottle rewardCard = new SnarkInABottle();


                                    AbstractDungeon.effectList.add(
                                            new com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect(
                                                    rewardCard,
                                                    Settings.WIDTH / 2.0f,
                                                    Settings.HEIGHT / 2.0f
                                            )
                                    );

                                    transitionKey("snark");
                                })
                )

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[1])
                                .setOptionResult(i -> {

                                    AbstractDungeon.player.gainGold(75);
                                    CardCrawlGame.sound.play("GOLD_GAIN");
                                    AbstractDungeon.effectList.add(
                                            new RainingGoldEffect(75)
                                    );
                                    transitionKey("gold");
                                })
                )

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[2])
                                .setOptionResult(i -> {

                                    int upgraded = 0;

                                    for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {

                                        if (c != null && c.canUpgrade()) {

                                            c.upgrade();
                                            c.superFlash();
                                            c.flash();

                                            AbstractDungeon.effectList.add(
                                                    new com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect(
                                                            c.makeStatEquivalentCopy()
                                                    )

                                            );
                                            CardCrawlGame.sound.play("CARD_UPGRADE");
                                            CardCrawlGame.sound.play("CARD_UPGRADE");
                                            upgraded++;

                                            if (upgraded >= 2) break;
                                        }
                                    }

                                    AbstractDungeon.player.masterDeck.refreshHandLayout();

                                    transitionKey("upgrade");
                                })
                )
        );

        registerPhase("snark",
                new TextPhase(DESCRIPTIONS[1])
                        .addOption(OPTIONS[3], i -> openMap())
        );

        registerPhase("gold",
                new TextPhase(DESCRIPTIONS[2])
                        .addOption(OPTIONS[3], i -> openMap())
        );

        registerPhase("upgrade",
                new TextPhase(DESCRIPTIONS[3])
                        .addOption(OPTIONS[3], i -> openMap())
        );

        transitionKey("start");
    }
}