package PloyWitch.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class OffGuardAction extends AbstractGameAction {

    public OffGuardAction() {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        int energyGain = 0;

        for (AbstractCard card : DrawCardAction.drawnCards) {
            if (card.type == AbstractCard.CardType.SKILL) {
                energyGain++;
            }
        }

        if (energyGain > 0) {
            addToTop(new GainEnergyAction(energyGain));
        }

        this.isDone = true;
    }
}
