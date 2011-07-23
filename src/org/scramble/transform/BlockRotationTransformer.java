/**
 * 
 */
package org.scramble.transform;

import java.awt.image.BufferedImage;

/**
 * @author alan
 *
 */
public class BlockRotationTransformer implements ImageTransformer {
	protected final int blockSize;
	
	/**
	 * @param blockSize Width and height of square sub-blocks
	 */
	public BlockRotationTransformer(int blockSize) {
		super();
		this.blockSize = blockSize;
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#transform(java.awt.image.BufferedImage)
	 */
	@Override
	public void transform(BufferedImage imageToChange) {
		int totalWidth = imageToChange.getWidth();
		int totalHeight = imageToChange.getHeight();
		int blockX = 0;
		int blockWidth = this.blockSize;
		int blockHeight = this.blockSize;

		//System.out.println("totalX=" + totalWidth + "totalY=" + totalHeight);
		
		
		// iterate over X and Y in blocks
		while(blockX < totalWidth) {
			if(blockX + this.blockSize > totalWidth) {
				blockWidth = totalWidth - blockX;
			} else {
				blockWidth = this.blockSize;
			}
			int blockY = 0;
			while(blockY < totalHeight) {
				if(blockY + this.blockSize > totalHeight) {
					blockHeight = totalHeight - blockY;
				} else {
					blockHeight = this.blockSize;
				}

				int[][] rgbBlock = copyToMatrix(imageToChange, blockX, blockY, blockWidth,
						blockHeight);
				// process block
				if(blockWidth == blockHeight) {
					//System.out.println("blockX=" + blockX + "blockY=" + blockY);

					// square, so rotate it
					for(int x = 0; x < blockWidth; x++) {
						for(int y = 0; y < blockHeight; y++) {
							int imageXTransformed = blockX + (blockWidth - y - 1);
							int imageYTransformed = blockY + x;
							
							imageToChange.setRGB(imageXTransformed, imageYTransformed, rgbBlock[x][y]);
						}	
					}

				} else {
					//System.out.println("blockX=" + blockX + "blockY=" + blockY);
					//System.out.println("blockW=" + blockWidth + "blockH=" + blockHeight);
					// not square, so flip over diagonal axis
					for(int x = 0; x < blockWidth; x++) {
						for(int y = 0; y < blockHeight; y++) {
							imageToChange.setRGB(blockX + (blockWidth - x - 1), blockY + (blockHeight - y - 1), rgbBlock[x][y]);
						}	
					}					
				}
				
				blockY += this.blockSize;
			}
			
			
			blockX += this.blockSize;
		}
	}

	// Copy the image data sub-block
	private int[][] copyToMatrix(BufferedImage imageToChange, int blockX,
			int blockY, int blockWidth, int blockHeight) {
		int[][] rgbBlock = new int[blockWidth][blockHeight];
		for(int x = blockX; x < blockX + blockWidth; x++) {
			for(int y = blockY; y < blockY + blockHeight; y++) {
				rgbBlock[x - blockX][y - blockY] = imageToChange.getRGB(x, y);
			}	
		}
		return rgbBlock;
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#untransform(java.awt.image.BufferedImage)
	 */
	@Override
	public void untransform(BufferedImage imageToChange) {
		int totalWidth = imageToChange.getWidth();
		int totalHeight = imageToChange.getHeight();
		int blockX = 0;
		int blockWidth = this.blockSize;
		int blockHeight = this.blockSize;
		
		// iterate over X and Y in blocks
		while(blockX < totalWidth) {
			if(blockX + this.blockSize > totalWidth) {
				blockWidth = totalWidth - blockX;
			} else {
				blockWidth = this.blockSize;
			}
			int blockY = 0;
			while(blockY < totalHeight) {
				if(blockY + this.blockSize > totalHeight) {
					blockHeight = totalHeight - blockY;
				} else {
					blockHeight = this.blockSize;
				}

				int[][] rgbBlock = copyToMatrix(imageToChange, blockX, blockY, blockWidth,
						blockHeight);

				// process block
				if(blockWidth == blockHeight) {
					// square, so rotate it
					for(int x = 0; x < blockWidth; x++) {
						for(int y = 0; y < blockHeight; y++) {
							imageToChange.setRGB(blockX + y, blockY + (blockWidth - x - 1), rgbBlock[x][y]);
						}	
					}
				} else {
					// not square, so flip over diagonal axis
					for(int x = 0; x < blockWidth; x++) {
						for(int y = 0; y < blockHeight; y++) {
							imageToChange.setRGB(blockX + (blockWidth - x - 1), blockY + (blockHeight - y - 1), rgbBlock[x][y]);
						}	
					}					
				}
				
				blockY += this.blockSize;
			}
			
			
			blockX += this.blockSize;
		}
	}

}
