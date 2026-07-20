package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.ManaPower;
import PloyWitch.util.CardStats;
import PloyWitch.powers.GainMana;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;


public class Strategy extends BaseCard {

    public static final String ID = makeID("Strategy");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 30;
    private static final int UPG_DAMAGE = 15;
    private static final int BLOCK = 25;
    private static final int UPG_BLOCK = 5;
    private static final int MANA_GAIN = 3;
    private static final int UPG_MANA_GAIN = 4;
    private static final int ENERGY_GAIN = 1;
    private static final int UPG_ENERGY_GAIN = 2;
    private boolean goldUp = false;


    public Strategy() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        this.baseMagicNumber = this.magicNumber = MANA_GAIN;
        this.isMultiDamage = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;

        ManaPower mana = (ManaPower) p.getPower(ManaPower.POWER_ID);

        if ((mana != null && mana.amount > 0) || EnergyPanel.totalCount > 0) {
            this.cantUseMessage = CardCrawlGame.languagePack.getUIString(ID).TEXT[0];
            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new GainBlockAction(p, p, block));
        if(goldUp) {
            addToBot(new GainGoldAction(30));
        } else {
            addToBot(new GainGoldAction(20));
        }
        addToBot(new GainMana(this.magicNumber));
        addToBot(new GainEnergyAction(upgraded ? UPG_ENERGY_GAIN : ENERGY_GAIN));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.baseMagicNumber = this.magicNumber = UPG_MANA_GAIN;
            upgradeDamage(UPG_DAMAGE);
            upgradeBlock(UPG_BLOCK);
            goldUp = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


}
