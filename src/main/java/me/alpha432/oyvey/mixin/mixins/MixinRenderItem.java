package me.alpha432.oyvey.mixin.mixins;

import me.alpha432.oyvey.MinecraftInstance;
import me.alpha432.oyvey.features.modules.render.EnchantColor;
import me.alpha432.oyvey.features.modules.render.ViewModel;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class MixinRenderItem implements MinecraftInstance {

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"))
    private int renderEffect(final int glintVal) {
        return EnchantColor.INSTANCE.isEnabled() ? EnchantColor.INSTANCE.getColor() : glintVal;
    }

    @Inject(method = "renderItemModel", at = @At("INVOKE"))
    public void renderItem(ItemStack stack, IBakedModel bakedmodel, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo ci) {
        if (ViewModel.INSTANCE.isEnabled() && (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transform == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)) {
            float scale = ViewModel.INSTANCE.scale.getValue();
            GL11.glScalef(scale / 10, scale / 10, scale / 10);
            float translateX = ViewModel.INSTANCE.translateX.getValue();
            float translateY = ViewModel.INSTANCE.translateY.getValue();
            float translateZ = ViewModel.INSTANCE.translateZ.getValue();
            if (transform.equals(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND)) {
                if (mc.player.getActiveHand() == EnumHand.OFF_HAND && mc.player.isHandActive() && ViewModel.INSTANCE.pauseOnEat.getValue())
                    return;

                GL11.glTranslated(translateX / 15, translateY / 15, translateZ / 15);
            } else {
                if (mc.player.getActiveHand() == EnumHand.MAIN_HAND && mc.player.isHandActive() && ViewModel.INSTANCE.pauseOnEat.getValue())
                    return;

                GL11.glTranslated(-translateX / 15, translateY / 15, translateZ / 15);
            }
        }
    }

}