package PloyWitch.events;

import PloyWitch.cards.ShinyStar;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import static PloyWitch.BasicMod.makeID;

public class ShinyStarEvent extends PhasedEvent {

    public static final String ID = makeID("ShinyStarEvent");

    private static final EventStrings eventStrings =
            CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = "PloyWitch/images/events/ShinyStarEvent.png";

    public ShinyStarEvent() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[0])
                                .setOptionResult(i -> {

                                    AbstractDungeon.player.gainGold(999);
                                    CardCrawlGame.sound.play("GOLD_GAIN");
                                    AbstractDungeon.effectList.add(
                                            new RainingGoldEffect(999)
                                    );
                                    transitionKey("gold");
                                })
                )

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[1])
                                .setOptionResult(i -> {

                                    for(int a = 0; a < 5; a++) {
                                        AbstractRelic relic = getUniqueCommonRelic();


                                        AbstractDungeon.getCurrRoom()
                                                .spawnRelicAndObtain(drawX, drawY, relic);


                                    }

                                    transitionKey("relics");
                                })
                )
                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[2])
                                .setOptionResult(i -> {

                                    ShinyStar rewardCard = new ShinyStar();


                                    AbstractDungeon.effectList.add(
                                            new com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect(
                                                    rewardCard,
                                                    Settings.WIDTH / 2.0f,
                                                    Settings.HEIGHT / 2.0f
                                            )
                                    );

                                    transitionKey("star");
                                })
                )
        );

        registerPhase("gold",
                new TextPhase(DESCRIPTIONS[1])
                        .addOption(OPTIONS[3], i -> openMap())
        );

        registerPhase("relics",
                new TextPhase(DESCRIPTIONS[2])
                        .addOption(OPTIONS[3], i -> openMap())
        );

        registerPhase("star",
                new TextPhase(DESCRIPTIONS[3])
                        .addOption(OPTIONS[3], i -> openMap())
        );

        transitionKey("start");

    }

    private static AbstractRelic getUniqueCommonRelic() {
        AbstractRelic relic = null;




            relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);

            if (relic != null && !AbstractDungeon.player.hasRelic(relic.relicId)) {
                return relic;
            }


        return relic;
    }
}