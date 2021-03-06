package lib.defines;

import lib.element.ElementTable;

public interface TableDefine 
{
	int TABLE_SCORE  = 0;
	int TABLE_STAGE = 1;
	
	ElementTable[] CONTAINER =
	{ 
		new ElementTable("level", new String[]{"level", "unlock", "complete"}, new String[]
		{
				"1", "1", "0",
				"2", "0", "0",
				"3", "0", "0",
				"4", "0", "0",
				"5", "0", "0",
		}),
		
		new ElementTable("stage", new String[]{"level", "stage", "value", "unlock", "complete"}, new String[]
		{
				"1", "1", "1", "1", "0",
				"1", "2", "1", "0", "0",
				"1", "3", "1", "0", "0",
				"1", "4", "1", "0", "0",
				"1", "5", "1", "0", "0",
				"1", "6", "1", "0", "0",
				
				"2", "1", "1", "1", "0",
				"2", "2", "1", "0", "0",
				"2", "3", "1", "0", "0",
				"2", "4", "1", "0", "0",
				"2", "5", "1", "0", "0",
				"2", "6", "1", "0", "0",
				
				"3", "1", "1", "1", "0",
				"3", "2", "1", "0", "0",
				"3", "3", "1", "0", "0",
				"3", "4", "1", "0", "0",
				"3", "5", "1", "0", "0",
				"3", "6", "1", "0", "0",
				
				"4", "1", "1", "1", "0",
				"4", "2", "1", "0", "0",
				"4", "3", "1", "0", "0",
				"4", "4", "1", "0", "0",
				"4", "5", "1", "0", "0",
				"4", "6", "1", "0", "0",
				
				"5", "1", "1", "1", "0",
				"5", "2", "1", "0", "0",
				"5", "3", "1", "0", "0",
				"5", "4", "1", "0", "0",
				"5", "5", "1", "0", "0",
				"5", "6", "1", "0", "0",
		}),
	};
}
