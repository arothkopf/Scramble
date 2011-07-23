/**
 * 
 */
package org.scramble.transform;

import java.util.Random;

/**
 * YBasedDisplaceTransformer based on a sequence of numbers (e.g. Fibonacci)
 * 
 * @author alan
 * 
 */
public class YBasedWithSequenceTransformer extends YBasedDisplaceTransformer {
	public static final int[] FIBONACCIS = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55,
			89, 144, 233 };
	public static final int[] EXPONENTS = { 2, 4, 8, 16, 32, 64, 128, 256, 512,
			1024 };
	public static final int[] WAVE = { 10, 20, 40, 80, 160, 320, 640, 320, 160,
			80, 40, 20 };
	public static final int[] HANDMADE1 = { 12, 100, 33, 222, 0, 77, 156, 66,
			33, 88, 176, 17, 207, 77, 44, 123 };
	public static final int[] HANDMADE2 = { 7, 44, 33, 65, 88, 2, 2, 77, 111,
			222, 111, 123, 11, 90 };
	public static final int[] RANDOM_SEQ1 = { 236, 250, 50, 109, 113, 217, 73,
			155, 27, 240, 116, 99, 168, 229, 219, 133, 61, 73, 194, 151, 173,
			243, 218, 64, 66, 204, 111, 141, 21 };
	public static final int[] RANDOM_SEQ2 = { 157, 251, 151, 237, 81, 6, 170,
			248, 12, 161, 9, 2, 54, 101, 14, 168, 244, 9, 241, 63, 160, 87, 92,
			1, 215, 4, 117, 212, 195, 137, 229, 85, 64 };
	public static final int[] RANDOM_SEQ3 = { 111, 241, 81, 47, 149, 103, 184,
			51, 69, 123, 87, 144, 89, 230, 201, 233, 70, 141, 65, 224, 68, 246,
			100, 82, 45, 9, 208, 65, 83, 175, 11, 158, 238, 70, 85 };
	public static final int[] RANDOM_SEQ4 = { 134, 96, 204, 6, 9, 115, 206, 79,
			48, 71, 211, 65, 97, 91, 147, 93, 71 };
	public static final int[] RANDOM_SEQ5 = { 19, 88, 160, 195, 175, 51, 219,
			127, 77, 27 };
	public static final int[] RANDOM_SEQ6 = { 151, 182, 113, 95, 89, 9, 203,
			135, 163, 109, 7, 69, 142, 163, 23, 84, 209, 19, 166, 100, 146,
			191, 232, 92, 97, 167, 10, 251 };
	public static final int[] RANDOM_SEQ7 = { 115, 44, 119, 80, 105, 246, 82,
			59, 121, 65, 207, 19, 159, 34, 98, 17, 80, 55, 101, 233, 201 };
	public static final int[] RANDOM_SEQ8 = { 173, 120, 240, 250, 167, 7, 197,
			221, 251, 40, 171, 30, 17, 212, 68, 178, 42, 18, 51, 238, 65, 215 };
	public static final int[] RANDOM_SEQ9 = { 7, 243, 54, 141, 57, 173, 172, 8,
			69, 53, 34, 45, 59, 113, 179, 236, 82, 35, 77, 214, 93, 188, 203,
			88, 212, 141, 25, 157, 112, 8, 47, 107, 30, 251 };
	public static final int[] RANDOM_SEQ10 = { 99, 51, 61, 178, 77, 182, 134,
			9, 1, 207, 53, 74, 197, 27, 69, 138, 106, 92, 40, 15, 219, 213,
			115, 102, 210, 164 };
	public static final int[] RANDOM_SEQ11 = { 40, 230, 208, 112, 45, 249, 195,
			19, 169, 46, 226, 58, 27 };
	public static final int[] RANDOM_SEQ12 = { 218, 123, 43, 184, 21, 245, 123,
			223, 248, 150, 137, 41 };
	public static final int[] RANDOM_SEQ13 = { 228, 123, 162, 203, 138, 251,
			16, 132, 9, 39, 171, 143, 214 };
	public static final int[] RANDOM_SEQ14 = { 149, 137, 95, 94, 127, 141, 74,
			59, 146, 39, 164, 231, 173, 73, 68, 219, 81, 231, 72, 255 };
	public static final int[] RANDOM_SEQ15 = { 43, 140, 144, 28, 94, 130, 36,
			12, 229, 68 };
	public static final int[] RANDOM_SEQ16 = { 119, 144, 143, 27, 192, 179, 1,
			245, 47, 254, 244, 88, 78, 153, 171, 20, 172, 43 };
	public static final int[] RANDOM_SEQ17 = { 96, 131, 111, 215, 15, 18, 109,
			122, 243, 182, 79, 149, 133, 206, 54, 99, 197, 180, 178, 102, 58,
			127, 227, 227, 128, 201, 105, 148, 248, 27, 66, 27, 0, 92, 24 };
	public static final int[] RANDOM_SEQ18 = { 176, 7, 190, 37, 21, 219, 239,
			1, 39, 145, 209, 208, 251, 101, 135, 67, 109, 161, 206, 29, 7, 226,
			153, 58, 97, 241, 56, 249, 56, 186, 126, 236, 212, 217, 139, 51,
			22, 254, 75 };
	public static final int[] RANDOM_SEQ19 = { 195, 185, 198, 114, 128, 218,
			181, 94, 153, 66, 169, 62, 148, 198, 133, 248, 218, 216 };
	public static final int[] RANDOM_SEQ20 = { 197, 162, 241, 230, 38, 166,
			187, 99, 72, 26 };
	public static final int[] RANDOM_SEQ21 = { 28, 246, 217, 61, 96, 54, 225,
			156, 22, 129, 1, 152, 69, 198, 48, 92, 187, 36, 31, 72, 135, 55,
			204, 44, 93, 116, 18, 89, 173, 48, 180, 135, 197, 22, 213, 22 };
	public static final int[] RANDOM_SEQ22 = { 32, 49, 206, 255, 141, 13, 229,
			59, 255, 99, 228, 64, 31, 219, 104, 43, 249, 39, 128, 255, 16, 44,
			92, 47, 124 };
	public static final int[] RANDOM_SEQ23 = { 40, 212, 226, 167, 59, 66, 188,
			241, 37, 224, 4, 151, 156, 221, 208, 11, 124, 33, 250, 14, 64, 87,
			237, 221, 116, 228, 49, 128, 187, 201, 28, 166, 167, 4 };
	public static final int[] RANDOM_SEQ24 = { 116, 91, 89, 26, 16, 253, 114,
			28, 21, 75, 148, 95, 210, 246, 136, 163, 76, 215, 140, 95, 49, 144,
			221, 14, 26, 12, 72, 80, 133, 133, 34 };
	public static final int[] RANDOM_SEQ25 = { 105, 181, 21, 230, 140, 191,
			100, 13, 108, 215, 217, 153, 181, 254, 91, 83, 238, 54, 23, 37, 43,
			162, 213, 31, 201, 85, 107, 217, 173, 228, 208, 185, 94, 37 };
	public static final int[] RANDOM_SEQ26 = { 4, 219, 121, 72, 15, 195, 240,
			140, 98, 52, 233 };
	public static final int[] RANDOM_SEQ27 = { 66, 148, 156, 226, 83, 37, 156,
			239, 224, 182, 23, 247, 147, 148 };
	public static final int[] RANDOM_SEQ28 = {13, 106, 213, 212, 231, 120, 85, 210, 163, 166, 56, 0, 18, 67, 175, 82, 218, 88, 130, 106, 123, 63
		, 138, 42, 240, 69, 9, 74, 119, 59, 119, 167, 161, 95, 168, 87, 50, 187, 173};
		public static final int[] RANDOM_SEQ29 = {60, 38, 173, 173, 154, 133, 75, 61, 63, 188, 132, 176, 151, 36, 138, 199, 138, 70, 234, 197, 173, 
		152};
		public static final int[] RANDOM_SEQ30 = {128, 43, 222, 82, 159, 142, 29, 44, 46, 157, 85, 196, 225, 92, 110, 7, 193, 69, 72, 129};
		public static final int[] RANDOM_SEQ31 = {177, 162, 130, 25, 247, 228, 128, 29, 207, 96, 7, 248, 144, 218, 237, 251, 152, 182, 55, 119, 220}
		;
		public static final int[] RANDOM_SEQ32 = {92, 95, 199, 71, 241, 42, 79, 211, 70, 218, 107, 3, 228, 175, 178, 0, 11, 199, 102, 148, 219, 76, 
		186, 27, 79};
		public static final int[] RANDOM_SEQ33 = {75, 54, 49, 128, 17, 65, 137, 116, 99, 88};
		public static final int[] RANDOM_SEQ34 = {118, 119, 69, 79, 142, 92, 141, 254, 151, 168, 40, 145, 54, 127, 212, 65, 2, 196, 152, 95, 47, 79,
		 15, 183, 156, 86, 191, 99, 104, 187, 23, 216, 101, 46, 69, 104, 65};
		public static final int[] RANDOM_SEQ35 = {138, 47, 235, 227, 51, 23, 209, 130, 225, 29, 163, 3, 117, 10, 198, 51, 63, 254, 155, 63, 168, 111
		, 192, 188, 189, 198, 19, 133, 13};
		public static final int[] RANDOM_SEQ36 = {253, 70, 192, 195, 92, 196, 39, 21, 253, 216, 217, 153, 74, 27, 181, 76, 104, 152, 16, 185, 98, 150, 193};
		public static final int[] RANDOM_SEQ37 = {250, 202, 84, 155, 83, 120, 211, 59, 200, 110, 7, 22, 185, 74, 110, 144};
		public static final int[] RANDOM_SEQ38 = {24, 250, 118, 196, 36, 112, 59, 29, 234, 59, 133, 228, 107, 52, 174, 62, 194, 146, 177};
		public static final int[] RANDOM_SEQ39 = {238, 209, 167, 86, 210, 252, 129, 50, 11, 38, 178, 9, 230, 82, 72, 172, 203, 195, 154, 17, 78, 136
		, 14, 230, 218, 25, 203, 132, 248, 49, 119, 16, 48};
		public static final int[] RANDOM_SEQ40 = {239, 84, 30, 111, 90, 91, 38, 163, 161, 50, 215, 27, 43, 239, 153, 246, 191, 227, 47};
		public static final int[] RANDOM_SEQ41 = {4, 229, 49, 19, 250, 58, 111, 231, 242, 131, 5, 80, 21, 43, 213, 0, 5, 41, 60, 0, 134, 10, 133, 147, 117, 228, 209, 242, 248, 4, 35, 247, 10, 252};
		public static final int[] RANDOM_SEQ42 = {241, 201, 7, 64, 147, 50, 148, 139, 67, 115, 47, 236, 145, 187, 186, 183};
		public static final int[] RANDOM_SEQ43 = {119, 179, 143, 160, 63, 156, 140, 151, 57, 87, 195, 204, 166, 0, 175, 183, 54, 23, 126, 132, 241, 
		203, 8, 180, 96, 191, 5, 116, 14};
		public static final int[] RANDOM_SEQ44 = {231, 91, 46, 61, 90, 55, 36, 177, 176, 110, 87, 25, 89, 129, 78, 96, 55, 158, 37, 233, 79, 54, 41,
		 156, 167, 164, 120, 143, 102, 199, 249, 105, 195, 57, 120, 206, 80, 22, 12};
		public static final int[] RANDOM_SEQ45 = {85, 167, 143, 186, 154, 32, 195, 173, 55, 222, 255, 242, 189, 16, 139, 129, 57, 125, 197, 89, 160,
		 32, 218, 129, 96, 169, 213, 247, 40, 113, 47, 212};
		public static final int[] RANDOM_SEQ46 = {34, 185, 216, 153, 160, 127, 145, 200, 81, 121, 96, 156, 250, 74};
		public static final int[] RANDOM_SEQ47 = {167, 33, 238, 241, 26, 201, 45, 178, 7, 55, 223, 245, 165, 132, 53, 210, 128, 113, 87, 181, 40, 123, 147, 45, 222, 183, 33, 233, 34, 156, 0};
		public static final int[] RANDOM_SEQ48 = {215, 74, 36, 50, 61, 60, 169, 163, 47, 85, 49, 155, 193, 37, 82, 243, 55, 150, 105, 232, 186, 40, 
		93, 144, 35, 228, 207, 174, 63};
		public static final int[] RANDOM_SEQ49 = {239, 63, 179, 122, 155, 20, 145, 126, 10, 203, 42, 59, 204, 3, 18, 210, 65, 254, 50, 121, 32, 182}
		;
		public static final int[] RANDOM_SEQ50 = {94, 87, 191, 82, 73, 165, 186, 166, 200, 145, 24};
		public static final int[] RANDOM_SEQ51 = {164, 99, 142, 229, 225, 187, 162, 46, 224, 0, 204, 48, 203, 105, 228, 162, 134, 9, 26, 54, 41, 88,
		 93, 152, 252, 98, 237, 36, 29, 216, 166, 243, 110};
		public static final int[] RANDOM_SEQ52 = {152, 90, 93, 162, 161, 16, 110, 111, 55, 181, 88, 168, 32, 255, 168, 233, 247, 40, 183, 78, 93, 249, 234, 146, 193, 212, 75, 131};
		public static final int[] RANDOM_SEQ53 = {165, 15, 230, 43, 122, 242, 81, 63, 203, 202, 87, 19, 201, 193, 212};
		public static final int[] RANDOM_SEQ54 = {56, 183, 165, 125, 137, 146, 31, 184, 24, 71, 91, 205, 178, 135, 199, 249, 11, 190, 133, 194, 2, 108, 143, 11, 235, 2, 110, 47, 178, 177};
		public static final int[] RANDOM_SEQ55 = {201, 46, 149, 204, 110, 122, 4, 72, 230, 126, 96, 230};
		public static final int[] RANDOM_SEQ56 = {42, 162, 4, 184, 63, 75, 61, 202, 102, 3, 86, 198, 167};
		public static final int[] RANDOM_SEQ57 = {248, 121, 108, 184, 171, 187, 53, 196, 77, 211, 182, 83, 124, 96, 45, 217, 90, 30, 1, 180, 122, 223, 169, 42, 174, 58, 73, 227, 164, 121, 252, 195, 161, 18, 97};
		public static final int[] RANDOM_SEQ58 = {168, 60, 8, 242, 129, 92, 179, 201, 214, 98};
		public static final int[] RANDOM_SEQ59 = {102, 31, 141, 102, 206, 102, 200, 148, 169, 28, 213, 69, 255, 250, 83, 188, 72, 95, 164, 249, 159,
		 87, 203, 100, 85, 161, 38, 18, 173, 148, 234, 24, 228, 161};
		public static final int[] RANDOM_SEQ60 = {10, 143, 78, 54, 107, 114, 231, 16, 239, 9, 122, 23, 130, 241, 148, 200, 154, 141, 160};
		public static final int[] RANDOM_SEQ61 = {159, 211, 117, 148, 110, 71, 230, 67, 199, 152, 60, 99, 73, 124, 28, 14, 235, 76, 26, 13, 30, 185,
		 90, 182, 53, 124, 18, 42, 174, 7, 147};
		public static final int[] RANDOM_SEQ62 = {23, 190, 235, 83, 163, 33, 18, 102, 120, 68};

	// public static final YBasedWithSequenceTransformer
	// FIBONACCI_TRANSFORMER_HORIZ = new
	// YBasedWithSequenceTransformer(FIBONACCIS);
	// public static final YBasedWithSequenceTransformer
	// EXPONENTIAL_TRANSFORMER_HORIZ = new
	// YBasedWithSequenceTransformer(EXPONENTS);
	// public static final YBasedWithSequenceTransformer
	// HANDMADE_TRANSFORMER_HORIZ = new YBasedWithSequenceTransformer(HANDMADE);
	// public static final YBasedWithSequenceTransformer WAVE_TRANSFORMER_HORIZ
	// = new YBasedWithSequenceTransformer(WAVE);
	//	
	// public static final YBasedWithSequenceTransformer
	// FIBONACCI_TRANSFORMER_VERTICAL = new
	// YBasedWithSequenceTransformer(FIBONACCIS, true);
	// public static final YBasedWithSequenceTransformer
	// EXPONENTIAL_TRANSFORMER_VERTICAL = new
	// YBasedWithSequenceTransformer(EXPONENTS, true);
	// public static final YBasedWithSequenceTransformer
	// HANDMADE_TRANSFORMER_VERTICAL = new
	// YBasedWithSequenceTransformer(HANDMADE, true);
	// public static final YBasedWithSequenceTransformer
	// WAVE_TRANSFORMER_VERTICAL = new YBasedWithSequenceTransformer(WAVE,
	// true);

	private final int[] sequence;

	/**
	 * Constructor
	 * 
	 * @param sequence
	 */
	public YBasedWithSequenceTransformer(int[] sequence) {
		super();
		this.sequence = sequence;
	}

	/**
	 * Constructor
	 * 
	 * @param sequence
	 * @param flipAxis
	 *            If true, flip to do transformation vertically
	 */
	public YBasedWithSequenceTransformer(int[] sequence, boolean flipAxis) {
		super();
		this.sequence = sequence;
		this.flipAxis = flipAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aip.scramble.transform.YBasedDisplaceTransformer#getSourceXForReverse
	 * (int, int, int)
	 */
	@Override
	protected int getSourceXForReverse(int width, int y, int x) {
		int sourceX = x - this.sequence[y % this.sequence.length] % width;
		if (sourceX < 0) {
			sourceX = width + sourceX;
		}
		return sourceX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aip.scramble.transform.YBasedDisplaceTransformer#getSourceXForTransform
	 * (int, int, int)
	 */
	@Override
	protected int getSourceXForTransform(int width, int y, int x) {
		int sourceX = (x + this.sequence[y % this.sequence.length]) % width;
		return sourceX;
	}

	public static void main(String[] args) {
		Random r = new Random(111);
		for (int i = 1; i <= 62; i++) {
			StringBuilder buf = new StringBuilder();
			buf.append("public static final int[] RANDOM_SEQ" + i + " = {");
			int len = 10 + Math.abs(r.nextInt()) % 30;
			for (int j = 0; j < len; j++) {
				if (j > 0) {
					buf.append(", ");
				}
				buf.append(Math.abs(r.nextInt()) % 256);
			}
			buf.append("};");
			System.out.println(buf.toString());
		}
	}

}
