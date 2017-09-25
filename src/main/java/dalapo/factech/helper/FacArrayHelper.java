package dalapo.factech.helper;

public class FacArrayHelper {
	private FacArrayHelper() {}
	
	public static int sum(int[] arr)
	{
		int count = 0;
		for (int i=0; i<arr.length; i++)
		{
			count += arr[i];
		}
		return count;
	}
	
	public static boolean contains(Object[] arr, Object target)
	{
		for (int i=0; i<arr.length; i++)
		{
			if (arr[i].equals(target)) return true;
		}
		return false;
	}
	
	public static void pushThrough(boolean[] arr, boolean newVal)
	{
		for (int i=arr.length-1; i>0; i--)
		{
			arr[i] = arr[i-1];
		}
		arr[0] = newVal;
	}
}