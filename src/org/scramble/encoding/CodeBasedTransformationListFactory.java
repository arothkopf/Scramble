/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.encoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.scramble.transform.BlockAddingTransformer;
import org.scramble.transform.BlockRotationTransformer;
import org.scramble.transform.ColorOnlyTransformer;
import org.scramble.transform.ColorSequenceBasedTransformer;
import org.scramble.transform.EvenColumnFlipTransformer;
import org.scramble.transform.EvenLineFlipTransformer;
import org.scramble.transform.HorizontalFlipTransformer;
import org.scramble.transform.ImageTransformer;
import org.scramble.transform.VerticalFlipTransformer;
import org.scramble.transform.YBasedWithSequenceTransformer;
import org.scramble.transform.data.RandomBlocks;
import org.scramble.transform.data.ShapedBlocks;
import org.scramble.util.color.ColorTransformer;
import org.scramble.util.color.InvertColorTransformer;
import org.scramble.util.color.ProgressiveColorTransformer;
import org.scramble.util.color.SwapGreenBlueTransformer;
import org.scramble.util.color.SwapRedBlueTransformer;
import org.scramble.util.color.SwapRedGreenTransformer;


/**
 * Factory that maps a code to an ordered array of ImageTransformers
 * @author alan
 *
 */
public class CodeBasedTransformationListFactory {
	
