package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.QuerySmResult;
import org.jsmpp.session.SMPPSession;

public class querySMSstatus {

	public static void main(String[] args) {
		
	}
	List <String> rspid = new ArrayList<String>();
	List <String> sndFrom = new ArrayList<String>();
	
	
	private void setRspid(){
		rspid.add("");sndFrom.add("8529300001 ");
	}
	querySMSstatus(){
		SMPPSession smppSession = new SMPPSession();
		setRspid();
		try {
			smppSession.connectAndBind("10.42.1.163", 2775, BindType.BIND_TRX, "17life","test17", "cln", TypeOfNumber.UNKNOWN,NumberingPlanIndicator.UNKNOWN, null);
			
			for(int i = 0;i<rspid.size();i++){
				
				org.jsmpp.bean.TypeOfNumber ton = TypeOfNumber.ALPHANUMERIC;
				try {
					long c = Long.parseLong(sndFrom.get(i));
					ton = TypeOfNumber.INTERNATIONAL;
				} catch (NumberFormatException e) {
					System.out
							.println("not send from number:" + sndFrom.get(i) + ":");
				}
				
				QuerySmResult q4 = smppSession.queryShortMessage( rspid.get(i),ton,NumberingPlanIndicator.UNKNOWN,sndFrom.get(i));
				byte status = q4.getMessageState().value();
				System.out.println("query:" + sndFrom.get(i) + ",status:" + status);
			}
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PDUException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResponseTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegativeResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
