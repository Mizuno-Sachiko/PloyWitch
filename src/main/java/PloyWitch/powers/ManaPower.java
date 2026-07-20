package PloyWitch.powers;

import PloyWitch.relics.ScrippsHumpty;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ManaPower extends AbstractPower {

    public static final String POWER_ID = PloyWitch.BasicMod.makeID("ManaPower");
    public static final int MAX_MANA = 10;

    public static boolean FREE_NEXT_CARD = false;
    public static boolean SHINY_STAR_FREE = false;

    public static int manaGeneratedThisCombat = 0;
    public static int manaGeneratedThisTurn = 0;

    private final String[] DESCRIPTIONS;
    private boolean manaGainDoubledThisTurn = false;

    public ManaPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        this.name = powerStrings.NAME;
        this.DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        this.owner = owner;
        this.amount = Math.min(amount, MAX_MANA);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage("PloyWitch/images/powers/Mana.png");
        updateDescription();
    }

    public static String getNotEnoughManaMessage() {
        return CardCrawlGame.languagePack.getUIString(POWER_ID).TEXT[0];
    }

    public static void resetManaTracking() {
        manaGeneratedThisCombat = 0;
        manaGeneratedThisTurn = 0;
    }

    public static void recordManaGenerated(int amount) {
        manaGeneratedThisCombat += amount;
        manaGeneratedThisTurn += amount;
    }

    public void enableManaGainDoubling() {
        this.manaGainDoubledThisTurn = true;
    }

    public boolean isManaGainDoubled() {
        return this.manaGainDoubledThisTurn;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.flash();
        this.amount = Math.min(this.amount + stackAmount, MAX_MANA);
        updateDescription();
    }



    public boolean spendMana(int cost) {


        if (cost == 0) {
            return true;
        }

        if (SHINY_STAR_FREE) {
            SHINY_STAR_FREE = false;
            updateDescription();
            return true;
        }

        if (FREE_NEXT_CARD) {
            FREE_NEXT_CARD = false;
            updateDescription();
            return true;
        }

        if (this.amount >= cost) {
            this.amount -= cost;

            if (AbstractDungeon.player.hasRelic(ScrippsHumpty.ID)) {
                ((ScrippsHumpty)AbstractDungeon.player.getRelic(ScrippsHumpty.ID)).onManaSpent();
            }

            updateDescription();
            return true;
        }

        return false;
    }

    @Override
    public void atStartOfTurn() {
        this.manaGainDoubledThisTurn = false;
    }

    //Used Mainly For "Devour"
    @Override
    public void onVictory() {
        resetManaTracking();
    }

    //Used for "Break"
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        manaGeneratedThisTurn = 0;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + MAX_MANA + DESCRIPTIONS[2];
    }
}
