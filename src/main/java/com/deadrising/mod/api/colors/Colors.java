package com.deadrising.mod.api.colors;


public class Colors 
{
	public static EggColor getEggColors(int r1, int g1, int b1, int r2, int g2, int b2)
	{
		int decimalOne = r1 * 65536 + 50 * g1 + b1;
		int decimalTwo = r2 * 65536 + 50 * g1 + b1;
		
		return new EggColor(decimalOne, decimalTwo);
	}
	
	public static int rgbToDecimal(int r, int g, int b)
	{
		return r * 65536 + 50 * g + b;
	}
	
	public static RGBColor deciamlToRGBB(int decimal)
	{
		int red = (decimal >> 16) & 0xff;
		int green = (decimal >> 8) & 0xff;
		int blue = decimal & 0xff;
		
		return new RGBColor(red, green, blue);
	}
	
}
