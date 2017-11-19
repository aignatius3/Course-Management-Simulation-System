package Model;
/**
 * Created by Ashwin Ignatius on 11/4/2017.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Scratchpad {
    private HashMap<String, Course> courses;

    private HashMap<String, Program> programs;

    private HashMap<String, Instructor> instructors;

    private HashMap<String, Student> students;

    private HashMap<String, String> coursesTaught;

    private List<Request> requests;

    private int cycle;

    private String year;

    private String[] terms;

    private Set coursesRequested;

    public Scratchpad() {
        this.courses = new HashMap<>();
        this.programs = new HashMap<>();
        this.instructors = new HashMap<>();
        this.students = new HashMap<>();
        this.coursesTaught = new HashMap<>();
        this.cycle = 0;
        this.year = Integer.toString(2017);
        this.terms = new String[]{"Winter", "Spring", "Summer", "Fall"};
        this.requests = new ArrayList<>();
        this.coursesRequested = new HashSet();
    }

    private void processFileContents(String inputFileName, String[] tokens) {
        // REMOVE THESE PRINT STATEMENTS BEFORE SUBMISSION
        // display the contents (token) in the line selected for processing
        int cut = inputFileName.lastIndexOf("/");
        String fileToParse = inputFileName.substring(cut + 1);

        switch (fileToParse) {
            case "courses.csv":
                courses.put(tokens[0], new Course(tokens[0], tokens[1], tokens[2]));
                break;
            case "instructors.csv":
                instructors.put(tokens[0], new Instructor(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]));
                break;
            case "listings.csv":
                programs.get(tokens[0]).addListing(tokens[1]);
                break;
            case "prereqs.csv":
                courses.get(tokens[0]).addPrereq(tokens[1]);
                break;
            case "programs.csv":
                programs.put(tokens[0], new Program(tokens[0], tokens[1]));
                break;
            case "students.csv":
                students.put(tokens[0], new Student(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]));                break;
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

    public void assignInstructors() {
        if (cycle == 0) {
            int numInstructors = instructors.keySet().size();
            int numCourses = courses.keySet().size();
            for (int i = 0; i < numInstructors; i++) {
                Object instKey = instructors.keySet().toArray()[i];
                int index = (int) Math.floor(Math.random() * (numCourses + 1));
                Object courseKey = courses.keySet().toArray()[index];
                coursesTaught.put(courses.get(courseKey).getCourseID(),
                        instructors.get(instKey).getID());
                System.out.println(courses.get(courseKey).getCourseID() + " " +
                        instructors.get(instKey).getID());
            }

        }
    }

    public void processRequests() {
        String fileToParse = "./TestCases/test_case1/requests"+ Integer.toString(cycle) + ".csv";
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

                requests.add(new Request(tokens[0], tokens[1]));
                coursesRequested.add(tokens[0]);
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
        cycle++;
        System.out.println(cycle);
    }

    public HashMap<String, Course> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<String, Course> courses) {
        this.courses = courses;
    }

    public HashMap<String, Program> getPrograms() {
        return programs;
    }

    public void setPrograms(HashMap<String, Program> programs) {
        this.programs = programs;
    }

    public HashMap<String, Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(HashMap<String, Instructor> instructors) {
        this.instructors = instructors;
    }

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public void setStudents(HashMap<String, Student> students) {
        this.students = students;
    }
}
