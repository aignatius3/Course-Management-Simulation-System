package MainApp;

import Model.*;

/**
 * Created by Ashwin Ignatius on 11/4/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scratchpad managementConsole = new Scratchpad();

        String[] managementSystemFiles = {"./TestCases/test_case1/courses.csv", "./TestCases/test_case1/instructors.csv",
                "./TestCases/test_case1/listings.csv", "./TestCases/test_case1/prereqs.csv",
                "./TestCases/test_case1/programs.csv", "./TestCases/test_case1/records.csv",
                "./TestCases/test_case1/requests.csv", "./TestCases/test_case1/students.csv"};
        for (String nextFileName : managementSystemFiles) {
            managementConsole.uploadFileContents(nextFileName);
        }
    }
}
