package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;

import java.util.Random;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Dice extends BaseCard {

    public static final String ID = makeID("Dice");

    private static final int MANA_COST = 3;

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            0
    );

    public Dice() {
        super(ID, info);
        this.exhaust = true;
        this.baseDamage = 14;
        this.baseBlock = 13;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        if (!super.canUse(p, m))
            return false;

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

        Random rng = new Random();

        int times = upgraded ? 2 : 1;

        for (int i = 0; i < times; i++) {

            int roll = rng.nextInt(6); // 0–5

            switch (roll) {

                case 0:
                    addToBot(new ApplyPowerAction(
                            p, p,
                            new DexterityPower(p, 2),
                            2
                    ));
                    break;

                case 1:
                    addToBot(new ApplyPowerAction(
                            p, p,
                            new StrengthPower(p, 2),
                            2
                    ));
                    break;

                case 2:
                    addToBot(new ApplyPowerAction(
                            m, p,
                            new WeakPower(m, 2, false),
                            2
                    ));
                    break;

                case 3:
                    addToBot(new ApplyPowerAction(
                            m, p,
                            new VulnerablePower(m, 2, false),
                            2
                    ));
                    break;

                case 4:
                    addToBot(new GainBlockAction(p, p, 13));
                    break;

                case 5:
                    addToBot(new DamageAction(
                            m,
                            new DamageInfo(p, 14),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL
                    ));
                    break;
            }
        }
    }}
