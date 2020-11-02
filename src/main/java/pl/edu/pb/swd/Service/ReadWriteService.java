package pl.edu.pb.swd.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReadWriteService {
    public LinkedList<LinkedList<String>> readDataFromOriginalFile(String filePath, String separator){
        LinkedList<LinkedList<String>> records = new LinkedList<LinkedList<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                String row = values[0];
                if(row.isEmpty() || row.charAt(0) == '#'){
                    continue;
                }
                values = row.split(separator);
                LinkedList ll = new LinkedList(Arrays.asList(values));
                records.add(ll);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void saveToWorkingFile(LinkedList<LinkedList<String>> data) throws IOException {
        String csvFile = "coWorking.csv";
        FileWriter outputfile = new FileWriter(csvFile);

        CSVWriter writer = new CSVWriter(outputfile, ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        List<String[]> dataTemp = new ArrayList<String[]>();
        for(List<String> record: data){
            String temp = record.get(0);
            for(int i=1; i<record.size(); i++){
                temp = temp + ";" + record.get(i);
            }
            String[] rowdata = temp.split(";");
            dataTemp.add(rowdata);
        }
        writer.writeAll(dataTemp);
        writer.close();
    }

    public LinkedList<LinkedList <String>> readDataFromWorkingFile(){
        LinkedList<LinkedList<String>> records = new LinkedList<LinkedList<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader("coWorking.csv"));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                String row = values[0];
                values = row.split(";");
                LinkedList ll = new LinkedList(Arrays.asList(values));
                records.add(ll);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
