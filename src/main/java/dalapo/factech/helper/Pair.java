package dalapo.factech.helper;

import com.google.common.base.Objects;

public class Pair<A,B> {
	public A a;
	public B b;
	
	public Pair(A a, B b)
	{
		this.a = a;
		this.b = b;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hashCode(a, b);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Pair<?,?>)
		{
			if (other == this) return true;
			
			Pair<?,?> pair = (Pair<?,?>) other;
			if (pair.a.equals(this.a) && pair.b.equals(this.b))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return String.format("Pair{%s, %s}", a, b);
	}
}