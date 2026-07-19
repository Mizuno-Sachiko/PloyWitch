package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.GainMana;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WanderSnatch extends BaseCard {

    public static final String ID = makeID("Wandersnatch");

    private static final int MANA_COST = 4;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int HEAL = 6;
    private static final int UPG_HEAL = 2;

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 5;

    private static final int MANA_GAIN = 2;
    private static final int UPG_MANA_GAIN = 1;

    public WanderSnatch() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;

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

        if (mana != null) {
            mana.spendMana(MANA_COST);
        }

        addToBot(new DamageAllEnemiesAction(
                p,
                multiDamage,
                DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL
        ));

        addToBot(new WaitAction(0.1f));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                int kills = 0;

                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {

                    if (mo == null) continue;
                    if (mo.halfDead) continue;

                    if (mo.isDead || mo.isDying) {
                        kills++;
                    }
                }

                if (kills > 0) {

                    for (int i = 0; i < kills; i++) {

                        addToTop(new HealAction(
                                p,
                                p,
                                upgraded ? (HEAL + UPG_HEAL) : HEAL
                        ));

                        addToTop(new GainMana(MANA_GAIN));
                    }
                }

                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            initializeDescription();
        }
    }
}
