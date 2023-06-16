package sender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class SendersTransmission {
	
	public void writeToLine(String input, String path, int lineNum) throws IOException{	
		File f = new File(path);
		FileWriter fw = new FileWriter(f,true);
		BufferedWriter bw = new BufferedWriter(fw);
		LineNumberReader lnr = new LineNumberReader(new FileReader(f));
		lnr.setLineNumber(lineNum);
		for(int i = 1;i < lnr.getLineNumber();i++){
			bw.newLine();
		}
		bw.write(input);
		bw.close();
		lnr.close();
	}
	

}
