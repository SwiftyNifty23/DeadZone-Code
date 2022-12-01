package com.deadrising.mod.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TRSRBakedModel implements IBakedModel {
  protected final IBakedModel original;
  
  protected final TRSRTransformation transformation;
  
  private final TRSROverride override;
  
  private final int faceOffset;
  
  private final Map<EnumFacing, List<BakedQuad>> cache;
  
  public TRSRBakedModel(IBakedModel original, float x, float y, float z, float scale) { this(original, x, y, z, 0.0F, 0.0F, 0.0F, scale, scale, scale); }
  
  public TRSRBakedModel(IBakedModel original, float x, float y, float z, float rotX, float rotY, float rotZ, float scale) { this(original, x, y, z, rotX, rotY, rotZ, scale, scale, scale); }
  
  public TRSRBakedModel(IBakedModel original, float x, float y, float z, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) { this(original, new TRSRTransformation(new Vector3f(x, y, z), null, new Vector3f(scaleX, scaleY, scaleZ),
          
          TRSRTransformation.quatFromXYZ(rotX, rotY, rotZ))); }
  
  public TRSRBakedModel(IBakedModel original, TRSRTransformation transform) {
    this.cache = new HashMap();
    this.original = original;
    this.transformation = TRSRTransformation.blockCenterToCorner(transform);
    this.override = new TRSROverride(this);
    this.faceOffset = 0;
  }
  
  public TRSRBakedModel(IBakedModel original, EnumFacing facing) {
    this.cache = new HashMap();
    this.original = original;
    this.override = new TRSROverride(this);
    this.faceOffset = 4 + EnumFacing.NORTH.getHorizontalIndex() - facing.getHorizontalIndex();
    double r = Math.PI * (360 - facing.getOpposite().getHorizontalIndex() * 90) / 180.0D;
    TRSRTransformation t = new TRSRTransformation(null, null, null, TRSRTransformation.quatFromXYZ(0.0F, (float)r, 0.0F));
    this.transformation = TRSRTransformation.blockCenterToCorner(t);
  }
  
  public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
    ImmutableList immutableList = (ImmutableList) this.cache.get(side);
    if (immutableList == null) {
      ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
      if (!this.original.isBuiltInRenderer()) {
        if (side != null && side.getHorizontalIndex() > -1)
          side = EnumFacing.getHorizontal((side.getHorizontalIndex() + this.faceOffset) % 4);
        for (BakedQuad quad : this.original.getQuads(state, side, rand)) {
          if (quad.getFormat() != null) {
            Transformer transformer = new Transformer(this.transformation, quad.getFormat());
            quad.pipe(transformer);
            builder.add(transformer.build());
          } 
        } 
      } 
      immutableList = builder.build();
      this.cache.put(side, immutableList);
    } 
    return immutableList;
  }
  
  public boolean isAmbientOcclusion() { return false; }
  
  public boolean isGui3d() { return this.original.isGui3d(); }
  
  public boolean isBuiltInRenderer() { return this.original.isBuiltInRenderer(); }
  
  public TextureAtlasSprite getParticleTexture() { return this.original.getParticleTexture(); }
  
  public ItemCameraTransforms getItemCameraTransforms() { return this.original.getItemCameraTransforms(); }
  
  public ItemOverrideList getOverrides() { return this.override; }
  
  private static class TRSROverride extends ItemOverrideList {
    private final TRSRBakedModel model;
    
    public TRSROverride(TRSRBakedModel model) {
      super(ImmutableList.of());
      this.model = model;
    }
    
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
      IBakedModel baked = this.model.original.getOverrides().handleItemState(originalModel, stack, world, entity);
      return new TRSRBakedModel(baked, this.model.transformation);
    }
  }
  
  private static class Transformer extends VertexTransformer {
    protected Matrix4f transformation;
    
    protected Matrix3f normalTransformation;
    
    public Transformer(TRSRTransformation transformation, VertexFormat format) {
      super(new UnpackedBakedQuad.Builder(format));
      this.transformation = transformation.getMatrix();
      this.normalTransformation = new Matrix3f();
      this.transformation.getRotationScale(this.normalTransformation);
      this.normalTransformation.invert();
      this.normalTransformation.transpose();
    }
    
    public void put(int element, float... data) {
      VertexFormatElement.EnumUsage usage = this.parent.getVertexFormat().getElement(element).getUsage();
      if (usage == VertexFormatElement.EnumUsage.POSITION && data.length >= 3) {
        Vector4f vec = new Vector4f(data[0], data[1], data[2], 1.0F);
        this.transformation.transform(vec);
        data = new float[4];
        vec.get(data);
      } else if (usage == VertexFormatElement.EnumUsage.NORMAL && data.length >= 3) {
        Vector3f vec = new Vector3f(data);
        this.normalTransformation.transform(vec);
        vec.normalize();
        data = new float[4];
        vec.get(data);
      } 
      super.put(element, data);
    }
    
    public UnpackedBakedQuad build() { return ((UnpackedBakedQuad.Builder)this.parent).build(); }
  }
}
