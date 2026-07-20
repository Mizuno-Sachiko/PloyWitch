package PloyWitch.cards;

import PloyWitch.character.Alice;
import PloyWitch.powers.Voice;
import PloyWitch.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VoiceOfYumina extends BaseCard {

    public static final String ID = makeID("VoiceOfYumina");

    private static final CardStats info = new CardStats(
            Alice.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public VoiceOfYumina() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (!p.hasPower(Voice.POWER_ID)) {
            addToBot(new ApplyPowerAction(
                    p,
                    p,
                    new Voice(p)
            ));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }









}
