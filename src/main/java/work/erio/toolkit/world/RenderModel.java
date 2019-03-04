package work.erio.toolkit.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import work.erio.toolkit.proxy.ClientProxy;
import work.erio.toolkit.tile.TileEntityModel;

import java.util.Set;

public class RenderModel extends RenderGlobal {
    public static final RenderModel INSTANCE = new RenderModel(Minecraft.getMinecraft());
    private static Vec3d POSITION_OFFSET = new Vec3d(0, 0, 0);
    private Profiler profiler;
    private Minecraft mc;
    private ModelWorld world;
    private RenderManager renderManager;
    private int frameCount = 0;
    private double frustumUpdatePosX = Double.MIN_VALUE;
    private double frustumUpdatePosY = Double.MIN_VALUE;
    private double frustumUpdatePosZ = Double.MIN_VALUE;
    private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
    private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
    private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;

    public RenderModel(Minecraft mcIn) {
        super(mcIn);
        this.mc = mcIn;
        this.profiler = mcIn.mcProfiler;
        this.renderManager = this.mc.getRenderManager();
    }

    public void onRender(TileEntityModel te, float partialTicks) {
        EntityPlayerSP player = this.mc.player;
        if (player != null) {
            this.profiler.startSection("model");
//            ClientProxy.setPlayerData(player, event.getPartialTicks());
//            ModelWorld model = ClientProxy.schematic;
            ModelWorld modelWorld = new ModelWorld(te.getModel());

            GlStateManager.pushMatrix();
            renderModel(te, modelWorld, partialTicks);
            GlStateManager.popMatrix();

            this.profiler.endSection();
        }
    }

    private void renderModel(TileEntityModel te, ModelWorld model, float partialTicks) {
        if (this.world != model) {
            this.world = model;

            loadRenderers();
        }

        POSITION_OFFSET.add(new Vec3d(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()))
                .subtract(renderManager.viewerPosX, renderManager.viewerPosY, renderManager.viewerPosZ);


        int fps = Math.max(Minecraft.getDebugFPS(), 30);
        renderWorld(partialTicks, System.nanoTime() + 1000000000 / fps);

    }

    private void renderWorld(  float partialTicks,   long finishTimeNano) {
        GlStateManager.enableCull();
        this.profiler.endStartSection("culling");
          Frustum frustum = new Frustum();
          Entity entity = this.mc.getRenderViewEntity();

          double x = POSITION_OFFSET.x;
          double y = POSITION_OFFSET.y;
          double z = POSITION_OFFSET.z;
        frustum.setPosition(x, y, z);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        this.profiler.endStartSection("prepareterrain");
        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        RenderHelper.disableStandardItemLighting();

        this.profiler.endStartSection("terrain_setup");
//        setupTerrain(entity, partialTicks, frustum, this.frameCount++, isInsideWorld(x, y, z));

        this.profiler.endStartSection("updatechunks");
//        updateChunks(finishTimeNano / 2);

        this.profiler.endStartSection("terrain");
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        renderBlockLayer(BlockRenderLayer.SOLID, partialTicks, 2, entity);
        renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, partialTicks, 2, entity);
        this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        renderBlockLayer(BlockRenderLayer.CUTOUT, partialTicks, 2, entity);
        this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        this.profiler.endStartSection("entities");
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
//        renderEntities(entity, frustum, partialTicks);
        GlStateManager.disableBlend();
        RenderHelper.disableStandardItemLighting();
        disableLightmap();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();

        GlStateManager.enableCull();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        this.profiler.endStartSection("translucent");
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        renderBlockLayer(BlockRenderLayer.TRANSLUCENT, partialTicks, 2, entity);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableCull();
    }

    private void disableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

