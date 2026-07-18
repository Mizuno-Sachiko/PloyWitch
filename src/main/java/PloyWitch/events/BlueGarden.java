package PloyWitch.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import static PloyWitch.BasicMod.makeID;

public class BlueGarden extends PhasedEvent {

    public static final String ID = makeID("BlueGarden");

    private static final EventStrings eventStrings =
            CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = "PloyWitch/images/events/BlueGarden.png";

    public BlueGarden() {
        super(ID, NAME, IMG);
        //Nabs MaxHp
        int healAmount = (int)(AbstractDungeon.player.maxHealth * 0.35f);
        registerPhase("start", new TextPhase(DESCRIPTIONS[0])

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[0])
                                .setOptionResult(i -> {




                                    AbstractDungeon.player.heal(healAmount );
                                    CardCrawlGame.sound.play("HEAL_1");


                                    transitionKey("heal");
                                })
                )

                .addOption(
                        new TextPhase.OptionInfo(OPTIONS[1])
                                .setOptionResult(i -> {

                                    AbstractDungeon.player.gainGold(50);
                                    CardCrawlGame.sound.play("GOLD_GAIN");
                                    AbstractDungeon.effectList.add(
                                            new RainingGoldEffect(50)
                                    );
                                    transitionKey("gold");
                                })
                )


        );

        registerPhase("heal",
                new TextPhase(DESCRIPTIONS[1])
                        .addOption(OPTIONS[2], i -> openMap())
        );

        registerPhase("gold",
                new TextPhase(DESCRIPTIONS[2])
                        .addOption(OPTIONS[2], i -> openMap())
        );



        transitionKey("start");
    }
}