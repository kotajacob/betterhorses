package nz.kota.betterhorses.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(HorseBaseEntity.class)
public abstract class BetterHorsesMixin extends AnimalEntity {

	protected BetterHorsesMixin(EntityType<? extends HorseBaseEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "setChildAttributes", at = @At(value = "HEAD"), cancellable = true)
	protected void onSetChildAttributes(PassiveEntity mate, HorseBaseEntity child, CallbackInfo ci) {
		// Average the parents stats.
		double health = (this.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)
				+ mate.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)) * 0.5D;

		double jump = (this.getAttributeBaseValue(EntityAttributes.HORSE_JUMP_STRENGTH)
				+ mate.getAttributeBaseValue(EntityAttributes.HORSE_JUMP_STRENGTH)) * 0.5D;

		double speed = (this.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)
				+ mate.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)) * 0.5D;

		// Modify the stats randomly and clamp to the maximum and minimum values
		// found in vanilla.
		health = this.clamp(15D, 30D,	health
				+ this.getChildHealthAdjustment());
		jump = this.clamp(0.4000000059604645D, 1.000000006D, jump
				+ this.getChildJumpStrengthAdjustment());
		speed = this.clamp(0.112499997D, 0.337499997D, speed
				+ this.getChildMovementSpeedAdjustment());

		child.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(health);
		child.getAttributeInstance(EntityAttributes.HORSE_JUMP_STRENGTH).setBaseValue(jump);
		child.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
		ci.cancel();
	}

	public double getChildHealthAdjustment() {
		return 4D - (double)this.random.nextInt(8);
	}

	public double getChildJumpStrengthAdjustment() {
		return 0.25 * (0.5D - this.random.nextDouble());
	}

	// Adjust movement speed by a maximum of about 10%.
	// Speed is in internal units, not meters/second.
	public double getChildMovementSpeedAdjustment() {
		return 0.225D * 0.20D * (0.5D - this.random.nextDouble());
	}

	private double clamp(double min, double max, double val) {
		return Math.max(min, Math.min(max, val));
	}
}
