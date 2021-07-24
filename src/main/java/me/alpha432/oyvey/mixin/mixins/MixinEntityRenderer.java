package me.alpha432.oyvey.mixin.mixins;

import com.google.common.base.Predicate;
import me.alpha432.oyvey.features.modules.misc.NoHitBox;
import me.alpha432.oyvey.features.modules.render.NoRender;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = {EntityRenderer.class})
public class MixinEntityRenderer {
    @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
        if (NoHitBox.getINSTANCE().isOn() && (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemPickaxe && NoHitBox.getINSTANCE().pickaxe.getValue() != false || Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && NoHitBox.getINSTANCE().crystal.getValue() != false || Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && NoHitBox.getINSTANCE().gapple.getValue() != false || Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == Items.FLINT_AND_STEEL || Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == Items.TNT_MINECART)) {
            return new ArrayList<Entity>();
        }
        return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }


    @Redirect(method = {"setupCameraTransform"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
    public float prevTimeInPortalHook(EntityPlayerSP entityPlayerSP) {
        if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).nausea.getValue()).booleanValue())
            return -3.4028235E38F;
        return entityPlayerSP.prevTimeInPortal;
    }

    @Inject(method = {"setupFog"}, at = {@At("HEAD")}, cancellable = true)
    public void setupFogHook(int startCoords, float partialTicks, CallbackInfo info) {
        if (NoRender.getInstance().isOn() && (NoRender.getInstance()).fog.getValue() == NoRender.Fog.NOFOG)
            info.cancel();
    }

    @Redirect(method = {"setupFog"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;getBlockStateAtEntityViewpoint(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;F)Lnet/minecraft/block/state/IBlockState;"))
    public IBlockState getBlockStateAtEntityViewpointHook(World worldIn, Entity entityIn, float p_186703_2_) {
        if (NoRender.getInstance().isOn() && (NoRender.getInstance()).fog.getValue() == NoRender.Fog.AIR)
            return Blocks.AIR.defaultBlockState;
        return ActiveRenderInfo.getBlockStateAtEntityViewpoint(worldIn, entityIn, p_186703_2_);
    }

    @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
    public void hurtCameraEffectHook(float ticks, CallbackInfo info) {
        if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).hurtcam.getValue()).booleanValue())
            info.cancel();
    }
}

