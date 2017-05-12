package program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.System.out;

public class LeetCodeTest {

	public static void main(String[] args) {
		
		LeetCodeTest lc = new LeetCodeTest();
		
		;
		out.println(lc.divide(2147483647,2));
		//-2147483648
		//1534236469
		//4294967296
		//2147483647


	}
	
	public int divide(int dividend, int divisor) {
		if(divisor == 0 ) return 0;
		int sign = 1;
		//if((dividend<0 && divisor<0 )||(dividend>0 && divisor>0 ))   sign = 1;
		if((dividend<0 && divisor>0 )||(dividend>0 && divisor<0 ))   sign = -1;
		
		long ldivisor = Math.abs((long)divisor);
		long ldividend = Math.abs((long)dividend);
		long quotient = 0;
		long temp = 0;
		long sum = 0;
		int i = 0;
		while(ldividend>=sum+ldivisor){
			ldividend-=sum;
			sum = ldivisor;
			temp = 1;
			while(ldividend>=sum+sum){
				sum += sum;
				temp += temp;
			}
			quotient += temp;			
			if(quotient>=Integer.MAX_VALUE) {
				out.println(quotient);
	    		return sign==1?Integer.MAX_VALUE : Integer.MIN_VALUE;
	    	}
		}
        return (int) (quotient*sign);
    }
}
