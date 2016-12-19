package test2;

public class Testyuv_rgb {

	public Testyuv_rgb() {
		// TODO Auto-generated constructor stub
	}

	int[] g_v_table=new int[256],g_u_table=new int[256],y_table=new int[256];
	int[][] r_yv_table=new int[256][256],b_yu_table=new int[256][256];
	int inited = 0;
	
	void initTable()
	{
		if (inited == 0)
		{
			inited = 1;
			int m = 0,n=0;
			for (; m < 256; m++)
			{
				g_v_table[m] = 833 * (m - 128);
				g_u_table[m] = 400 * (m - 128);
				y_table[m] = 1192 * (m - 16);
			}
			int temp = 0;
			for (m = 0; m < 256; m++)
				for (n = 0; n < 256; n++)
				{
					temp = 1192 * (m - 16) + 1634 * (n - 128);
					if (temp < 0) temp = 0; else if (temp > 262143) temp = 262143;
					r_yv_table[m][n] = temp;
					temp = 1192 * (m - 16) + 2066 * (n - 128);
					if (temp < 0) temp = 0; else if (temp > 262143) temp = 262143;
					b_yu_table[m][n] = temp;
				}
		}
	}
	
}