//    @Override
//    public void setupTerrain(  Entity viewEntity,   double partialTicks,   ICamera camera,   int frameCount,   boolean playerSpectator) {
////        if (ConfigurationHandler.renderDistance != this.renderDistanceChunks || this.vboEnabled != OpenGlHelper.useVbo()) {
////            loadRenderers();
////        }
//
//        this.profiler.startSection("camera");
//          double posX = POSITION_OFFSET.x;
//          double posY = POSITION_OFFSET.y;
//          double posZ = POSITION_OFFSET.z;
//
//          double deltaX = posX - this.frustumUpdatePosX;
//          double deltaY = posY - this.frustumUpdatePosY;
//          double deltaZ = posZ - this.frustumUpdatePosZ;
//
//          int chunkCoordX = MathHelper.floor(posX) >> 4;
//          int chunkCoordY = MathHelper.floor(posY) >> 4;
//          int chunkCoordZ = MathHelper.floor(posZ) >> 4;
//
//        if (this.frustumUpdatePosChunkX != chunkCoordX || this.frustumUpdatePosChunkY != chunkCoordY || this.frustumUpdatePosChunkZ != chunkCoordZ || deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 16.0) {
//            this.frustumUpdatePosX = posX;
//            this.frustumUpdatePosY = posY;
//            this.frustumUpdatePosZ = posZ;
//            this.frustumUpdatePosChunkX = chunkCoordX;
//            this.frustumUpdatePosChunkY = chunkCoordY;
//            this.frustumUpdatePosChunkZ = chunkCoordZ;
//            this.viewFrustum.updateChunkPositions(posX, posZ);
//        }
//
//        this.profiler.endStartSection("renderlistcamera");
//        this.renderContainer.initialize(posX, posY, posZ);
//
//        this.profiler.endStartSection("culling");
//          BlockPos posEye = new BlockPos(posX, posY + viewEntity.getEyeHeight(), posZ);
//          RenderChunk renderChunkCurrent = this.viewFrustum.getRenderChunk(posEye);
//          RenderOverlay renderOverlayCurrent = this.viewFrustum.getRenderOverlay(posEye);
//
//        this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || posX != this.lastViewEntityX || posY != this.lastViewEntityY || posZ != this.lastViewEntityZ || viewEntity.rotationPitch != this.lastViewEntityPitch || viewEntity.rotationYaw != this.lastViewEntityYaw;
//        this.lastViewEntityX = posX;
//        this.lastViewEntityY = posY;
//        this.lastViewEntityZ = posZ;
//        this.lastViewEntityPitch = viewEntity.rotationPitch;
//        this.lastViewEntityYaw = viewEntity.rotationYaw;
//
//        this.profiler.endStartSection("update");
//        if (this.displayListEntitiesDirty) {
//            this.displayListEntitiesDirty = false;
//            this.renderInfos = Lists.newArrayListWithCapacity(CHUNKS);
//
//              LinkedList<ContainerLocalRenderInformation> renderInfoList = Lists.newLinkedList();
//            boolean renderChunksMany = this.mc.renderChunksMany;
//
//            if (renderChunkCurrent == null) {
//                  int chunkY = posEye.getY() > 0 ? 248 : 8;
//
//                for (int chunkX = -this.renderDistanceChunks; chunkX <= this.renderDistanceChunks; chunkX++) {
//                    for (int chunkZ = -this.renderDistanceChunks; chunkZ <= this.renderDistanceChunks; chunkZ++) {
//                          BlockPos pos = new BlockPos((chunkX << 4) + 8, chunkY, (chunkZ << 4) + 8);
//                          RenderChunk renderChunk = this.viewFrustum.getRenderChunk(pos);
//                          RenderOverlay renderOverlay = this.viewFrustum.getRenderOverlay(pos);
//
//                        if (renderChunk != null && camera.isBoundingBoxInFrustum(renderChunk.boundingBox)) {
//                            renderChunk.setFrameIndex(frameCount);
//                            renderOverlay.setFrameIndex(frameCount);
//                            renderInfoList.add(new ContainerLocalRenderInformation(renderChunk, renderOverlay, null, 0));
//                        }
//                    }
//                }
//            } else {
//                boolean add = false;
//                  ContainerLocalRenderInformation renderInfo = new ContainerLocalRenderInformation(renderChunkCurrent, renderOverlayCurrent, null, 0);
//                  Set<EnumFacing> visibleSides = getVisibleSides(posEye);
//
//                if (visibleSides.size() == 1) {
//                      Vector3f viewVector = getViewVector(viewEntity, partialTicks);
//                      EnumFacing facing = EnumFacing.getFacingFromVector(viewVector.x, viewVector.y, viewVector.z).getOpposite();
//                    visibleSides.remove(facing);
//                }
//
//                if (visibleSides.isEmpty()) {
//                    add = true;
//                }
//
//                if (add && !playerSpectator) {
//                    this.renderInfos.add(renderInfo);
//                } else {
//                    if (playerSpectator && this.world.getBlockState(posEye).isOpaqueCube()) {
//                        renderChunksMany = false;
//                    }
//
//                    renderChunkCurrent.setFrameIndex(frameCount);
//                    renderOverlayCurrent.setFrameIndex(frameCount);
//                    renderInfoList.add(renderInfo);
//                }
//            }
//
//            this.profiler.startSection("iteration");
//            while (!renderInfoList.isEmpty()) {
//                  ContainerLocalRenderInformation renderInfo = renderInfoList.poll();
//                  RenderChunk renderChunk = renderInfo.renderChunk;
//                  EnumFacing facing = renderInfo.facing;
//                this.renderInfos.add(renderInfo);
//
//                for (  EnumFacing side : EnumFacing.VALUES) {
//                      RenderChunk neighborRenderChunk = getNeighborRenderChunk(posEye, renderChunk, side);
//                      RenderOverlay neighborRenderOverlay = getNeighborRenderOverlay(posEye, renderChunk, side);
//
//                    if ((!renderChunksMany || !renderInfo.setFacing.contains(side.getOpposite())) && (!renderChunksMany || facing == null || renderChunk.getCompiledChunk().isVisible(facing.getOpposite(), side)) && neighborRenderChunk != null && neighborRenderChunk.setFrameIndex(frameCount) && camera.isBoundingBoxInFrustum(neighborRenderChunk.boundingBox)) {
//                          ContainerLocalRenderInformation renderInfoNext = new ContainerLocalRenderInformation(neighborRenderChunk, neighborRenderOverlay, side, renderInfo.counter + 1);
//                        renderInfoNext.setFacing.addAll(renderInfo.setFacing);
//                        renderInfoNext.setFacing.add(side);
//                        renderInfoList.add(renderInfoNext);
//                    }
//                }
//            }
//            this.profiler.endSection();
//        }
//
//        this.profiler.endStartSection("rebuild");
//          Set<RenderChunk> set = this.chunksToUpdate;
//          Set<RenderOverlay> set1 = this.overlaysToUpdate;
//        this.chunksToUpdate = Sets.newLinkedHashSet();
//        this.overlaysToUpdate = Sets.newLinkedHashSet();
//
//        for (  ContainerLocalRenderInformation renderInfo : this.renderInfos) {
//              RenderChunk renderChunk = renderInfo.renderChunk;
//              RenderOverlay renderOverlay = renderInfo.renderOverlay;
//
//            if (renderChunk.needsUpdate() || set.contains(renderChunk)) {
//                this.displayListEntitiesDirty = true;
//
//                this.chunksToUpdate.add(renderChunk);
//            }
//
//            if (renderOverlay.needsUpdate() || set1.contains(renderOverlay)) {
//                this.displayListEntitiesDirty = true;
//
//                this.overlaysToUpdate.add(renderOverlay);
//            }
//        }
//
//        this.chunksToUpdate.addAll(set);
//        this.overlaysToUpdate.addAll(set1);
//        this.profiler.endSection();
//    }

    private boolean isInsideWorld(final double x, final double y, final double z) {
        return x >= -1 && y >= -1 && z >= -1 && x <= this.world.getWidth() && y <= this.world.getHeight() && z <= this.world.getLength();
    }
}