	byte[] codeToBytes(String code) {
	    MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
		    m.update(code.getBytes(),0,code.length());
		    byte[] digest = m.digest();
			//System.out.println("MD5: "+new BigInteger(1,digest).toString(16));
		    return digest;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Convert bytes to ints between 0 and 255
	 * @param bytes
	 * @return
	 */
	int[] bytesToInts0to255(byte[] bytes) {
		int[] ints = new int[bytes.length];
		for(int i=0; i < ints.length; i++) {
			ints[i] = (bytes[i] + 100) & 0xff;
		}
		return ints;
	}
	
	/**
	 * Build a ProgressiveColorTransformer instance
	 * @param magnitude
	 * @param stepRed
	 * @param stepGreen
	 * @param stepBlue
	 * @return
	 */
	protected ImageTransformer makeProgressiveColorTransformer(int magnitude, int stepRed, int stepGreen, int stepBlue) {
		ProgressiveColorTransformer trans = new ProgressiveColorTransformer();
		trans.setMagnitudes(magnitude);
		trans.setStepRed(stepRed);
		trans.setStepGreen(stepGreen);
		trans.setStepBlue(stepBlue);
		
		return new ColorOnlyTransformer(trans);
	}
	
	/**
	 * Get the list of transformations to execute
	 * @param password
	 * @return
	 */
	public ImageTransformer[] getTransformersFromPassword(String password) {
		return this.codeToTransformerList(this.codeToBytes(password));
	}
	
	ImageTransformer[] codeToTransformerList(byte[] codeBytes) {
		int[] codeInts = this.bytesToInts0to255(codeBytes);
		
		ArrayList<ImageTransformer> list = new ArrayList<ImageTransformer>();
		list.add(this.makeProgressiveColorTransformer(codeInts[0], codeInts[1], codeInts[2], codeInts[3]));
		Set<String> flags = new TreeSet<String>();
		
		for(int i=4; i < codeInts.length; i++) {
			int curByte = codeInts[i];
			int typeSelector = curByte % 8;
			int configValue = curByte / 8; // from 0 to 31
			/**
			 * For each byte, choice of 8 types of transformer with 32 different configs (with a few exceptions)
			 */
			addTransformersForBytes(list, flags, typeSelector, configValue);
		}
		
		return list.toArray(new ImageTransformer[0]);  
		
	}

	/**
	 * @param list
	 * @param flags
	 * @param typeSelector
	 * @param configValue
	 */
	private void addTransformersForBytes(ArrayList<ImageTransformer> list,
			Set<String> flags, int typeSelector, int configValue) {
		switch(typeSelector) {
			case 0:
				// BlockRotationTransformer - 32 different block sizes (from 2 to 64)
				list.add(new BlockRotationTransformer(2 * (configValue + 1)));						
				break;
			case 1:
				// YBasedWithSequenceTransformer - 32 different int arrays
				list.add(new YBasedWithSequenceTransformer(this.chooseYBSTransformerSequence(configValue), false));						
				break;
			case 2:
				// YBasedWithSequenceTransformer (axis flipped) - 32 different int arrays
				list.add(new YBasedWithSequenceTransformer(this.chooseYBSTransformerSequence(configValue), true));						
				break;
				
			case 3:
				//  BlockAddingTransformer - 32 different block combinations
				list.add(new BlockAddingTransformer(this.chooseRedBlock(configValue), this.chooseGreenBlock(configValue), this.chooseBlueBlock(configValue)));
				break;
			case 4:
				// ColorSequenceBasedTransformer - 32 different block combinations
				list.add(new ColorSequenceBasedTransformer(this.chooseColorTransformerSequence(configValue)));
				break;
				
			case 5:
				// ColorSequenceBasedTransformer - 32 more block combinations
				list.add(new ColorSequenceBasedTransformer(this.chooseColorTransformerSequence(configValue + 32)));					
				break;
			case 6:
				// complex choice - 2 transformers to add
				this.chooseTransformersFor6(list, configValue, flags);
				break;
				
			case 7:
			default:
				// YBasedWithSequenceTransformer - 32 more different int arrays
				list.add(new YBasedWithSequenceTransformer(this.chooseYBSTransformerSequence(configValue + 32), true));											
				break;
		}
	}
	
	// add up to 2 different transformers for 1 
	protected void chooseTransformersFor6(List<ImageTransformer> targetList, int configValue, Set<String> flags) {
		int part1 = configValue % 4;
		int part2 = (configValue / 4)%8;
		
		// select 1 of these 4
//		EvenLineFlipTransformer- no config, should only be used once max
//		EvenColumnFlipTransformer - no config, should only be used once max
//		HorizontalFlipTransformer- no config, should only be used once max
//		VerticalFlipTransformer- no config, should only be used once max;
		switch(part1) {
			case 0:
				if(!flags.contains("EvenLineFlip")) {
					targetList.add(new EvenLineFlipTransformer());
					flags.add("EvenLineFlip");
				} else {
					part2 += 8 * part1;
				}
				break;
			case 1:
				if(!flags.contains("EvenColFlip")) {
					targetList.add(new EvenColumnFlipTransformer());
					flags.add("EvenColFlip");
				} else {
					part2 += 8 * part1;
				}
				break;
				
			case 2:
				if(!flags.contains("HFlip")) {
					targetList.add(new HorizontalFlipTransformer());
					flags.add("HFlip");
				} else {
					part2 += 8 * part1;
				}
				break;
				
			default:
				if(!flags.contains("VFlip")) {
					targetList.add(new VerticalFlipTransformer());
					flags.add("VFlip");
				} else {
					part2 += 8 * part1;
				}
				break;
		}

		// BlockRotationTransformer
		targetList.add(new BlockRotationTransformer(130 + part2));								
	}

	private static final int[][][][] COLOR_BLOCK_COMBOS = {
		{ShapedBlocks.WAVE_BLOCK_32, ShapedBlocks.WAVE_BLOCK_32, ShapedBlocks.WAVE_BLOCK_32},
		{ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.UPPER_LEFT_BLOCK_8},
		{ShapedBlocks.UPPER_LEFT_BLOCK_64,ShapedBlocks.UPPER_LEFT_BLOCK_64,ShapedBlocks.UPPER_LEFT_BLOCK_64},
		{ShapedBlocks.EVEN_ODD_BLOCK_16,ShapedBlocks.EVEN_ODD_BLOCK_16,ShapedBlocks.EVEN_ODD_BLOCK_16},
		{RandomBlocks.SEMI_RANDOM_BLOCK_4_32,RandomBlocks.SEMI_RANDOM_BLOCK_4_32,RandomBlocks.SEMI_RANDOM_BLOCK_4_32},
		{RandomBlocks.RANDOM_BLOCK_1_64,RandomBlocks.RANDOM_BLOCK_1_64,RandomBlocks.RANDOM_BLOCK_1_64},
		{RandomBlocks.RANDOM_BLOCK_2_16,RandomBlocks.RANDOM_BLOCK_2_16,RandomBlocks.RANDOM_BLOCK_2_16},
		{RandomBlocks.RANDOM_BLOCK_3_32,RandomBlocks.RANDOM_BLOCK_3_32,RandomBlocks.RANDOM_BLOCK_3_32},
		{ShapedBlocks.WAVE_BLOCK_32,RandomBlocks.SEMI_RANDOM_BLOCK_4_32, RandomBlocks.SEMI_RANDOM_BLOCK_4_32},
		{ShapedBlocks.WAVE_BLOCK_32,RandomBlocks.SEMI_RANDOM_BLOCK_4_32,ShapedBlocks.UPPER_LEFT_BLOCK_8},
		{ShapedBlocks.WAVE_BLOCK_32,ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.EVEN_ODD_BLOCK_16},
		{ShapedBlocks.WAVE_BLOCK_32,ShapedBlocks.UPPER_LEFT_BLOCK_8,RandomBlocks.RANDOM_BLOCK_2_16},
		{RandomBlocks.SEMI_RANDOM_BLOCK_4_32,ShapedBlocks.EVEN_ODD_BLOCK_16, ShapedBlocks.EVEN_ODD_BLOCK_16},
		{RandomBlocks.SEMI_RANDOM_BLOCK_4_32,ShapedBlocks.EVEN_ODD_BLOCK_16,RandomBlocks.RANDOM_BLOCK_2_16},
		{RandomBlocks.SEMI_RANDOM_BLOCK_4_32,RandomBlocks.RANDOM_BLOCK_2_16,ShapedBlocks.UPPER_LEFT_BLOCK_64},
		{RandomBlocks.SEMI_RANDOM_BLOCK_4_32,ShapedBlocks.UPPER_LEFT_BLOCK_64,ShapedBlocks.UPPER_LEFT_BLOCK_64},
		{ShapedBlocks.UPPER_LEFT_BLOCK_64,RandomBlocks.RANDOM_BLOCK_1_64,RandomBlocks.RANDOM_BLOCK_1_64},
		{ShapedBlocks.UPPER_LEFT_BLOCK_64,RandomBlocks.RANDOM_BLOCK_1_64,ShapedBlocks.WAVE_BLOCK_32},
		{ShapedBlocks.UPPER_LEFT_BLOCK_64,ShapedBlocks.WAVE_BLOCK_32,ShapedBlocks.WAVE_BLOCK_32},
		{ShapedBlocks.UPPER_LEFT_BLOCK_64,ShapedBlocks.WAVE_BLOCK_32,ShapedBlocks.UPPER_LEFT_BLOCK_8},
		{RandomBlocks.RANDOM_BLOCK_1_64,RandomBlocks.SEMI_RANDOM_BLOCK_4_32,RandomBlocks.SEMI_RANDOM_BLOCK_4_32},
		{RandomBlocks.RANDOM_BLOCK_1_64,RandomBlocks.SEMI_RANDOM_BLOCK_4_32,RandomBlocks.RANDOM_BLOCK_2_16},
		{RandomBlocks.RANDOM_BLOCK_1_64,RandomBlocks.RANDOM_BLOCK_2_16,ShapedBlocks.EVEN_ODD_BLOCK_16},
		{RandomBlocks.RANDOM_BLOCK_1_64,RandomBlocks.RANDOM_BLOCK_2_16,ShapedBlocks.UPPER_LEFT_BLOCK_8},
		{ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.EVEN_ODD_BLOCK_16,ShapedBlocks.EVEN_ODD_BLOCK_16},
		{ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.EVEN_ODD_BLOCK_16,ShapedBlocks.UPPER_LEFT_BLOCK_8},
		{ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.UPPER_LEFT_BLOCK_8,RandomBlocks.RANDOM_BLOCK_3_32},
		{ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.EVEN_ODD_BLOCK_16},
		{ShapedBlocks.EVEN_ODD_BLOCK_16,ShapedBlocks.UPPER_LEFT_BLOCK_8,ShapedBlocks.UPPER_LEFT_BLOCK_8},
		{ShapedBlocks.EVEN_ODD_BLOCK_16,ShapedBlocks.UPPER_LEFT_BLOCK_8,RandomBlocks.RANDOM_BLOCK_3_32},
		{ShapedBlocks.EVEN_ODD_BLOCK_16,RandomBlocks.RANDOM_BLOCK_3_32,ShapedBlocks.EVEN_ODD_BLOCK_16},
		{ShapedBlocks.EVEN_ODD_BLOCK_16,ShapedBlocks.WAVE_BLOCK_32,ShapedBlocks.WAVE_BLOCK_32}};
	
	// decide which block to use for red in BlockAddingTransformer:
	private int[][] chooseRedBlock(int configValue) {		
		return COLOR_BLOCK_COMBOS[configValue%32][0];
	}

	// decide which block to use for red in BlockAddingTransformer:
	private int[][] chooseGreenBlock(int configValue) {		
		return COLOR_BLOCK_COMBOS[configValue%32][1];
	}

	// decide which block to use for red in BlockAddingTransformer:
	private int[][] chooseBlueBlock(int configValue) {		
		return COLOR_BLOCK_COMBOS[configValue%32][2];
	}

	
	
	
	private static final int[][] YBST_SEQUENCES = {
		YBasedWithSequenceTransformer.EXPONENTS,
		YBasedWithSequenceTransformer.FIBONACCIS, 
		YBasedWithSequenceTransformer.WAVE,
		YBasedWithSequenceTransformer.HANDMADE1,
		YBasedWithSequenceTransformer.HANDMADE2,
		YBasedWithSequenceTransformer.RANDOM_SEQ1,
		YBasedWithSequenceTransformer.RANDOM_SEQ2,
		YBasedWithSequenceTransformer.RANDOM_SEQ3,
		YBasedWithSequenceTransformer.RANDOM_SEQ4,
		YBasedWithSequenceTransformer.RANDOM_SEQ5,
		YBasedWithSequenceTransformer.RANDOM_SEQ6,
		YBasedWithSequenceTransformer.RANDOM_SEQ7,
		YBasedWithSequenceTransformer.RANDOM_SEQ8,
		YBasedWithSequenceTransformer.RANDOM_SEQ9,
		YBasedWithSequenceTransformer.RANDOM_SEQ10,
		YBasedWithSequenceTransformer.RANDOM_SEQ11,
		YBasedWithSequenceTransformer.RANDOM_SEQ12,
		YBasedWithSequenceTransformer.RANDOM_SEQ13,
		YBasedWithSequenceTransformer.RANDOM_SEQ14,
		YBasedWithSequenceTransformer.RANDOM_SEQ15,
		YBasedWithSequenceTransformer.RANDOM_SEQ16,
		YBasedWithSequenceTransformer.RANDOM_SEQ17,
		YBasedWithSequenceTransformer.RANDOM_SEQ18,
		YBasedWithSequenceTransformer.RANDOM_SEQ19,
		YBasedWithSequenceTransformer.RANDOM_SEQ20,
		YBasedWithSequenceTransformer.RANDOM_SEQ21,
		YBasedWithSequenceTransformer.RANDOM_SEQ22,
		YBasedWithSequenceTransformer.RANDOM_SEQ23,
		YBasedWithSequenceTransformer.RANDOM_SEQ24,
		YBasedWithSequenceTransformer.RANDOM_SEQ25,
		YBasedWithSequenceTransformer.RANDOM_SEQ26,
		YBasedWithSequenceTransformer.RANDOM_SEQ27,
		YBasedWithSequenceTransformer.RANDOM_SEQ28,
		YBasedWithSequenceTransformer.RANDOM_SEQ29,
		YBasedWithSequenceTransformer.RANDOM_SEQ30,
		YBasedWithSequenceTransformer.RANDOM_SEQ31,
		YBasedWithSequenceTransformer.RANDOM_SEQ32,
		YBasedWithSequenceTransformer.RANDOM_SEQ33,
		YBasedWithSequenceTransformer.RANDOM_SEQ34,
		YBasedWithSequenceTransformer.RANDOM_SEQ35,
		YBasedWithSequenceTransformer.RANDOM_SEQ36,
		YBasedWithSequenceTransformer.RANDOM_SEQ37,
		YBasedWithSequenceTransformer.RANDOM_SEQ38,
		YBasedWithSequenceTransformer.RANDOM_SEQ39,
		YBasedWithSequenceTransformer.RANDOM_SEQ40,
		YBasedWithSequenceTransformer.RANDOM_SEQ41,
		YBasedWithSequenceTransformer.RANDOM_SEQ42,
		YBasedWithSequenceTransformer.RANDOM_SEQ43,
		YBasedWithSequenceTransformer.RANDOM_SEQ44,
		YBasedWithSequenceTransformer.RANDOM_SEQ45,
		YBasedWithSequenceTransformer.RANDOM_SEQ46,
		YBasedWithSequenceTransformer.RANDOM_SEQ47,
		YBasedWithSequenceTransformer.RANDOM_SEQ48,
		YBasedWithSequenceTransformer.RANDOM_SEQ49,
		YBasedWithSequenceTransformer.RANDOM_SEQ50,
		YBasedWithSequenceTransformer.RANDOM_SEQ51,
		YBasedWithSequenceTransformer.RANDOM_SEQ52,
		YBasedWithSequenceTransformer.RANDOM_SEQ53,
		YBasedWithSequenceTransformer.RANDOM_SEQ54,
		YBasedWithSequenceTransformer.RANDOM_SEQ55,
		YBasedWithSequenceTransformer.RANDOM_SEQ56,
		YBasedWithSequenceTransformer.RANDOM_SEQ57,
		YBasedWithSequenceTransformer.RANDOM_SEQ58,
		YBasedWithSequenceTransformer.RANDOM_SEQ59,
		YBasedWithSequenceTransformer.RANDOM_SEQ60
	};
	
	protected int[] chooseYBSTransformerSequence(final int configValue) {
		return YBST_SEQUENCES[configValue % 64];
	}
	
	static final ColorTransformer[][] COLOR_TRANS_SEQUENCES = {
		{new InvertColorTransformer()},
		{new SwapRedGreenTransformer()},
		{new SwapGreenBlueTransformer()},
		{new SwapRedBlueTransformer()},
		{new InvertColorTransformer(), new SwapRedGreenTransformer()},
		{new InvertColorTransformer(), new SwapGreenBlueTransformer()},
		{new InvertColorTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedGreenTransformer(), new InvertColorTransformer()},
		{new SwapRedGreenTransformer(), new SwapGreenBlueTransformer()},
		{new SwapRedGreenTransformer(), new SwapRedBlueTransformer()},
		{new SwapGreenBlueTransformer(), new InvertColorTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedBlueTransformer(), new InvertColorTransformer()},
		{new SwapRedBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapRedBlueTransformer(), new SwapGreenBlueTransformer()},
		{new InvertColorTransformer(), new InvertColorTransformer(), new SwapRedGreenTransformer()},
		{new InvertColorTransformer(), new InvertColorTransformer(), new SwapGreenBlueTransformer()},
		{new InvertColorTransformer(), new InvertColorTransformer(), new SwapRedBlueTransformer()},
		{new InvertColorTransformer(), new SwapRedGreenTransformer(), new InvertColorTransformer()},
		{new InvertColorTransformer(), new SwapRedGreenTransformer(), new SwapGreenBlueTransformer()},
		{new InvertColorTransformer(), new SwapRedGreenTransformer(), new SwapRedBlueTransformer()},
		{new InvertColorTransformer(), new SwapGreenBlueTransformer(), new InvertColorTransformer()},
		{new InvertColorTransformer(), new SwapGreenBlueTransformer(), new SwapRedGreenTransformer()},
		{new InvertColorTransformer(), new SwapGreenBlueTransformer(), new SwapRedBlueTransformer()},
		{new InvertColorTransformer(), new SwapRedBlueTransformer(), new InvertColorTransformer()},
		{new InvertColorTransformer(), new SwapRedBlueTransformer(), new SwapRedGreenTransformer()},
		{new InvertColorTransformer(), new SwapRedBlueTransformer(), new SwapGreenBlueTransformer()},
		{new SwapRedGreenTransformer(), new InvertColorTransformer(), new SwapRedGreenTransformer()},
		{new SwapRedGreenTransformer(), new InvertColorTransformer(), new SwapGreenBlueTransformer()},
		{new SwapRedGreenTransformer(), new InvertColorTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedGreenTransformer(), new SwapRedGreenTransformer(), new InvertColorTransformer()},
		{new SwapRedGreenTransformer(), new SwapRedGreenTransformer(), new SwapGreenBlueTransformer()},
		{new SwapRedGreenTransformer(), new SwapRedGreenTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedGreenTransformer(), new SwapGreenBlueTransformer(), new InvertColorTransformer()},
		{new SwapRedGreenTransformer(), new SwapGreenBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapRedGreenTransformer(), new SwapGreenBlueTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedGreenTransformer(), new SwapRedBlueTransformer(), new InvertColorTransformer()},
		{new SwapRedGreenTransformer(), new SwapRedBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapRedGreenTransformer(), new SwapRedBlueTransformer(), new SwapGreenBlueTransformer()},
		{new SwapGreenBlueTransformer(), new InvertColorTransformer(), new SwapRedGreenTransformer()},
		{new SwapGreenBlueTransformer(), new InvertColorTransformer(), new SwapGreenBlueTransformer()},
		{new SwapGreenBlueTransformer(), new InvertColorTransformer(), new SwapRedBlueTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedGreenTransformer(), new InvertColorTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedGreenTransformer(), new SwapGreenBlueTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedGreenTransformer(), new SwapRedBlueTransformer()},
		{new SwapGreenBlueTransformer(), new SwapGreenBlueTransformer(), new InvertColorTransformer()},
		{new SwapGreenBlueTransformer(), new SwapGreenBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapGreenBlueTransformer(), new SwapGreenBlueTransformer(), new SwapRedBlueTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedBlueTransformer(), new InvertColorTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapGreenBlueTransformer(), new SwapRedBlueTransformer(), new SwapGreenBlueTransformer()},
		{new SwapRedBlueTransformer(), new InvertColorTransformer(), new SwapRedGreenTransformer()},
		{new SwapRedBlueTransformer(), new InvertColorTransformer(), new SwapGreenBlueTransformer()},
		{new SwapRedBlueTransformer(), new InvertColorTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedBlueTransformer(), new SwapRedGreenTransformer(), new InvertColorTransformer()},
		{new SwapRedBlueTransformer(), new SwapRedGreenTransformer(), new SwapGreenBlueTransformer()},
		{new SwapRedBlueTransformer(), new SwapRedGreenTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedBlueTransformer(), new SwapGreenBlueTransformer(), new InvertColorTransformer()},
		{new SwapRedBlueTransformer(), new SwapGreenBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapRedBlueTransformer(), new SwapGreenBlueTransformer(), new SwapRedBlueTransformer()},
		{new SwapRedBlueTransformer(), new SwapRedBlueTransformer(), new InvertColorTransformer()},
		{new SwapRedBlueTransformer(), new SwapRedBlueTransformer(), new SwapRedGreenTransformer()},
		{new SwapRedBlueTransformer(), new SwapRedBlueTransformer(), new SwapGreenBlueTransformer()},
		};
	
	protected ColorTransformer[] chooseColorTransformerSequence(int configValueUpTo64) {
		return COLOR_TRANS_SEQUENCES[configValueUpTo64 % 64];  
	}
}
