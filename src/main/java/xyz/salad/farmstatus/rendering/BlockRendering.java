package xyz.salad.farmstatus.rendering;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import static xyz.salad.farmstatus.FarmStatus.mc;

public class BlockRendering {
    public static void highlightBlock(BlockPos blockPos) {
        if (mc.thePlayer!= null && mc.theWorld!= null) {
            double x = blockPos.getX() - mc.getRenderManager().viewerPosX;
            double y = blockPos.getY() - mc.getRenderManager().viewerPosY;
            double z = blockPos.getZ() - mc.getRenderManager().viewerPosZ;

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.3F);

            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();

            worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

            worldrenderer.pos(x, y, z).endVertex();
            worldrenderer.pos(x + 1, y, z).endVertex();
            worldrenderer.pos(x + 1, y, z + 1).endVertex();
            worldrenderer.pos(x, y, z + 1).endVertex();

            worldrenderer.pos(x, y + 1, z).endVertex();
            worldrenderer.pos(x + 1, y + 1, z).endVertex();
            worldrenderer.pos(x + 1, y + 1, z + 1).endVertex();
            worldrenderer.pos(x, y + 1, z + 1).endVertex();

            worldrenderer.pos(x, y, z).endVertex();
            worldrenderer.pos(x + 1, y, z).endVertex();
            worldrenderer.pos(x + 1, y + 1, z).endVertex();
            worldrenderer.pos(x, y + 1, z).endVertex();

            worldrenderer.pos(x, y, z + 1).endVertex();
            worldrenderer.pos(x + 1, y, z + 1).endVertex();
            worldrenderer.pos(x + 1, y + 1, z + 1).endVertex();
            worldrenderer.pos(x, y + 1, z + 1).endVertex();

            worldrenderer.pos(x, y, z).endVertex();
            worldrenderer.pos(x, y, z + 1).endVertex();
            worldrenderer.pos(x, y + 1, z + 1).endVertex();
            worldrenderer.pos(x, y + 1, z).endVertex();

            worldrenderer.pos(x + 1, y, z).endVertex();
            worldrenderer.pos(x + 1, y, z + 1).endVertex();
            worldrenderer.pos(x + 1, y + 1, z + 1).endVertex();
            worldrenderer.pos(x + 1, y + 1, z).endVertex();

            tessellator.draw();

            GL11.glEnable(GL11.GL_TEXTURE_2D); // Re-enable textures
            GL11.glEnable(GL11.GL_DEPTH_TEST); // Re-enable depth testing
            GL11.glEnable(GL11.GL_CULL_FACE); // Re-enable face culling
            GL11.glDisable(GL11.GL_BLEND); // Disable blending (transparency)
            GL11.glPopMatrix();
        }
    }
}
