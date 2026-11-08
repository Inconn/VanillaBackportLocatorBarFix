package gay.foxirl.vanillabackportlocatorbarfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.blackgear.vanillabackport.client.api.modules.waypoints.LocatorBarRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

@Mixin(LocatorBarRenderer.class)
public abstract class LocatorBarRendererMixin {
    @Shadow 
    private Minecraft minecraft;

    @Inject(method = "shouldSkipRendering()Z", at = @At("HEAD"), cancellable = true)
    private void vblbf$skipHideGui(CallbackInfoReturnable<Boolean> cir) {
        if (minecraft.options.hideGui) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "renderWaypoints(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At("HEAD"))
    private void vblbf$renderWaypointsHead(GuiGraphics guiGraphics, CallbackInfo ci) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 3000.0F);
    }

    @Inject(method = "renderWaypoints(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At("TAIL"))
    private void vblbf$renderWaypointsTail(GuiGraphics guiGraphics, CallbackInfo ci) {
        guiGraphics.pose().popPose();
    }
}
