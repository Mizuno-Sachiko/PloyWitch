package PloyWitch.events;

import PloyWitch.relics.LostRobinRondo;
import PloyWitch.relics.ScrippsHumpty;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static PloyWitch.BasicMod.makeID;

public class PloyShop extends PhasedEvent {

    public static final String ID = makeID("PloyShop");

    private static final EventStrings eventStrings =
            CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = "PloyWitch/images/events/PloyShop.png";

    public PloyShop() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[0])
                                .enabledCondition(
                                        () -> AbstractDungeon.player.gold >= 50
                                                && !AbstractDungeon.player.hasRelic(LostRobinRondo.ID),
                                        OPTIONS[3]
                                )
                                .setOptionResult(i -> {

                                    AbstractRelic relic = new LostRobinRondo();

                                    AbstractDungeon.player.loseGold(50);

                                    AbstractDungeon.getCurrRoom()
                                            .spawnRelicAndObtain(drawX, drawY, relic);

                                    AbstractEvent.logMetricObtainRelicAtCost(
                                            ID,
                                            "Bought Lost Robin Rondo",
                                            relic,
                                            50
                                    );
                                    CardCrawlGame.sound.play("GOLD_JINGLE");
                                    transitionKey("boughtRondo");
                                })
                )

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[1])
                                .enabledCondition(
                                        () -> AbstractDungeon.player.gold >= 80
                                                && !AbstractDungeon.player.hasRelic(ScrippsHumpty.ID),
                                        OPTIONS[4]
                                )
                                .setOptionResult(i -> {

                                    AbstractRelic relic = new ScrippsHumpty();

                                    AbstractDungeon.player.loseGold(80);

                                    AbstractDungeon.getCurrRoom()
                                            .spawnRelicAndObtain(drawX, drawY, relic);

                                    AbstractEvent.logMetricObtainRelicAtCost(
                                            ID,
                                            "Bought Scripps Humpty",
                                            relic,
                                            80
                                    );
                                    CardCrawlGame.sound.play("GOLD_JINGLE");
                                    transitionKey("boughtHumpty");
                                })
                )

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[2])
                                .enabledCondition(
                                        () -> AbstractDungeon.player.gold >= 120,
                                        OPTIONS[5]
                                )
                                .setOptionResult(i -> {

                                    AbstractRelic relic = getUniqueRareRelic();

                                    AbstractDungeon.player.loseGold(120);

                                    AbstractDungeon.getCurrRoom()
                                            .spawnRelicAndObtain(drawX, drawY, relic);

                                    AbstractEvent.logMetricObtainRelicAtCost(
                                            ID,
                                            "Bought Rare Relic",
                                            relic,
                                            120
                                    );
                                    CardCrawlGame.sound.play("GOLD_JINGLE");
                                    transitionKey("boughtRare");
                                })
                )

                .addOption(OPTIONS[6], i -> transitionKey("decline"))
        );

        registerPhase("boughtRondo",
                new TextPhase(DESCRIPTIONS[1])
                        .addOption(OPTIONS[7], i -> openMap())
        );

        registerPhase("boughtHumpty",
                new TextPhase(DESCRIPTIONS[2])
                        .addOption(OPTIONS[7], i -> openMap())
        );

        registerPhase("boughtRare",
                new TextPhase(DESCRIPTIONS[3])
                        .addOption(OPTIONS[7], i -> openMap())
        );

        registerPhase("decline",
                new TextPhase(DESCRIPTIONS[4])
                        .addOption(OPTIONS[7], i -> openMap())

        );

        transitionKey("start");
    }

    private static AbstractRelic getUniqueRareRelic() {
        AbstractRelic relic = null;

        for (int i = 0; i < 50; i++) {
            relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);

            if (relic != null && !AbstractDungeon.player.hasRelic(relic.relicId)) {
                return relic;
            }
        }

        return relic;
    }
}