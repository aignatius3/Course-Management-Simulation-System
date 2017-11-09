package Model; /**
 * Created by Ashwin Ignatius on 11/4/2017.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Scratchpad {
    public Scratchpad() { }

    private void processFileContents(String inputFileName, String[] tokens) {
        // REMOVE THESE PRINT STATEMENTS BEFORE SUBMISSION
        // display the contents (token) in the line selected for processing
        int cut = inputFileName.lastIndexOf("/");
        String fileToParse = inputFileName.substring(cut + 1);

        switch (fileToParse) {
            case "courses.csv":
                System.out.println(tokens[0] + ", " + tokens[1] + ", " + tokens[2]);
                break;
            case "instructors.csv":
                System.out.println(tokens[0] + ", " + tokens[1] + ", " + tokens[2] + ", " + tokens[3] + ", " + tokens[4]);
                break;
            case "listings.csv":
                System.out.println(tokens[0] + ", " + tokens[1]);
                break;
            case "prereqs.csv":
                System.out.println(tokens[0] + ", " + tokens[1]);
                break;
            case "programs.csv":
                System.out.println(tokens[0] + ", " + tokens[1]);
                break;
            case "records.csv":
                System.out.println(tokens[0] + ", " + tokens[1] + ", " + tokens[2] + ", " + tokens[3] + ", " + tokens[4]);
                break;
            case "requests.csv":
                System.out.println(tokens[0] + ", " + tokens[1]);
                break;
            case "students.csv":
                System.out.println(tokens[0] + ", " + tokens[1] + ", " + tokens[2] + ", " + tokens[3] + ", " + tokens[4]);
                break;
            default:
                System.out.println("# error: unknown input file name");
                break;
        }
    }

    public void uploadFileContents(String inputFileName) {
        // REMOVE THIS PRINT STATEMENT BEFORE SUBMISSION
        // display the name of the file selected for processing
        System.out.println(inputFileName);

        // Input file which needs to be parsed
        String fileToParse = inputFileName;
        BufferedReader fileReader = null;

        // Delimiter used in CSV file
        final String DELIMITER = ",";
        try {
            String line = "";
            // Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            // Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                // Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                processFileContents(inputFileName, tokens);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
