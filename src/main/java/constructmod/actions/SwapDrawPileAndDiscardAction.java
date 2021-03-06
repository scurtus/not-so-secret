package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class SwapDrawPileAndDiscardAction extends AbstractGameAction
{
	
	private AbstractPlayer p;
	private int count;
    
    public SwapDrawPileAndDiscardAction() {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup tmpDraw = new CardGroup(CardGroupType.UNSPECIFIED);
            tmpDraw.group.addAll(p.drawPile.group);
            CardGroup tmpDiscard = new CardGroup(CardGroupType.UNSPECIFIED);
            tmpDiscard.group.addAll(p.discardPile.group);
            
           for (AbstractCard c : tmpDiscard.group) {
                ++this.count;
                if (this.count < 6) {
                    AbstractDungeon.getCurrRoom().souls.shuffle(c, false);
                }
                else {
                    AbstractDungeon.getCurrRoom().souls.shuffle(c, true);
                }
                p.discardPile.removeCard(c);
            }
            
            for (AbstractCard c2 : tmpDraw.group) {
            	c2.untip();
                c2.unhover();
                c2.darken(true);
                c2.shrink(true);
            	p.drawPile.moveToDiscardPile(c2);
            }
            
            tmpDraw.clear();
            tmpDiscard.clear();
            
        }
        this.tickDuration();
    }
}
