package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

import java.util.ArrayList;

public class WanderSnatchAction extends DamageAllEnemiesAction {

    private final AbstractPlayer player;
    private final int manaGain;
    private final int heal;
    private final ArrayList<AbstractMonster> livingEnemies = new ArrayList<>();
    private boolean initialized = false;
    private boolean rewardsGranted = false;

    public WanderSnatchAction(AbstractPlayer player, int[] damage, int manaGain, int heal) {
        super(
                player,
                damage,
                DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL
        );
        this.player = player;
        this.manaGain = manaGain;
        this.heal = heal;
    }

    @Override
    public void update() {
        if (!initialized) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo != null && !mo.isDead && !mo.isDying && mo.currentHealth > 0 && !mo.halfDead && !mo.isEscaping) {
                    livingEnemies.add(mo);
                }
            }

            initialized = true;
        }

        // Resolve rewards from the current damage action so final kills survive
        // DamageAllEnemiesAction's post-combat queue cleanup.
        super.update();

        if (!isDone || rewardsGranted) {
            return;
        }

        rewardsGranted = true;

        for (AbstractMonster mo : livingEnemies) {
            if ((mo.isDying || mo.currentHealth <= 0)
                    && !mo.halfDead
                    && !mo.hasPower(MinionPower.POWER_ID)) {
                addToTop(new HealAction(player, player, heal));
                addToTop(new GainMana(manaGain));
            }
        }
    }
}
