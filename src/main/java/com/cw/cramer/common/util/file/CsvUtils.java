package com.cw.cramer.common.util.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.cw.cramer.common.helper.CsvReader;
import com.cw.cramer.common.helper.CsvWriter;
import com.cw.cramer.common.util.LogUtils;

/**
 * CSV文件操作类
 * @author wicks
 */
public class CsvUtils {

	/**
	 * 读取CSV文件
	 */
	public static List<String[]> readeCsv(String path, String filename) {
		List<String[]> csvList = new ArrayList<String[]>(); 
		try {
			String csvFilePath = path + filename;
			CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8")); 
			reader.readHeaders();
			csvList.add(reader.getValues());
			while (reader.readRecord()) { //逐行读入除表头的数据    
				if(reader.getValues()[0] == null || reader.getValues()[0].equals("")){
					break;
				}
				csvList.add(reader.getValues());
			}
			reader.close();

		} catch (Exception ex) {
			LogUtils.error("读取CSV文件失败，"+filename, ex);
		}
		
		return csvList;
	}

	/**
	 * 写入CSV文件
	 */
	public static String writeCsv(String path, String filename, List<String[]> csvList) {
		try {
			String csvFilePath = path + filename;
			CsvWriter wr = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
			if(csvList != null && csvList.size() > 0){
				for(String[] strs : csvList){
					wr.writeRecord(strs);
				}
			}
			wr.close();
			return csvFilePath;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
