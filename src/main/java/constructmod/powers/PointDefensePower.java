package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class PointDefensePower extends AbstractCyclePower {
	public static final String POWER_ID = ConstructMod.makeID("PointDefense");
	public static final String NAME = "Point Defense";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever a card #ycycles, gain #b1 #yBlock."
	};
	
	private int amountPerTurn=0;
	
	public PointDefensePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = this.amountPerTurn = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/pointdefense.png");
	}
	
	@Override
	public void stackPower(final int stackAmount) {
		super.stackPower(stackAmount);
		this.amountPerTurn += stackAmount;
    }
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atStartOfTurn() {
		this.amount = this.amountPerTurn;
    }
	
	@Override
	public void onCycleCard(AbstractCard card) {
		if (this.amount > 0) amount--;
		else return;
		
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner,this.owner,1));
	}
}