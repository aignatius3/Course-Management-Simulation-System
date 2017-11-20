package Model;
/**
 * Created by Ashwin Ignatius on 11/4/2017.
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Scratchpad {
    private HashMap<String, Course> courses;

    private HashMap<String, Program> programs;

    private HashMap<String, Instructor> instructors;

    private HashMap<String, Student> students;

    private HashMap<String, String> coursesTaught;

    private List<Request> requests;

    private int cycle;

    private Integer year;

    private String[] terms;

    private HashMap<String, Integer> coursesRequested;

    public Scratchpad() {
        this.courses = new HashMap<>();
        this.programs = new HashMap<>();
        this.instructors = new HashMap<>();
        this.students = new HashMap<>();
        this.coursesTaught = new HashMap<>();
        this.cycle = 0;
        this.year = 2017;
        this.terms = new String[]{"Winter", "Spring", "Summer", "Fall"};
        this.requests = new ArrayList<>();
        this.coursesRequested = new HashMap<>();
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
        System.out.println("Course , Instructor");
        if (cycle == 0) {
            int numCourses = courses.size();
            Set<Integer> coursesNeeded = new HashSet<>();
            for (Object key : instructors.keySet()) {
                int index = (int) Math.floor(Math.random() * (numCourses));
                while (!coursesNeeded.add(index)) {
                    index = (int) Math.floor(Math.random() * (numCourses));
                }
                Object courseKey = courses.keySet().toArray()[index];
                coursesTaught.put(courses.get(courseKey).getCourseID(),
                        instructors.get(key).getID());
                System.out.println(courses.get(courseKey).getCourseID() + "      , " +
                        instructors.get(key).getID());
            }
        }
        loadRequests();
    }

    public void loadRequests() {
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
                if (coursesRequested.containsKey(tokens[1])) {
                    coursesRequested.put(tokens[1], coursesRequested.get(tokens[1]) + 1);
                } else {
                    coursesRequested.put(tokens[1], 1);
                }
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

        System.out.println("Course , Number of Requests");
        for (Object key : coursesRequested.keySet()) {
            System.out.println(key+ "       , " + coursesRequested.get(key));
        }

        reassignInstructor();
    }

    public void reassignInstructor() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Reassign 1 Instructor (CourseID, InstructorID): ");
        String reassignment = reader.nextLine();
        String[] tokens = reassignment.split(",");
        reader.close();
        System.out.println(tokens[0]);
        if (!reassignment.equals("")) {
            for (Object key: coursesTaught.keySet()) {
                if (coursesTaught.get(key).equals(tokens[1])) {
                    coursesTaught.remove(key);
                    coursesTaught.put(tokens[0], tokens[1]);
                    break;
                }
            }
        }
        System.out.println("Course , Instructor");
        for (Object key :  coursesTaught.keySet()) {
            System.out.println(key + "      , " + coursesTaught.get(key));
        }

        processRequests();
    }

    public void processRequests() {
        for (Request request: requests) {
            if (coursesTaught.containsKey(request.getCourseID())) {
                boolean granted = true;
                String studentID = request.getStudentID();
                Student student = students.get(studentID);
                String courseID = request.getCourseID();
                ArrayList<String> prereqs = (ArrayList<String>) courses.get(courseID).getPrereqs();
                for (String id : prereqs) {
                    boolean granted2 = false;
                    for (HashMap record : student.getRecords()) {
                        if (record.containsKey(id)) {
                            granted2 = true;
                        }
                    }
                    if (!granted2) {
                        granted = granted2;
                        break;
                    }
                }
                if (granted) {
                    if (student.getRecords().size() == cycle) {
                        student.getRecords().add(new HashMap<>());
                    }
                    student.addCourseRecord(courseID, "A", cycle);

                    String recordsFile = "./src/MainApp/Records.csv";

                    try {
                        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(recordsFile, true));

                        String record = studentID + "," + courseID + "," + "A" + ","
                                + Integer.toString(year) + "," + Integer.toString(cycle % 4 + 1) + "\n";
                        System.out.print(record);
                        fileWriter.write(record);
                        fileWriter.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        cycle++;
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
