package sender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadRecieversTrans {
	
	public String getLine(int line) throws IOException {
		String data = null;
		
		BufferedReader br = new BufferedReader(new FileReader("/Users/ricardobaeza/Documents/recieversTransmission.txt"));
		
		String ln;
		boolean found = false;
		int lineIndex = 1;
		while((ln = br.readLine()) != null) {
			if(lineIndex == line) {
				data = br.readLine();
				found = true;
			}
			lineIndex++;
		}
		if (!found) {
			System.out.println("Line not found");
		}
		return data;
	
	}

}
